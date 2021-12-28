package com.inspiredcoda.chatincognito.data

import com.inspiredcoda.chatincognito.data.remote.NetworkEvent
import com.inspiredcoda.chatincognito.data.remote.dto.BaseResponse
import com.inspiredcoda.chatincognito.data.remote.dto.LoginRequest
import com.inspiredcoda.chatincognito.data.remote.dto.LoginResponse
import com.inspiredcoda.chatincognito.data.remote.dto.SignUpRequest
import com.inspiredcoda.chatincognito.data.remote.dto.SignUpResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class HttpServiceImpl @Inject constructor(
    val httpClient: HttpClient
) : HttpService {

    override suspend fun login(loginCred: LoginRequest): NetworkEvent {
        return try {
            val response =
                httpClient.post<BaseResponse<LoginResponse>>(HttpService.Endpoints.Login.url) {
                    contentType(ContentType.Application.Json)
                    body = loginCred
                }
            if (response.status) {
                NetworkEvent.Success(response.data)
            } else {
                NetworkEvent.Failure(response.message ?: "An error occurred")
            }
        } catch (ex: Exception) {
            NetworkEvent.Failure(ex.message ?: "Error validating user")
        }
    }

    override suspend fun signUp(request: SignUpRequest): NetworkEvent {
        return try {
            val response =
                httpClient.post<BaseResponse<SignUpResponse>>(HttpService.Endpoints.SignUp.url) {
                    contentType(ContentType.Application.Json)
                    body = request
                }
            if (response.status) {
                NetworkEvent.Success(response.data)
            } else {
                NetworkEvent.Failure(response.message ?: "An error occurred")
            }
        } catch (ex: Exception) {
            NetworkEvent.Failure(ex.message ?: "Error registering user")
        }
    }

}