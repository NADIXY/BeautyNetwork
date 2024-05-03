package com.example.beautynetwork.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beautynetwork.R
import com.example.beautynetwork.data.model.beautyapi.BeautyItem
import com.example.beautynetwork.data.model.Services
import com.example.beautynetwork.data.remote.BeautyApi
import java.lang.Exception

const val TAG = "Repository"

class Repository(private val api: BeautyApi) {

    private val _beauty = MutableLiveData<List<BeautyItem>>()
    val beauty : LiveData<List<BeautyItem>>
        get() = _beauty

    //Diese Funktion ruft Beautydaten von der API ab und aktualisiert den Wert des LiveData-Objekts _beauty
    suspend fun getBeauty() {
        try {
            _beauty.postValue(api.retrofitService.getBeautyProduct("maybelline"))
            Log.d(TAG, "${_beauty.value}")
        } catch (e : Exception) {
            Log.e(TAG, "$e")
        }

    }

    val beautyServices: List<Services> = listOf(
        Services("FIRST TIME TREATMENT","Für Ihren ersten Besuch im Medical Beauty Institut. Gesicht + Hals + Dekolleté *ausführliche computergestützt Hautanalyse mittels Skin Alyzer med7 Pro \n*aktuelles Hautbedürfnis und Pflegekonzept besprechen *Mikrodermabrasion oder Ultraschall *Wirkstoff Versorgung und Abschlusspflege 120min ", R.drawable.proxy,"160 EUR","*einmalig buchbar"),
        Services("Hautanalyse by Skin Alyzer med7 pro","",R.drawable.proxy,"60 EUR","30min"),
        Services("Anti-Age CARE","Gesicht + Hals + Dekolleté *High Performance Treatment: Eine Premium-Behandlung für die anspruchsvolle Kundin mit Zeichen von Hautalterung und Verzuckerung, Verjüngung auf höchstem Niveau für jeden Hauttyp geeignet 150min\n *Anti Age Treatment: Optimal für regenerationsbedürftige Haut mit moderaten bis deutlich ausgeprägten Zeichen der Hautalterung",R.drawable.proxy,"280 EUR","*Inkl. Mikrodermabrasion & Ultraschall"),
        Services("Hydration CARE","Gesicht + Hals + Dekolleté 1. Moisture Beauty Break: Intensive Durchfeuchtung und Plumping für glatte Haut. Ohne TECHNOLOGY 45min",R.drawable.proxy,"75 EUR","Ohne TECHNOLOGY"),
        Services("Lifting Beauty Break","Der Vitamin C-Kick mit Boost-Effekt",R.drawable.proxy,"","Ohne TECHNOLOGY"),
        Services("Hydration Treatment","",R.drawable.proxy,"",""),
        Services("MICRO NEEDLING","Micro-Needling zählt als Geheimwaffe unter den Anti-Aging Methoden zur Behandlung ästhetischer Hautprobleme wie:\n • Falten/erschlafftes Gewebe • Lichtgeschädigte Haut • bestimmte Narben • Stiae 80min",R.drawable.proxy,"210 EUR","Behandlungsempfehlung: 6 Behandlungen als Kur im Abstand von 2 Wochen. 6er KUR Gesicht + Hals 1300 EUR (inkl. Post Needling Kit im Wert von 89 EUR)"),
        Services("","",R.drawable.proxy,"",""),
        Services("","",R.drawable.proxy,"",""),
        Services("","",R.drawable.proxy,"",""),
        Services("","",R.drawable.proxy,"",""),
        Services("","",R.drawable.proxy,"",""),
        Services("","",R.drawable.proxy,"",""),
        Services("","",R.drawable.proxy,"",""),
        Services("","",R.drawable.proxy,"",""),
    )

    fun getItems(): List<Services> {
        return beautyServices
    }

}