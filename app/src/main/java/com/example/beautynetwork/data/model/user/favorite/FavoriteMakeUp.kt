package com.example.beautynetwork.data.model.user.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMakeUp(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String?

)
