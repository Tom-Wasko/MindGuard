package com.mindguard.mentor.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_message")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val role: String,        // "user" | "mentor"
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val sessionId: Int = 0   // groups conversation sessions
)
