package com.mindguard.mentor.domain.repository

import com.mindguard.mentor.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface MentorRepository {
    fun getMessagesFlow(): Flow<List<ChatMessage>>
    suspend fun sendMessage(userMessage: String, userContext: String): Flow<String>
    suspend fun saveMessage(message: ChatMessage)
    suspend fun clearHistory()
}
