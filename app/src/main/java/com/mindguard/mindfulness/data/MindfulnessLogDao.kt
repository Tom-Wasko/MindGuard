package com.mindguard.mindfulness.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MindfulnessLogDao {
    @Query("SELECT * FROM mindfulness_log ORDER BY startedAt DESC LIMIT 30")
    fun getRecentLogsFlow(): Flow<List<MindfulnessLogEntity>>

    @Insert
    suspend fun insertLog(log: MindfulnessLogEntity): Long

    @Query("UPDATE mindfulness_log SET completed = 1, completedAt = :completedAt WHERE id = :logId")
    suspend fun markCompleted(logId: Long, completedAt: Long = System.currentTimeMillis())
}
