package com.mindguard.mindfulness.domain.usecase

import com.mindguard.mindfulness.data.MindfulnessExerciseProvider
import com.mindguard.mindfulness.domain.model.MindfulnessExercise

class GetRandomExerciseUseCase {
    operator fun invoke(): MindfulnessExercise = MindfulnessExerciseProvider.getRandomExercise()
}
