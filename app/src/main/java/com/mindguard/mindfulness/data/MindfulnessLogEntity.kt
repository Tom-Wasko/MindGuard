package com.mindguard.mindfulness.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mindfulness_log")
data class MindfulnessLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val exerciseId: String,
    val completed: Boolean = false,
    val startedAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)
