package com.itu.lsm.classes

data class Chat(
    val id: String,
    val name: String,
    val messagePreview: String,
    val timestamp: String,
    val unreadCount: Int
)

