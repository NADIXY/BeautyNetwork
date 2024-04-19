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

    suspend fun getBeauty(){
        try {
        } catch (e : Exception) {
            Log.d("Repo", "$e")
        }

    }

}