package com.example.beautynetwork.data.model.user

import com.google.firebase.firestore.DocumentId

data class Profile(
    //@DocumentId
    //val userId: String = "",
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val number: String = "",
    val email: String = "",
    val adress: String = "",
    val dateOfBirth: String = "",
    val profession: String = "",
    val profilePicture: String = ""
)