package com.mindguard.mentor.data.repository

import com.mindguard.mentor.data.GeminiMentorService
import com.mindguard.mentor.data.dao.ChatMessageDao
import com.mindguard.mentor.domain.model.ChatMessage
import com.mindguard.mentor.domain.model.MessageRole
import com.mindguard.mentor.domain.model.toDomain
import com.mindguard.mentor.domain.model.toEntity
import com.mindguard.mentor.domain.repository.MentorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MentorRepositoryImpl(
    private val dao: ChatMessageDao,
    private val geminiService: GeminiMentorService
) : MentorRepository {

    override fun getMessagesFlow(): Flow<List<ChatMessage>> =
        dao.getAllMessagesFlow().map { list -> list.map { it.toDomain() } }

    override suspend fun sendMessage(userMessage: String, userContext: String): Flow<String> {
        // Save user message
        dao.insertMessage(
            ChatMessage(role = MessageRole.USER, content = userMessage).toEntity()
        )
        // Return streaming flow from Gemini
        return geminiService.streamResponse(userMessage, userContext)
    }

    override suspend fun saveMessage(message: ChatMessage) {
        dao.insertMessage(message.toEntity())
    }

    override suspend fun clearHistory() = dao.clearAllMessages()
}
