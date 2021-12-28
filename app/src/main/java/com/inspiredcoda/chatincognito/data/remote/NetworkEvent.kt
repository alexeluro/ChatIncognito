package com.inspiredcoda.chatincognito.data.remote

sealed class NetworkEvent {
    data class Success<T>(val data: T) : NetworkEvent()
    data class Failure(val message: String) : NetworkEvent()
}
