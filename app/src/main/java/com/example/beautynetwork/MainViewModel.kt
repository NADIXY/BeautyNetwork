package com.example.beautynetwork

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beautynetwork.data.Repository
import com.example.beautynetwork.data.local.getDatabase
import com.example.beautynetwork.data.model.management.Services
import com.example.beautynetwork.data.model.makeupapi.BeautyItem
import com.example.beautynetwork.data.model.management.SlidePics
import com.example.beautynetwork.data.model.user.Appointment
import com.example.beautynetwork.data.model.user.GeneralQuestionnaire
import com.example.beautynetwork.data.model.user.Profile
import com.example.beautynetwork.data.model.user.chat.Chat
import com.example.beautynetwork.data.model.user.chat.ChatProfile
import com.example.beautynetwork.data.model.user.chat.Message
import com.example.beautynetwork.data.model.user.favorite.FavoriteMakeUp
import com.example.beautynetwork.data.remote.BeautyApi
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//ViewModel-Klasse, die von AndroidViewModel erbt und eine Referenz auf die Anwendung enthält.
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Instanz von Firebase Authentication
    // Ersetzt in diesem Fall ein Repository
    val auth = Firebase.auth

    // Instanz von Firebase Firestore
    val firestore = Firebase.firestore

    // Instanz und Referenz von Firebase Storage
    val storage = Firebase.storage

    // LiveData um den aktuellen User zu halten
    // Initialwert ist in diesem Fall auth.currentUser
    // Das gewährleistet, dass der User sofort wieder eingeloggt ist sollte
    // er sich bereits einmal eingeloggt haben
    // LiveData kann auch "null" sein (Wenn der User nicht eingeloggt ist)
    private var _user = MutableLiveData<FirebaseUser?>(auth.currentUser)
    val user: LiveData<FirebaseUser?>
        get() = _user

    private var _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?>
        get() = _toastMessage


    //Das profile Document enthält ein einzelnes Profil(das des eingeloggten Users)
    //Document ist wie ein Objekt
    var profileRef: DocumentReference? = null
    var appointmentRef: CollectionReference? = null
    var generalQuestionnaireRef: CollectionReference? = null
    var profileCollectionReference: CollectionReference? = null
    lateinit var currentChatDocumentReference: DocumentReference
    var chatProfileRef: DocumentReference? = null

    init {
        setupUserEnv()
    }

    fun setupUserEnv() {
        _user.value = auth.currentUser
        auth.currentUser?.let { firebaseUser ->
            if (profileRef == null) {

                profileRef = firestore.collection("profiles").document(firebaseUser.uid)
                appointmentRef = firestore.collection("appointment")
                generalQuestionnaireRef = firestore.collection("generalQuestionnaire")
                profileCollectionReference= firestore.collection("profiles")
                chatProfileRef = firestore.collection("chat_profiles").document(firebaseUser.uid)

            }
        }
    }

    // Funktion um neuen User zu erstellen
    fun register(email: String, password: String, username: String) {
        // Firebase-Funktion um neuen User anzulegen
        // CompleteListener sorgt dafür, dass wir anschließend feststellen können, ob das funktioniert hat
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
            if (authResult.isSuccessful) {
                // Wenn Registrierung erfolgreich ist senden wir eine Email um die Email-Adresse zu bestätigen
                auth.currentUser?.sendEmailVerification()
                // Die Profil-Referenz wird jetzt gesetzt, da diese vom aktuellen User abhängt
                profileRef = firestore.collection("profiles").document(auth.currentUser!!.uid)
                // Ein neues, leeres Profil wird für jeden User erstellt der zum ersten mal einen Account für die App anlegt
                profileRef!!.set(Profile(username=username))

                chatProfileRef = firestore.collection("chat_profiles").document(auth.currentUser!!.uid)
                chatProfileRef!!.set(ChatProfile(username=username))

                // Danach führen wir logout Funktion aus, da beim Erstellen eines Users dieser sofort eingeloggt wird
                logout()
            } else {
                // Log, falls Fehler beim Erstellen eines Users auftritt
                Log.e("FIREBASE", "${authResult.exception}")
            }
        }
    }

    fun login(email: String, password: String) {
        // Firebase-Funktion um User einzuloggen
        // CompleteListener sorgt dafür, dass wir anschließend feststellen können, ob das funktioniert hat
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
            if (authResult.isSuccessful) {

                // Überprüfung, ob User bereits Email verifiziert hat
                if (auth.currentUser!!.isEmailVerified) {
                    
                    setupUserEnv()

                } else {
                    // Wenn User zwar exisitiert und Eingaben stimmen aber User seine Email noch nicht bestätigt hat
                    // wird User wieder ausgeloggt und eine Fehlermeldung ausgegeben
                    Log.e("FIREBASE", "User not verified")
                    logout()
                }

            } else {
                // Log, falls Fehler beim Login eines Users auftritt
                Log.e("FIREBASE", "${authResult.exception}")
            }
        }
    }

    fun sendPasswordReset(email: String) {
        auth.sendPasswordResetEmail(email)

    }

    fun logout() {
        auth.signOut()

        _user.value = auth.currentUser
    }

    fun uploadProfilePicture(uri: Uri) {

        val imageRef = storage.reference.child("images/${auth.currentUser!!.uid}/profilePicture")
        imageRef.putFile(uri).addOnCompleteListener {
            if (it.isSuccessful) {
                imageRef.downloadUrl.addOnCompleteListener { finalImageUrl ->
                    profileRef?.update("profilePicture", finalImageUrl.result.toString())
                }
            }
        }

    }

    // Funktion um Eingabefelder des Profils eines Users zu updaten
    fun updateProfile(profile: Profile) {
        profileRef?.update(
            mapOf(
                "username" to profile.username,
                "firstName" to profile.firstName,
                "lastName" to profile.lastName,
                "number" to profile.number,
                "email" to profile.email,
                "adress" to profile.adress,
                "dateOfBirth" to profile.dateOfBirth,
                "profession" to profile.profession

            )
        )
    }

    fun setAppointment(
        professional: String,
        date: String,
        hour: String,
        service: String,
        ) {

        val setAppointment = Appointment(
            professional = professional,
            date = date,
            hour = hour,
            service = service,
            userId = user.value!!.uid
            )

        appointmentRef?.add(setAppointment)

    }

    fun setGeneralQuestionnaire(
        question1: String,
        question2: String,
        question3: String,
        question4: String,
        question5: String,
        question6: String,
        question7: String,
        question8: String
    ) {

        val setQuestions = GeneralQuestionnaire(
            question1 = question1,
            question2 = question2,
            question3 = question3,
            question4 = question4,
            question5 = question5,
            question6 = question7,
            question7 = question8,
            question8 = question6,
            userId = user.value!!.uid
        )
        generalQuestionnaireRef?.add(setQuestions)
    }


    fun sendMessage(message: String) {
        val newMessage = Message(message, auth.currentUser!!.uid)
        currentChatDocumentReference.update("messages", FieldValue.arrayUnion(newMessage))
    }

    fun setCurrentChat(chatPartnerId: String) {
        val chatId = createChatId(chatPartnerId, user.value!!.uid)
        currentChatDocumentReference = firestore.collection("chats").document(chatId)
        currentChatDocumentReference.get().addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null && !task.result.exists()) {
                currentChatDocumentReference.set(Chat())
            }
        }
    }

    fun resetToastMessage() {
        _toastMessage.value = null
    }

    private fun createChatId(id1: String, id2: String): String {
        val ids = listOf(id1, id2).sorted()
        return ids.first() + ids.last()
    }


    //Repository Bereich


    //Beauty Api

    private val repository = Repository(BeautyApi, getDatabase(application))

    val beauty = repository.beauty

    fun loadBeauty() {
        viewModelScope.launch {
            repository.getBeauty()
        }

    }

    private var _setSelectedProduct = MutableLiveData<BeautyItem>()
    val setSelectedProduct: LiveData<BeautyItem>
        get() = _setSelectedProduct

    fun setSelectedProduct(item: BeautyItem) {
        _setSelectedProduct.value = item

    }


    //Beauty Api Endregion


    //SlidePics

    private val allSlidePics = repository.getSlidePics()

    private var _slidePics: MutableLiveData<List<SlidePics>> = MutableLiveData(allSlidePics)
    val slidePics: LiveData<List<SlidePics>>
        get() = _slidePics

    //SlidePics Endregion


    //Services

    private val allBeautyServices = repository.getItems()

    private var _items: MutableLiveData<List<Services>> = MutableLiveData(allBeautyServices)
    val items: LiveData<List<Services>>
        get() = _items

    // Funktion um Suche zu filtern
    fun filterItems(input: String) {
        val filteredItems =
            allBeautyServices.filter { it.title.lowercase().contains(input.lowercase()) }
        _items.value = filteredItems
    }

    private var _setSelectedServices = MutableLiveData<Services>()
    val setSelectedServices: LiveData<Services>
        get() = _setSelectedServices

    fun setSelectedServices(items: Services) {
        _setSelectedServices.value = items

    }


    //Services Endregion


    //Favorites

    val favorites = repository.favoriteMakeUp

    fun myFavoriteMakeUp(beautyItem: BeautyItem) {
        val newFavoriteMakeUp = FavoriteMakeUp(0, beautyItem.name, beautyItem.description,
            beautyItem.image_link)
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveFavoriteMakeUp(newFavoriteMakeUp)
        }
    }

    fun myDeletedFavoriteMakeUp(favoriteMakeUp: FavoriteMakeUp) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteMakeUp(favoriteMakeUp)
        }
    }

    fun allDeleted() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavoriteMakeUps()
        }
    }

    //Favorites Endregion

    //Repository Endbereich

}