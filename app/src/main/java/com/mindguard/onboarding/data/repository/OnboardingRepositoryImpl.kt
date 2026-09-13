package com.mindguard.onboarding.data.repository

import com.mindguard.onboarding.data.dao.UserProfileDao
import com.mindguard.onboarding.domain.model.UserProfile
import com.mindguard.onboarding.domain.model.toDomain
import com.mindguard.onboarding.domain.model.toEntity
import com.mindguard.onboarding.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OnboardingRepositoryImpl(
    private val dao: UserProfileDao
) : OnboardingRepository {

    override fun getUserProfileFlow(): Flow<UserProfile?> =
        dao.getUserProfileFlow().map { it?.toDomain() }

    override suspend fun getUserProfileOnce(): UserProfile? =
        dao.getUserProfileOnce()?.toDomain()

    override suspend fun saveUserProfile(profile: UserProfile) =
        dao.saveUserProfile(profile.toEntity())

    override suspend fun clearProfile() = dao.clearProfile()
}
