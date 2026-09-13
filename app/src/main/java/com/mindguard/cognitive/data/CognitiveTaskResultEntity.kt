package com.mindguard.cognitive.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cognitive_task_result")
data class CognitiveTaskResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val taskId: String,
    val correct: Boolean,
    val answeredAt: Long = System.currentTimeMillis(),
    val responseTimeMs: Int = 0
)
