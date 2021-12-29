package com.inspiredcoda.chatincognito.presentation.register

import com.inspiredcoda.chatincognito.data.HttpService
import com.inspiredcoda.chatincognito.data.remote.NetworkEvent
import com.inspiredcoda.chatincognito.data.remote.dto.SignUpRequest
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val service: HttpService
) {


    suspend fun signUp(signUpRequest: SignUpRequest): NetworkEvent{
        return service.signUp(signUpRequest)
    }

}