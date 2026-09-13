package com.mindguard.onboarding.domain.repository

import com.mindguard.onboarding.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    fun getUserProfileFlow(): Flow<UserProfile?>
    suspend fun getUserProfileOnce(): UserProfile?
    suspend fun saveUserProfile(profile: UserProfile)
    suspend fun clearProfile()
}
