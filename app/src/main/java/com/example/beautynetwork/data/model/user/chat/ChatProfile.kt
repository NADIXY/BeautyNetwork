package com.example.beautynetwork.data.model.user.chat

import com.google.firebase.firestore.DocumentId

data class ChatProfile(
    @DocumentId
    val userId: String = "",
    val username: String = "",
    val profilePictureChat: String = ""
)
