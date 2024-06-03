package com.example.beautynetwork.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beautynetwork.R
import com.example.beautynetwork.data.local.FavoriteMakeUpDatabase
import com.example.beautynetwork.data.model.makeupapi.BeautyItem
import com.example.beautynetwork.data.model.management.Services
import com.example.beautynetwork.data.model.management.SlidePics
import com.example.beautynetwork.data.model.user.favorite.FavoriteMakeUp
import com.example.beautynetwork.data.remote.BeautyApi
import java.lang.Exception

const val TAG = "Repository"

class Repository(private val api: BeautyApi, private val database: FavoriteMakeUpDatabase) {

    private val _beauty = MutableLiveData<List<BeautyItem>>()
    val beauty: LiveData<List<BeautyItem>>
        get() = _beauty

    //Diese Funktion ruft Beautydaten von der API ab und aktualisiert den Wert des LiveData-Objekts _beauty
    suspend fun getBeauty() {
        try {
            _beauty.postValue(api.retrofitService.getBeautyProduct("l'oreal"))
            Log.d(TAG, "${_beauty.value}")
        } catch (e: Exception) {
            Log.e(TAG, "$e")
        }

    }

    //Favorite Liste, speichert das Ergebnis der getFavoriteMakeUp Anfrange des Dao
    val favoriteMakeUp = database.dao.getFavoriteMakeUp()

    suspend fun saveFavoriteMakeUp(beautyItem: FavoriteMakeUp) {
        database.dao.insertFavoriteMakeUp(beautyItem)

    }

    suspend fun deleteFavoriteMakeUp(beautyItem: FavoriteMakeUp) {
        database.dao.delete(beautyItem)

    }

    suspend fun deleteAllFavoriteMakeUps() {
        try {
            database.dao.deleteAll()
        } catch (e: Exception) {
            Log.e(TAG, "Deleting all FavoriteMakeUps from database: $e")
        }
    }

    val mySlidePics: List<SlidePics> = listOf(
        SlidePics(R.drawable.logo),
        SlidePics(R.drawable.pic1111),
        SlidePics(R.drawable.pic9),
        SlidePics(R.drawable.pic9),
        SlidePics(R.drawable.pic10),
        SlidePics(R.drawable.pic5),
        SlidePics(R.drawable.pic6),
        SlidePics(R.drawable.pic7),
        SlidePics(R.drawable.pic8),
        SlidePics(R.drawable.pic11)
    )

    val myBeautyServices: List<Services> = listOf(
        Services(
            "1. FIRST TIME TREATMENT",
            "Für Ihren ersten Besuch im Medical Beauty Institut. Gesicht + Hals + Dekolleté *ausführliche computergestützt Hautanalyse mittels Skin Alyzer med7 Pro. \n*aktuelles Hautbedürfnis und Pflegekonzept besprechen *Mikrodermabrasion oder Ultraschall *Wirkstoff Versorgung und Abschlusspflege 120min ",
            R.drawable.proxy,
            "160 EUR",
            "*einmalig buchbar"
        ),
        Services(
            "2. Hautanalyse by Skin Alyzer med7 pro",
            "Der SkinAlyzermed7 von REVIDERM findet exakt heraus, was Ihrer Haut fehlt und wie Sie ihren Zustand nachhaltig verbessern.\nDafür misst und dokumentiert die computergestützte Hautanalyse jede Partie Ihrer Haut. In Farbbildern zeigt Ihnen die Kamera des Geräts alle Anomalien – und damit Abweichungen von gesunder und schöner Haut.",
            R.drawable.proxy,
            "60 EUR",
            "30min"
        ),
        Services(
            "3. Anti-Age CARE",
            "Gesicht + Hals + Dekolleté *High Performance Treatment: Eine Premium-Behandlung für die anspruchsvolle Kundin mit Zeichen von Hautalterung und Verzuckerung, Verjüngung auf höchstem Niveau für jeden Hauttyp geeignet 150min\n *Anti Age Treatment: Optimal für regenerationsbedürftige Haut mit moderaten bis deutlich ausgeprägten Zeichen der Hautalterung",
            R.drawable.proxy,
            "280 EUR",
            "*Inkl. Mikrodermabrasion & Ultraschall"
        ),
        Services(
            "4. Hydration CARE",
            "Gesicht + Hals + Dekolleté 1. Moisture Beauty Break: Intensive Durchfeuchtung und Plumping für glatte Haut. Ohne TECHNOLOGY 45min",
            R.drawable.proxy,
            "75 EUR",
            "Ohne TECHNOLOGY"
        ),
        Services(
            "5. Lifting Beauty Break",
            "Der Vitamin C-Kick mit Boost-Effekt",
            R.drawable.proxy,
            "75 EUR",
            "Ohne TECHNOLOGY"
        ),
        Services(
            "6. MICRO NEEDLING",
            "Micro-Needling zählt als Geheimwaffe unter den Anti-Aging Methoden zur Behandlung ästhetischer Hautprobleme wie:\n • Falten/erschlafftes Gewebe • Lichtgeschädigte Haut • bestimmte Narben • Stiae 80min",
            R.drawable.proxy,
            "210 EUR",
            "Behandlungsempfehlung: 6 Behandlungen als Kur im Abstand von 2 Wochen. 6er KUR Gesicht + Hals 1300 EUR (inkl. Post Needling Kit im Wert von 89 EUR)"
        ),

        )

    fun getItems(): List<Services> {
        return myBeautyServices
    }

    fun getSlidePics(): List<SlidePics> {
        return mySlidePics
    }
}