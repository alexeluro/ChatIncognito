package com.inspiredcoda.chatincognito.data

import com.inspiredcoda.chatincognito.data.remote.NetworkEvent
import com.inspiredcoda.chatincognito.data.remote.dto.LoginRequest
import com.inspiredcoda.chatincognito.data.remote.dto.SignUpRequest

interface HttpService {

    suspend fun login(loginCred: LoginRequest): NetworkEvent

    suspend fun signUp(signUpCred: SignUpRequest): NetworkEvent

    sealed class Endpoints(val url: String) {
        object Login : Endpoints("$BASE_URL/login")
        object SignUp : Endpoints("$BASE_URL/register")
    }

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
//        const val BASE_URL = "http://192.168.1.2:8080"
    }

}