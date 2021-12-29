package com.inspiredcoda.chatincognito.presentation.login

import com.inspiredcoda.chatincognito.data.HttpService
import com.inspiredcoda.chatincognito.data.remote.NetworkEvent
import com.inspiredcoda.chatincognito.data.remote.dto.LoginRequest
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val service: HttpService
) {

    suspend fun login(request: LoginRequest): NetworkEvent {
        return service.login(request)
    }


}