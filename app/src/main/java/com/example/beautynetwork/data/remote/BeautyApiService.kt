package com.example.beautynetwork.data.remote

import com.example.beautynetwork.data.model.BeautyItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Die Konstante enthält die URL der API-Endpunkt
const val BASE_URL = "http://makeup-api.herokuapp.com/api/v1/"

// Moshi konvertiert Serverantworten in Kotlin Objekte
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Dies ist eine private Variable, die einen HttpLoggingInterceptor-Objekt enthält.
//Setzt das Level des HttpLoggingInterceptor auf BODY, um den gesamten Body der HTTP-Anfragen und -Antworten zu loggen.
private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level =
        HttpLoggingInterceptor.Level.BODY //BASIC oder HEADERS (Andere verfügbare Optionen sind BASIC und HEADERS)
}

private val httpClient = OkHttpClient.Builder() //Erstellt einen neuen OkHttpClient Builder.
    .addInterceptor(logger) //Fügt einen Interceptor zum OkHttpClient Builder hinzu.
    .build() //Erstellt einen neuen OkHttpClient mit den konfigurierten Einstellungen.

// Retrofit übernimmt die Kommunikation und übersetzt die Antwort
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(httpClient)//Fügt einen Konverter hinzu, der Moshi zur Serialisierung und Deserialisierung verwendet.
    .baseUrl(BASE_URL)
    .build()

// Das Interface definiert unsere API Calls und bestimmt, wie mit dem Server kommuniziert wird
interface BeautyApiService {

    @GET("products.json")
    suspend fun getBeautyProduct(
        @Query("brand") brand: String,
        //@Query("product_type") product_type: String
    ): List<BeautyItem>

}

// Das Objekt dient als Zugangspunkt für den Rest der App und stellt den API Service zur Verfügung
object BeautyApi {
    val retrofitService: BeautyApiService by lazy { retrofit.create(BeautyApiService::class.java) }
}