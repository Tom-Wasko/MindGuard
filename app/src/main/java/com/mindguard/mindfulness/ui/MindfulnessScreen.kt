package com.mindguard.mindfulness.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mindguard.R
import com.mindguard.core.ui.theme.*
import com.mindguard.mindfulness.domain.model.ExerciseType
import com.mindguard.mindfulness.domain.model.StepInputType
import com.mindguard.mindfulness.ui.components.*
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MindfulnessScreen(
    onExerciseComplete: () -> Unit,
    viewModel: MindfulnessViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isComplete) {
        if (state.isComplete) onExerciseComplete()
    }

    val exercise = state.exercise ?: return

    AnimatedLofiBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .filmGrain(0.05f)
                .vignette(0.7f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // Exercise title (retro mono font)
                Text(
                    text = getTitleForExercise(exercise.id),
                    style = MaterialTheme.typography.headlineMedium,
                    color = WarmAmber,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Step progress bar
                if (exercise.steps.size > 1) {
                    LinearProgressIndicator(
                        progress = {
                            (state.currentStepIndex + 1).toFloat() / exercise.steps.size.toFloat()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = WarmAmber,
                        trackColor = MidnightSurfaceVar
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(
                            R.string.mindfulness_step_of,
                            state.currentStepIndex + 1,
                            exercise.steps.size
                        ),
                        style = MaterialTheme.typography.labelSmall,
                        color = MutedLilac,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Main content area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    when (exercise.type) {
                        ExerciseType.BREATHING -> {
                            val pattern = exercise.breathingPattern
                            if (pattern != null) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    // Instruction
                                    Text(
                                        text = exercise.steps.firstOrNull()?.instructionPl ?: "",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MutedLilac,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                    Spacer(modifier = Modifier.height(32.dp))
                                    BreathingAnimation(
                                        pattern = pattern,
                                        onComplete = { viewModel.completeExercise() }
                                    )
                                }
                            }
                        }

                        ExerciseType.GROUNDING -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                exercise.steps.forEachIndexed { index, step ->
                                    GroundingStepItem(
                                        step = step,
                                        stepIndex = index,
                                        currentTapCount = state.stepTapCounts[index] ?: 0,
                                        isCurrentStep = index == state.currentStepIndex,
                                        onTap = {
                                            val done = viewModel.recordTap(index)
                                            if (done) viewModel.goToNextStep()
                                        }
                                    )
                                }
                            }
                        }

                        ExerciseType.INTERACTIVE -> {
                            val currentStep = exercise.steps.getOrNull(state.currentStepIndex)
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                currentStep?.let { step ->
                                    Text(
                                        text = step.instructionPl,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = CreamWhite
                                    )
                                    if (step.inputType == StepInputType.TEXT ||
                                        step.inputType == StepInputType.NUMERIC
                                    ) {
                                        var answer by remember(state.currentStepIndex) {
                                            mutableStateOf(state.stepAnswers[state.currentStepIndex] ?: "")
                                        }
                                        OutlinedTextField(
                                            value = answer,
                                            onValueChange = {
                                                answer = it
                                                viewModel.recordStepAnswer(state.currentStepIndex, it)
                                            },
                                            placeholder = {
                                                Text(
                                                    stringResource(R.string.mindfulness_type_your_answer),
                                                    color = DimText
                                                )
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(120.dp),
                                            minLines = 3,
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = WarmAmber,
                                                unfocusedBorderColor = MidnightSurfaceVar,
                                                focusedTextColor = CreamWhite,
                                                unfocusedTextColor = CreamWhite,
                                                cursorColor = WarmAmber
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                    }
                                    if (state.currentStepIndex < exercise.steps.size - 1) {
                                        Button(
                                            onClick = { viewModel.goToNextStep() },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = WarmAmber,
                                                contentColor = MidnightInk
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text(stringResource(R.string.onboarding_next))
                                        }
                                    }
                                }
                            }
                        }

                        ExerciseType.TIMED, ExerciseType.REFLECTIVE -> {
                            val currentStep = exercise.steps.getOrNull(state.currentStepIndex)
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                currentStep?.let { step ->
                                    Text(
                                        text = step.instructionPl,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = CreamWhite,
                                        textAlign = TextAlign.Center,
                                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                                    )
                                }

                                val totalDuration = currentStep?.durationSeconds
                                    ?: exercise.totalDurationSeconds ?: 0
                                if (totalDuration > 0 && state.timerRemainingSeconds > 0) {
                                    CountdownTimer(
                                        remainingSeconds = state.timerRemainingSeconds,
                                        totalSeconds = totalDuration
                                    )
                                }

                                if (exercise.steps.size > 1 &&
                                    state.currentStepIndex < exercise.steps.size - 1 &&
                                    (state.timerRemainingSeconds == 0 || totalDuration == 0)
                                ) {
                                    Button(
                                        onClick = { viewModel.goToNextStep() },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = WarmAmber,
                                            contentColor = MidnightInk
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(stringResource(R.string.onboarding_next))
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Complete button
                Button(
                    onClick = { viewModel.completeExercise() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SageGreen,
                        contentColor = MidnightInk
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.mindfulness_complete),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// Helper to get title string resource id by exercise id
@Composable
fun getTitleForExercise(exerciseId: String): String {
    return stringResource(
        when (exerciseId) {
            "grounding_54321" -> R.string.mindfulness_title_grounding
            "stop_technique" -> R.string.mindfulness_title_stop
            "box_breathing" -> R.string.mindfulness_title_box_breathing
            "breathing_478" -> R.string.mindfulness_title_breathing_478
            "helicopter_view" -> R.string.mindfulness_title_helicopter
            "wise_mind" -> R.string.mindfulness_title_wise_mind
            "gratitude" -> R.string.mindfulness_title_gratitude
            "safe_place" -> R.string.mindfulness_title_safe_place
            "mantra" -> R.string.mindfulness_title_mantra
            "counting_back" -> R.string.mindfulness_title_counting
            "body_scan" -> R.string.mindfulness_title_body_scan
            "abc_analysis" -> R.string.mindfulness_title_abc
            "cognitive_restructuring" -> R.string.mindfulness_title_restructuring
            "detail_registration" -> R.string.mindfulness_title_details
            "emotion_acceptance" -> R.string.mindfulness_title_acceptance
            else -> R.string.mindfulness_title_grounding
        }
    )
}
