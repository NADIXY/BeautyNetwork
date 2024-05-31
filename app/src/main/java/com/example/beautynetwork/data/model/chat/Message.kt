package com.example.beautynetwork.data.model.chat

import com.google.firebase.Timestamp

class Message(
    val text: String = "",
    val sender: String = "",
    val timestamp: Timestamp = Timestamp.now()
)