package com.mindguard.mentor.domain.model

data class ChatMessage(
    val id: Long = 0,
    val role: MessageRole,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isStreaming: Boolean = false
)

enum class MessageRole { USER, MENTOR }

fun com.mindguard.mentor.data.entity.ChatMessageEntity.toDomain() = ChatMessage(
    id = id,
    role = if (role == "user") MessageRole.USER else MessageRole.MENTOR,
    content = content,
    timestamp = timestamp
)

fun ChatMessage.toEntity(sessionId: Int = 0) =
    com.mindguard.mentor.data.entity.ChatMessageEntity(
        id = id,
        role = if (role == MessageRole.USER) "user" else "mentor",
        content = content,
        timestamp = timestamp,
        sessionId = sessionId
    )
