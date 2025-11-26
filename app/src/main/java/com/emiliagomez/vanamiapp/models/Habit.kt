package com.emiliagomez.vanamiapp.models

data class Habit(
    val id: String = "",
    val name: String = "",
    val category: Category = Category(),
    val userId: String? = null
)


