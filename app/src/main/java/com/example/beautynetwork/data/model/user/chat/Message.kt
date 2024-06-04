package com.example.beautynetwork.data.model.user.chat

import com.google.firebase.Timestamp

data class Message(
    val text: String = "",
    val sender: String = "",
    val timestamp: Timestamp = Timestamp.now()
)
