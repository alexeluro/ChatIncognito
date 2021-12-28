package com.inspiredcoda.chatincognito.data

import com.inspiredcoda.chatincognito.data.remote.NetworkEvent
import com.inspiredcoda.chatincognito.data.remote.dto.BaseResponse
import com.inspiredcoda.chatincognito.data.remote.dto.ErrorResponse
import com.inspiredcoda.chatincognito.data.remote.dto.LoginRequest
import com.inspiredcoda.chatincognito.data.remote.dto.LoginResponse
import com.inspiredcoda.chatincognito.data.remote.dto.SignUpRequest
import com.inspiredcoda.chatincognito.data.remote.dto.SignUpResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class HttpServiceImpl @Inject constructor(
    val httpClient: HttpClient,
    val serializer: Json
) : HttpService {

    override suspend fun login(loginCred: LoginRequest): NetworkEvent {
        return try {
            val response =
                httpClient.post<BaseResponse<LoginResponse>>(HttpService.Endpoints.Login.url) {
//                    contentType(ContentType.Application.Json)
                    body = loginCred
                }
            if (response.status) {
                NetworkEvent.Success(response.data)
            } else {
                NetworkEvent.Failure(response.message ?: "An error occurred")
            }
        } catch (ex: ClientRequestException) {
            val response = ex.errorResponse()
            NetworkEvent.Failure(response.message ?: "An error occurred")
        } catch (ex: Exception) {
            NetworkEvent.Failure(ex.message ?: "Error validating user")
        }
    }

    override suspend fun signUp(request: SignUpRequest): NetworkEvent {
        return try {
            val response =
                httpClient.post<HttpResponse>(HttpService.Endpoints.SignUp.url) {
//                    contentType(ContentType.Application.Json)
                    body = request
                }
            if (response.status.value in 200..299) {
                val baseResponse = response.receive<BaseResponse<SignUpResponse>>()
                NetworkEvent.Success(baseResponse)
            } else {
                val baseResponse = response.receive<ErrorResponse>()
                NetworkEvent.Failure(baseResponse.message ?: "An error occurred")
            }
        } catch (ex: ClientRequestException) {
            val response = ex.errorResponse()
            NetworkEvent.Failure(response.message ?: "An error occurred")
        } catch (ex: Exception) {
            val error = ex.errorResponse()
            NetworkEvent.Failure(error.message ?: "Error registering user")
        }
    }

    private fun Exception.errorResponse(): ErrorResponse {
        return ErrorResponse(
            status = false,
            message = this.message
        )
    }

    private suspend fun ClientRequestException.errorResponse(): ErrorResponse {
        return response.call.receive<ErrorResponse>()
    }

    private suspend fun ByteReadChannel.getError(): ErrorResponse {
        return serializer.decodeFromString(readUTF8Line(toString().length)!!)
    }

}