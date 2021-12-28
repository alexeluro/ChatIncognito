package com.inspiredcoda.chatincognito.domain

sealed class UIState{
    data class Success<T>(val data: T): UIState()
    data class Error(val message: String): UIState()
}
