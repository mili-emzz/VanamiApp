package com.emiliagomez.vanamiapp.models

data class User (
    val id: String = "",
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val startDate: com.google.firebase.Timestamp? = null
)