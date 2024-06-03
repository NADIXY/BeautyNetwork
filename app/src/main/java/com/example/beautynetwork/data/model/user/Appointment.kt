package com.example.beautynetwork.data.model.user

import com.google.firebase.Timestamp

data class Appointment(
    val professional: String = "",
    val date: String = "",
    val hour: String = "",
    val service: String = "",
    val userId: String = "",
    val username: String = "",
    val timestamp: Timestamp = Timestamp.now()
)