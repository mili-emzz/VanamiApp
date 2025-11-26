package com.emiliagomez.vanamiapp.models

import com.emiliagomez.vanamiapp.models.references.EmotionReference
import com.emiliagomez.vanamiapp.models.references.HabitReference

data class Record(
    val date: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now(),
    val description: String = "",
    val habit: HabitReference = HabitReference(),
    val emotions: List<EmotionReference> = emptyList()
)
