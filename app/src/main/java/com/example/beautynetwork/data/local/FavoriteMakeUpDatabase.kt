package com.example.beautynetwork.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.beautynetwork.data.model.makeupapi.BeautyItem
import com.example.beautynetwork.data.model.user.favorite.FavoriteMakeUp


@Database(entities = [FavoriteMakeUp::class], version = 1)
abstract class FavoriteMakeUpDatabase : RoomDatabase() {
    abstract val dao: FavoriteMakeUpDatabaseDao
}

private lateinit var INSTANCE: FavoriteMakeUpDatabase

fun getDatabase(context: Context): FavoriteMakeUpDatabase  {
    synchronized(FavoriteMakeUpDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {

            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                FavoriteMakeUpDatabase::class.java,
                "my_favorite_make_up"
            ).build()
        }
        return INSTANCE

    }
}