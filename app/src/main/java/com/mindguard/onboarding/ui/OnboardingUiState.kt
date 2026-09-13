package com.mindguard.onboarding.ui

import com.mindguard.onboarding.domain.model.MentorType

data class OnboardingUiState(
    val currentStep: Int = 0,
    val totalSteps: Int = 5,
    val selectedLanguage: String = "pl",
    val lifeGoals: String = "",
    val selectedTriggers: Set<String> = emptySet(),
    val changeReasons: String = "",
    val mentorType: MentorType = MentorType.FRIEND,
    val isSaving: Boolean = false,
    val isComplete: Boolean = false,
    val validationError: String? = null
)

val triggerOptions = listOf(
    "boredom", "stress", "loneliness", "anxiety",
    "anger", "sadness", "social_pressure",
    "tiredness", "procrastination", "celebration", "other"
)
