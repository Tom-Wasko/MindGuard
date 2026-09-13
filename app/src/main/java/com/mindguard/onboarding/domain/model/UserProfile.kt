package com.mindguard.onboarding.domain.model

data class UserProfile(
    val id: Int = 1,
    val language: String = "pl",
    val lifeGoals: String = "",
    val triggers: List<String> = emptyList(),
    val changeReasons: String = "",
    val mentorType: MentorType = MentorType.FRIEND,
    val mentorTraits: List<String> = emptyList()
)

enum class MentorType(val key: String) {
    COACH("coach"),
    FRIEND("friend"),
    SAGE("sage")
}

fun com.mindguard.onboarding.data.entity.UserProfileEntity.toDomain() = UserProfile(
    id = id,
    language = language,
    lifeGoals = lifeGoals,
    triggers = triggers,
    changeReasons = changeReasons,
    mentorType = MentorType.entries.find { it.key == mentorType } ?: MentorType.FRIEND,
    mentorTraits = mentorTraits
)

fun UserProfile.toEntity() = com.mindguard.onboarding.data.entity.UserProfileEntity(
    id = id,
    language = language,
    lifeGoals = lifeGoals,
    triggers = triggers,
    changeReasons = changeReasons,
    mentorType = mentorType.key,
    mentorTraits = mentorTraits,
    updatedAt = System.currentTimeMillis()
)
