package com.app.simplelogin.db

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Login", primaryKeys = ["email"])
data class Login(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)