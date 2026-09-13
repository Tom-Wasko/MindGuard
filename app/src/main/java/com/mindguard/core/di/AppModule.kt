package com.mindguard.core.di

import androidx.room.Room
import com.mindguard.BuildConfig
import com.mindguard.cognitive.ui.CognitiveTaskViewModel
import com.mindguard.core.data.MindGuardDatabase
import com.mindguard.mentor.data.GeminiMentorService
import com.mindguard.mentor.data.repository.MentorRepositoryImpl
import com.mindguard.mentor.domain.repository.MentorRepository
import com.mindguard.mentor.domain.usecase.SendMessageUseCase
import com.mindguard.mentor.ui.MentorChatViewModel
import com.mindguard.mindfulness.domain.usecase.GetRandomExerciseUseCase
import com.mindguard.mindfulness.ui.MindfulnessViewModel
import com.mindguard.onboarding.data.repository.OnboardingRepositoryImpl
import com.mindguard.onboarding.domain.repository.OnboardingRepository
import com.mindguard.onboarding.domain.usecase.SaveUserProfileUseCase
import com.mindguard.onboarding.ui.OnboardingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // === DATABASE ===
    single {
        Room.databaseBuilder(
            androidContext(),
            MindGuardDatabase::class.java,
            "mindguard.db"
        ).build()
    }

    // DAOs
    single { get<MindGuardDatabase>().userProfileDao }
    single { get<MindGuardDatabase>().chatMessageDao }
    single { get<MindGuardDatabase>().mindfulnessLogDao }

    // === REPOSITORIES ===
    single<OnboardingRepository> { OnboardingRepositoryImpl(get()) }
    single<MentorRepository> { MentorRepositoryImpl(get(), get()) }

    // === USE CASES ===
    factory { SaveUserProfileUseCase(get()) }
    factory { GetRandomExerciseUseCase() }
    factory { SendMessageUseCase(get()) }

    // === AI SERVICE ===
    single { GeminiMentorService(BuildConfig.GEMINI_API_KEY) }

    // === VIEW MODELS ===
    viewModel { OnboardingViewModel(get(), get()) }
    viewModel { MindfulnessViewModel(get(), get()) }
    viewModel { MentorChatViewModel(get()) }
    viewModel { CognitiveTaskViewModel() }
}
