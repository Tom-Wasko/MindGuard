package com.mindguard.mindfulness.domain.model

data class MindfulnessExercise(
    val id: String,
    val titleKey: String,              // Maps to string resource key suffix
    val type: ExerciseType,
    val steps: List<ExerciseStep>,
    val totalDurationSeconds: Int? = null,
    val hasBreathingAnimation: Boolean = false,
    val breathingPattern: BreathingPattern? = null
)

enum class ExerciseType {
    GROUNDING,       // Interactive step-by-step tapping
    BREATHING,       // Timed animation
    REFLECTIVE,      // Write/think prompts
    TIMED,           // Just a timer with instructions
    INTERACTIVE      // Forms, inputs
}

data class ExerciseStep(
    val instructionEn: String,
    val instructionPl: String,
    val durationSeconds: Int? = null,
    val inputType: StepInputType = StepInputType.NONE,
    val tapCount: Int? = null          // For grounding steps
)

enum class StepInputType { NONE, TEXT, TAP, NUMERIC }

data class BreathingPattern(
    val inhaleDuration: Int,
    val holdDuration: Int,
    val exhaleDuration: Int,
    val cycles: Int,
    val shape: BreathingShape = BreathingShape.CIRCLE
)

enum class BreathingShape { CIRCLE, SQUARE }
