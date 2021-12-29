package com.inspiredcoda.chatincognito.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class SignUpRequest(
    val username: String,
    val password: String,
    val phoneNumber: String,
    val email: String,
    val profilePicture: String = "",
    val dateOfBirth: String
)