package com.mindguard.mentor.domain.usecase

import com.mindguard.mentor.domain.repository.MentorRepository
import kotlinx.coroutines.flow.Flow

class SendMessageUseCase(private val repository: MentorRepository) {
    suspend operator fun invoke(message: String, userContext: String = ""): Flow<String> =
        repository.sendMessage(message, userContext)
}
