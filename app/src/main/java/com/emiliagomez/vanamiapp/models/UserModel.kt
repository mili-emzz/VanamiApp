package com.emiliagomez.vanamiapp.models

import com.google.firebase.Timestamp

data class UserModel (
    val id: String,
    val name: String,
    val username: String,
    val email: String,
    val startDate: Timestamp? = null
){
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "userId" to this.id,
            "email" to this.email,
            "name" to this.name,
            "username" to this.username,
            "startDate" to (this.startDate ?: Timestamp.now())
        )
    }
    companion object {
        fun fromMap(map: Map<String, Any>): UserModel {
            return UserModel(
                id = map["userId"] as? String ?: "",
                name = map["name"] as? String ?: "",
                username = map["username"] as? String ?: "",
                email = map["email"] as? String ?: "",
                startDate = map["startDate"] as? Timestamp
            )
        }
    }
}