package com.example.beautynetwork.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beautynetwork.data.model.BeautyItem
import com.example.beautynetwork.data.remote.BeautyApi
import java.lang.Exception

const val TAG = "Repository"

class Repository(private val api: BeautyApi) {

    private val _beauty = MutableLiveData<List<BeautyItem>>()
    val beauty : LiveData<List<BeautyItem>>
        get() = _beauty

    private val _brand = MutableLiveData<List<BeautyItem>>()
    val brand : LiveData<List<BeautyItem>>
        get() = _brand

    //Diese Funktion ruft Beautydaten von der API ab und aktualisiert den Wert des LiveData-Objekts _beauty

    suspend fun getBeautyBrand(){
        try {
            _brand.postValue(api.retrofitService.getMaybelline("clinique"))
            Log.d(TAG, "${_brand.value}")
        } catch (e : Exception) {
            Log.e(TAG, "$e")
        }

    }

    suspend fun getBeauty() {
        try {
            _beauty.postValue(api.retrofitService.getBeautyProduct("lipstick"))
            Log.d(TAG, "${_beauty.value}")
        } catch (e : Exception) {
            Log.e(TAG, "$e")
        }

    }

}