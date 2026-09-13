package com.mindguard.mentor.data.dao

import androidx.room.*
import com.mindguard.mentor.data.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_message ORDER BY timestamp ASC")
    fun getAllMessagesFlow(): Flow<List<ChatMessageEntity>>

    @Query("SELECT * FROM chat_message WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    fun getSessionMessagesFlow(sessionId: Int): Flow<List<ChatMessageEntity>>

    @Insert
    suspend fun insertMessage(message: ChatMessageEntity): Long

    @Query("DELETE FROM chat_message")
    suspend fun clearAllMessages()

    @Query("SELECT MAX(sessionId) FROM chat_message")
    suspend fun getLatestSessionId(): Int?
}
