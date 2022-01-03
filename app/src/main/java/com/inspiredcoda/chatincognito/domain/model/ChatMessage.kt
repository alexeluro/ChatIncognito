package com.inspiredcoda.chatincognito.domain.model

data class ChatMessage(
    val sender: String,
    val message: String,
    val fromMe: Boolean,
    val timestamp: Long
)
