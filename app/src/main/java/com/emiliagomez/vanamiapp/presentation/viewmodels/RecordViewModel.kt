package com.emiliagomez.vanamiapp.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId

data class DailyRecord(val date: String = "",
    val emotionId: String = "",
    val emotionName: String = "",
    val emotionImgUrl: String = "",
    val habits: Map<String, Int> = emptyMap(),
    val note: String = "",
    val createdAt: Timestamp = Timestamp.now()
) {
    fun toMap(): Map<String, Any> = mapOf(
        "date" to date,
        "emotionId" to emotionId,
        "emotionName" to emotionName,
        "emotionImgUrl" to emotionImgUrl,
        "habits" to habits,
        "note" to note,
        "createdAt" to createdAt
    )

    companion object {
        fun fromMap(map: Map<String, Any>): DailyRecord = DailyRecord(
            date = map["date"] as? String ?: "",
            emotionId = map["emotionId"] as? String ?: "",
            emotionName = map["emotionName"] as? String ?: "",
            emotionImgUrl = map["emotionImgUrl"] as? String ?: "",
            habits = (map["habits"] as? Map<String, Long>)?.mapValues { it.value.toInt() } ?: emptyMap(),
            note = map["note"] as? String ?: "",
            createdAt = map["createdAt"] as? Timestamp ?: Timestamp.now()
        )
    }
}

class RecordViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = Firebase.firestore

    // Estado del registro actual
    var currentDate by mutableStateOf(LocalDate.now())
    var selectedEmotion by mutableStateOf<EmotionData?>(null)
    var selectedHabits by mutableStateOf<Map<String, Int>>(emptyMap())
    var note by mutableStateOf("")
    var showAlert by mutableStateOf(false)
    var alertMessage by mutableStateOf("")

    var recordsByDate by mutableStateOf<Map<String, DailyRecord>>(emptyMap())
    var isLoading by mutableStateOf(false)

    init {
        loadUserRecords()
    }

    fun loadUserRecords() {
        val userId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            isLoading = true
            try {
                val snapshot = firestore
                    .collection("Users")
                    .document(userId)
                    .collection("Records")
                    .get()
                    .await()

                val records = mutableMapOf<String, DailyRecord>()
                snapshot.documents.forEach { doc ->
                    val record = DailyRecord.fromMap(doc.data ?: emptyMap())
                    records[record.date] = record
                }
                recordsByDate = records

                // Cargar datos del día actual si existen
                loadRecordForDate(currentDate)
            } catch (e: Exception) {
                Log.e("RecordViewModel", "Error loading records: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun loadRecordForDate(date: LocalDate) {
        currentDate = date
        val dateKey = formatDate(date)
        val record = recordsByDate[dateKey]

        if (record != null) {
            selectedEmotion = EmotionData(
                id = record.emotionId,
                name = record.emotionName,
                imgUrl = record.emotionImgUrl
            )
            selectedHabits = record.habits
            note = record.note
        } else {
            // Limpiar si no hay registro
            selectedEmotion = null
            selectedHabits = emptyMap()
            note = ""
        }
    }

    fun saveRecord(onSuccess: () -> Unit = {}, onError: () -> Unit = {}) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            alertMessage = "Debes iniciar sesión para guardar"
            showAlert = true
            onError()
            return
        }

        if (selectedEmotion == null) {
            alertMessage = "Selecciona una emoción primero"
            showAlert = true
            onError()
            return
        }

        viewModelScope.launch {
            try {
                val dateKey = formatDate(currentDate)
                val record = DailyRecord(
                    date = dateKey,
                    emotionId = selectedEmotion!!.id,
                    emotionName = selectedEmotion!!.name,
                    emotionImgUrl = selectedEmotion!!.imgUrl,
                    habits = selectedHabits,
                    note = note
                )

                firestore
                    .collection("Users")
                    .document(userId)
                    .collection("Records")
                    .document(dateKey)
                    .set(record.toMap())
                    .await()

                //se actualiza el cache por el registro y el record
                recordsByDate = recordsByDate + (dateKey to record)

                alertMessage = "¡Registro guardado!"
                showAlert = true
                onSuccess()
            } catch (e: Exception) {
                Log.e("RecordViewModel", "Error saving record: ${e.message}")
                alertMessage = "Error al guardar: ${e.localizedMessage}"
                showAlert = true
                onError()
            }
        }
    }

    //guarda lo q se registra
    fun selectEmotion(emotion: EmotionData) {
        selectedEmotion = emotion
    }
    fun selectHabit(category: String, index: Int) {
        selectedHabits = selectedHabits + (category to index)
    }

    fun updateNote(newNote: String) {
        note = newNote
    }

    //no che si se va a usar
    fun hasRecordForDate(date: LocalDate): Boolean {
        return recordsByDate.containsKey(formatDate(date))
    }
    fun getEmotionForDate(date: LocalDate): EmotionData? {
        val record = recordsByDate[formatDate(date)]
        return if (record?.emotionId?.isNotEmpty() == true) {
            EmotionData(
                id = record.emotionId,
                name = record.emotionName,
                imgUrl = record.emotionImgUrl
            )
        } else null
    }

    fun closeAlert() {
        showAlert = false
    }

    private fun formatDate(date: LocalDate): String {
        return "${date.year}-${date.monthValue.toString().padStart(2, '0')}-${date.dayOfMonth.toString().padStart(2, '0')}"
    }
}

//otra pq no me jalaba la otra pero luego lo arreglo
data class EmotionData(
    val id: String = "",
    val name: String = "",
    val imgUrl: String = ""
)