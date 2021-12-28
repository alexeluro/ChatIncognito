package com.inspiredcoda.chatincognito.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: Boolean = false,
    val message: String? = "",
    val data: T? = null
)


@Serializable
data class ErrorResponse(
    val status: Boolean = false,
    val message: String? = ""
)

@Serializable
data class LoginResponse(
    @SerialName("username")
    val username: String?,
    @SerialName("authToken")
    val token: String?
)

@Serializable
data class SignUpResponse(
    val username: String?,
    val phoneNumber: String?,
    val email: String?,
    val profilePic: String?,
    val dateOfBirth: String?
)