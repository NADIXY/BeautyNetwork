package com.example.beautynetwork.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.beautynetwork.data.model.user.favorite.FavoriteMakeUp

@Dao
interface FavoriteMakeUpDatabaseDao {

    @Query("SELECT * FROM FavoriteMakeUp ORDER BY id DESC")
    fun getFavoriteMakeUp() : LiveData<List<FavoriteMakeUp>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMakeUp(favoriteMakeUp: FavoriteMakeUp)

    @Delete
    suspend fun delete(favoriteMakeUp: FavoriteMakeUp)

    @Query("DELETE FROM FavoriteMakeUp ")
    suspend fun deleteAll()
}