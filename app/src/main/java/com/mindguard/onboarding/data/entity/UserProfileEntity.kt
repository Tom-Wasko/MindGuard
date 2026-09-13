package com.mindguard.onboarding.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val language: String = "pl",
    val lifeGoals: String = "",
    val triggers: List<String> = emptyList(),
    val changeReasons: String = "",
    val mentorType: String = "friend",       // coach | friend | sage
    val mentorTraits: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
