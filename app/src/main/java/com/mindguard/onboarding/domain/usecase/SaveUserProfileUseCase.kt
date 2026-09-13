package com.mindguard.onboarding.domain.usecase

import com.mindguard.onboarding.domain.model.UserProfile
import com.mindguard.onboarding.domain.repository.OnboardingRepository

class SaveUserProfileUseCase(private val repository: OnboardingRepository) {
    suspend operator fun invoke(profile: UserProfile) = repository.saveUserProfile(profile)
}
