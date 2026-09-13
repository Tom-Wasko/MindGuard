package com.mindguard.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindguard.onboarding.domain.model.MentorType
import com.mindguard.onboarding.domain.model.UserProfile
import com.mindguard.onboarding.domain.repository.OnboardingRepository
import com.mindguard.onboarding.domain.usecase.SaveUserProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val saveUserProfileUseCase: SaveUserProfileUseCase,
    private val repository: OnboardingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun updateLanguage(language: String) {
        _uiState.update { it.copy(selectedLanguage = language, validationError = null) }
    }

    fun updateLifeGoals(goals: String) {
        _uiState.update { it.copy(lifeGoals = goals, validationError = null) }
    }

    fun toggleTrigger(trigger: String) {
        _uiState.update { state ->
            val updated = state.selectedTriggers.toMutableSet()
            if (trigger in updated) updated.remove(trigger) else updated.add(trigger)
            state.copy(selectedTriggers = updated, validationError = null)
        }
    }

    fun updateChangeReasons(reasons: String) {
        _uiState.update { it.copy(changeReasons = reasons, validationError = null) }
    }

    fun updateMentorType(type: MentorType) {
        _uiState.update { it.copy(mentorType = type, validationError = null) }
    }

    fun nextStep() {
        val state = _uiState.value
        val error = validateCurrentStep(state)
        if (error != null) {
            _uiState.update { it.copy(validationError = error) }
            return
        }
        if (state.currentStep < state.totalSteps - 1) {
            _uiState.update { it.copy(currentStep = it.currentStep + 1, validationError = null) }
        } else {
            saveProfile()
        }
    }

    fun previousStep() {
        _uiState.update {
            if (it.currentStep > 0) it.copy(currentStep = it.currentStep - 1, validationError = null)
            else it
        }
    }

    private fun validateCurrentStep(state: OnboardingUiState): String? {
        return when (state.currentStep) {
            1 -> if (state.lifeGoals.trim().length < 20) "validation_min_chars" else null
            2 -> if (state.selectedTriggers.isEmpty()) "validation_select_trigger" else null
            3 -> if (state.changeReasons.trim().length < 20) "validation_min_chars" else null
            else -> null
        }
    }

    private fun saveProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            try {
                val state = _uiState.value
                saveUserProfileUseCase(
                    UserProfile(
                        language = state.selectedLanguage,
                        lifeGoals = state.lifeGoals,
                        triggers = state.selectedTriggers.toList(),
                        changeReasons = state.changeReasons,
                        mentorType = state.mentorType
                    )
                )
                _uiState.update { it.copy(isSaving = false, isComplete = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, validationError = "error_saving") }
            }
        }
    }
}
