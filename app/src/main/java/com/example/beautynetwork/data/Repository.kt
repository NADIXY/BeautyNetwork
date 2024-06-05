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
            "1. MAKE UP WORKSHOP",
            "Das richtige Make-up vervollständigt nicht nur dein Outfit, sondern gibt dir den extra Schub Selbstvertrauen." +
                    "\n\n Tages- vs. Abend-Make-up: Wo liegt der Unterschied?\n" +
                    "Was ist beim Abend-Make-up zu beachten?\n" +
                    "Die besten Make-up-Looks für Dinnerpartys und andere Anlässe.\n" +
                    "Schimmernde Make-up-Looks;\n" +
                    "Smokey Eyes und verführerische Lippen;\n" +
                    "Klassisch-elegantes Abend-Make-up;\n" +
                    "In wenigen Schritten zum perfekten Abend-Make-up;\n" +
                    "Gesicht vorbereiten.\n" +
                    "So ebnest du einen Teint und lässt ihn strahlen;\n" +
                    "Augen- und Lippen-Make-up;\n" +
                    "Finishing Touches runden den Look gekonnt ab;\n" +
                    "Tipps und Tricks für dein Dinnerparty-Make-up;\n" +
                    "Welche Farbnuancen passen zu dir?\n" +
                    "So hält dein Make-up den ganzen Abend;\n" +
                    "Abend-Make-up einfach entfernen.\n\n" +
                    "Kreieren Sie Ihren Make-up Look mit unseren Produkten • 120 min. •",
            R.drawable.proxy,
            "250 EUR",
            "max. 5er Gruppe '1250 EUR' (inkl. Snacks & Drinks )"
        ),
        Services(
            "2. FIRST TIME TREATMENT",
            "Für Ihren ersten Besuch im Medical Beauty Institut Nadia Baptista." +
                    " Gesicht + Hals + Dekolleté * ausführliche computergestützt Hautanalyse mittels Skin Alyzer med7 Pro. *" +
                    " \n * Aktuelles Hautbedürfnis und Pflegekonzept besprechen * Wirkstoff Versorgung und Abschlusspflege • 80 min. •",
            R.drawable.proxy,
            "160 EUR",
            "* Mikrodermabrasion oder Ultraschall * einmalig buchbar * "
        ),
        Services(
            "3. Hautanalyse by Skin Alyzer med7 pro",
            "Der SkinAlyzermed7 von REVIDERM findet exakt heraus, was Ihrer Haut fehlt und wie Sie ihren Zustand nachhaltig verbessern." +
                    "\nDafür misst und dokumentiert die computergestützte Hautanalyse jede Partie Ihrer Haut. In Farbbildern zeigt Ihnen die Kamera des Geräts alle Anomalien" +
                    " – und damit Abweichungen von gesunder und schöner Haut.",
            R.drawable.proxy,
            "60 EUR",
            "• 30 min. •"
        ),
        Services(
            "4. Sensitive CARE",
            "Gesicht + Hals + Dekolleté * High Performance Treatment: Optimal für Sensible Haut bis hin zur therapiebegleitenden Pflege bei Neurodermitis * • 80 min. •",
            R.drawable.proxy,
            "179 EUR",
            "* Inkl. Mikrodermabrasion oder Ultraschall *"
        ),
        Services(
            "5. Oleosa Purity CARE",
            "Gesicht + Hals + Dekolleté * High Performance Treatment: Optimal für fett- und" +
                    " feuchtigkeitsreiche Haut bis hin zur therapiebegleitenden pflege bei Akne * • 80 min. •",
            R.drawable.proxy,
            "179 EUR",
            "* Inkl. Mikrodermabrasion & Ultraschall *"
        ),
        Services(
            "6. Sicca Purity CARE",
            "Gesicht + Hals + Dekolleté * High Performance Treatment: Optimal für fettreiche und feuchtigkeitsarme Haut * • 80 min. •",

            R.drawable.proxy,
            "179 EUR",
            "* Inkl. Mikrodermabrasion & Ultraschall *"
        ),
        Services(
            "7. Anti-Age CARE",
            "Gesicht + Hals + Dekolleté * High Performance Treatment: Eine Premium-Behandlung für die anspruchsvolle Kundin mit Zeichen von Hautalterung und Verzuckerung," +
                    " Verjüngung auf höchstem Niveau für jeden Hauttyp geeignet." +
                    "\n * Anti Age Treatment: Optimal für regenerationsbedürftige Haut mit moderaten bis deutlich ausgeprägten Zeichen der Hautalterung * • 150 min. •",
            R.drawable.proxy,
            "280 EUR",
            "* Inkl. Mikrodermabrasion oder Ultraschall *"
        ),
        Services(
            "8. Hydration CARE",
            "Gesicht + Hals + Dekolleté. Moisture Beauty Break: Intensive Durchfeuchtung und Plumping für glatte Haut. Ohne TECHNOLOGY • 30 min. •",
            R.drawable.proxy,
            "75 EUR",
            "Ohne TECHNOLOGY"
        ),
        Services(
            "9. Lifting Beauty Break",
            "Gesicht + Hals + Dekolleté. Der Vitamin C-Kick mit Boost-Effekt • 30 min. •",
            R.drawable.proxy,
            "75 EUR",
            "Ohne TECHNOLOGY"
        ),
        Services(
            "10. MICRO NEEDLING",
            "Micro-Needling zählt als Geheimwaffe unter den Anti-Aging Methoden zur Behandlung ästhetischer Hautprobleme wie:" +
                    "\n • Falten/erschlafftes Gewebe • Lichtgeschädigte Haut • bestimmte Narben • Stiae • 45 min. •",
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