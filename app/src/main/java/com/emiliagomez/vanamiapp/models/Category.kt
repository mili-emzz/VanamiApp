package com.emiliagomez.vanamiapp.models

data class Category(
    val name: String = "",
    val isDefault: Boolean = false,
    val createdAt: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now()

)
