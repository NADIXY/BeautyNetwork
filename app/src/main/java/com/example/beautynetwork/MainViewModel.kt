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
import com.example.beautynetwork.data.model.Services
import com.example.beautynetwork.data.model.makeupapi.BeautyItem
import com.example.beautynetwork.data.model.user.GeneralQuestionnaire
import com.example.beautynetwork.data.model.user.Profile
import com.example.beautynetwork.data.model.user.favorite.FavoriteMakeUp
import com.example.beautynetwork.data.remote.BeautyApi
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
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
    // Initialwert ist in diesem Fall firebaseAuth.currentUser
    // Das gewährleistet, dass der User sofort wieder eingeloggt ist sollte er sich bereits einmal eingeloggt haben
    // LiveData kann auch "null" sein (Wenn der User nicht eingeloggt ist)
    private var _user = MutableLiveData<FirebaseUser?>(auth.currentUser)
    val user: LiveData<FirebaseUser?>
        get() = _user

    //Das profile Document enthält ein einzelnes Profil(das des eingeloggten Users)
    //Document ist wie ein Objekt

    var profileRef: DocumentReference? = null
    var generalQuestionnaireRef: CollectionReference? = null

    init {
        setupUserEnv()
    }

    fun setupUserEnv() {

        _user.value = auth.currentUser

        auth.currentUser?.let { firebaseUser ->
            if (profileRef == null) {

                profileRef = firestore.collection("profiles").document(firebaseUser.uid)

                generalQuestionnaireRef =
                    firestore.collection("profiles").document(firebaseUser.uid)
                        .collection("generalQuestionnaire")
            }
        }
    }

    // Funktion um neuen User zu erstellen
    fun register(email: String, password: String) {
        // Firebase-Funktion um neuen User anzulegen
        // CompleteListener sorgt dafür, dass wir anschließend feststellen können, ob das funktioniert hat
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
            if (authResult.isSuccessful) {
                // Wenn Registrierung erfolgreich ist senden wir eine Email um die Email-Adresse zu bestätigen
                auth.currentUser?.sendEmailVerification()
                // Die Profil-Referenz wird jetzt gesetzt, da diese vom aktuellen User abhängt
                profileRef = firestore.collection("profiles").document(auth.currentUser!!.uid)
                // Ein neues, leeres Profil wird für jeden User erstellt der zum ersten mal einen Account für die App anlegt
                profileRef!!.set(Profile())

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
                    // Wenn Email verifiziert, wird LiveData mit dem eingeloggten User befüllt
                    // Das triggert dann die Navigation im SignInFragment

                    // Die Profil-Referenz wird jetzt gesetzt, da diese vom aktuellen User abhängt
                    profileRef = firestore.collection("profiles").document(auth.currentUser!!.uid)

                    _user.value = auth.currentUser

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

        // Danach wird der Wert der currentUser LiveData auf den aktuellen Wert des Firebase-CurrentUser gesetzt
        // Nach Logout ist dieser Wert null, also ist auch in der LiveData danach der Wert null gespeichert
        // Dies triggert die Navigation aus dem HomeFragment zurück zum SignInFragment
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
                "firstName" to profile.firstName,
                "lastName" to profile.lastName,
                "number" to profile.number,
                "email" to profile.email,
                "adress" to profile.adress,
                "dateOfBirth" to profile.dateOfBirth,
            )
        )
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
            question6 = question6,
            question7 = question7,
            question8 = question8,
        )
        generalQuestionnaireRef?.add(setQuestions)
    }


    //Repository Bereich

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

    //Services Enderegion


    //Favorites

    val favorites = repository.favoriteMakeUp

    fun myFavoriteMakeUp(beautyItem: BeautyItem) {
        val newFavoriteMakeUp = FavoriteMakeUp(0, beautyItem.name, beautyItem.description, beautyItem.image_link)
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveFavoriteMakeUp(newFavoriteMakeUp)
        }
    }

    fun my(favoriteMakeUp: FavoriteMakeUp) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteMakeUp(favoriteMakeUp)
        }
    }

    //Favorites Enderegion

}