package com.emiliagomez.vanamiapp.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emiliagomez.vanamiapp.models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = Firebase.firestore
    var currentUser by mutableStateOf<UserModel?>(null)
    var showAlert by mutableStateOf(false)

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            Log.d("Error en Firebase", "Error: ${task.exception?.localizedMessage}")
                            showAlert = true
                        }
                    }
            } catch (e: Exception) {
                Log.d("Error en Jetpack", "Error: ${e.localizedMessage}")
                showAlert = true
            }
        }
    }

    fun createUser(email: String, password: String, name: String, username: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val profileUpdates = userProfileChangeRequest {
                                displayName = username
                            }

                            auth.currentUser?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { profileTask ->
                                    if (profileTask.isSuccessful) {
                                        Log.d("LoginViewModel", "DisplayName actualizado")
                                    }
                                }

                            saveUser(username,name, email, onSuccess)
                        } else {
                            Log.d("Error en Firebase", "Error: ${task.exception?.localizedMessage}")
                            showAlert = true
                        }
                    }
            } catch (e: Exception) {
                Log.d("Error en Jetpack", "Error: ${e.localizedMessage}")
            }
        }
    }

    private fun saveUser(username: String, name: String, email: String, onSuccess: () -> Unit) {
        val id = auth.currentUser?.uid

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (id != null) {
                    val user = UserModel(
                        id = id,
                        name = name,
                        username = username,
                        email = email,
                        startDate = Timestamp.now()
                    )

                    firestore.collection("Users")
                        .document(id)
                        .set(user.toMap())
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            showAlert = true
                        }
                } else {
                    showAlert = true
                }
            } catch (e: Exception) {
                showAlert = true
                Log.e("Error en Firebase", "Error al guardar el usuario: ${e.localizedMessage}")
            }
        }
    }

    fun closeAlert() {
        showAlert = false
    }

    fun logout(){
        auth.signOut()
    }

    fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    fun getUserData(onSuccess: (UserModel?) -> Unit) {
        val userId = auth.currentUser?.uid

        if(userId != null){
            firestore.collection("Users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userData = document.data
                        val user = userData?.let { UserModel.fromMap(it) }
                        currentUser = user

                        Log.d("LoginViewModel", "Datos del usuario obtenidos: ${user?.name}")
                    } else {
                        currentUser = null
                        Log.e("LoginViewModel", "Documento no existe en Firestore")
                    }
                }
                .addOnFailureListener { exception ->
                    currentUser = null
                    Log.e("LoginViewModel", "Error al obtener datos: ${exception.message}")
                }
        }
    }
}