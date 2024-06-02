package com.example.beautynetwork.data.model.user

import com.google.firebase.Timestamp

data class GeneralQuestionnaire(
    val question1: String = "",
    val question2: String = "",
    val question3: String = "",
    val question4: String = "",
    val question5: String = "",
    val question6: String = "",
    val question7: String = "",
    val question8: String = "",
    val timestamp: Timestamp = Timestamp.now()
)
