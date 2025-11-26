package com.emiliagomez.vanamiapp.models

import com.emiliagomez.vanamiapp.models.references.CategoryReference

data class Emotion(
    val id: String = "",
    val name: String = "",
    val imgUrl: String = "",
    val category: CategoryReference = CategoryReference()
)
