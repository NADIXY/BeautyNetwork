package com.example.beautynetwork.data.model.user

import com.google.firebase.firestore.DocumentId


data class Profile(
    val firstName: String = "",
    val lastName: String = "",
    val number: String = "",
    val email: String = "",
    val adress: String = "",
    val dateOfBirth: String = "",
    val profilePicture: String = "",
    val question1: String = "",
    val question2: String = "",
    val question3: String = "",
    val question4: String = "",
    val question5: String = "",
    val question6: String = "",
    val question7: String = "",
    val question8: String = "",
)