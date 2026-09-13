package com.mindguard.onboarding.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mindguard.R
import com.mindguard.core.ui.theme.*
import com.mindguard.onboarding.domain.model.MentorType
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isComplete) {
        if (state.isComplete) onOnboardingComplete()
    }

    AnimatedLofiBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .filmGrain()
                .vignette()
                .systemBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(48.dp))

                // Header
                Text(
                    text = stringResource(R.string.onboarding_welcome),
                    style = MaterialTheme.typography.headlineMedium,
                    color = WarmAmber
                )
                Text(
                    text = stringResource(R.string.onboarding_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MutedLilac
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Step indicator
                StepIndicator(
                    currentStep = state.currentStep,
                    totalSteps = state.totalSteps
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Step content with animation
                AnimatedContent(
                    targetState = state.currentStep,
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInHorizontally { it } + fadeIn(tween(300)) togetherWith
                                slideOutHorizontally { -it } + fadeOut(tween(200))
                        } else {
                            slideInHorizontally { -it } + fadeIn(tween(300)) togetherWith
                                slideOutHorizontally { it } + fadeOut(tween(200))
                        }
                    },
                    modifier = Modifier.weight(1f),
                    label = "step_anim"
                ) { step ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        when (step) {
                            0 -> LanguageStepContent(
                                selectedLanguage = state.selectedLanguage,
                                onLanguageSelected = viewModel::updateLanguage
                            )
                            1 -> GoalsStepContent(
                                goals = state.lifeGoals,
                                onGoalsChanged = viewModel::updateLifeGoals
                            )
                            2 -> TriggersStepContent(
                                selectedTriggers = state.selectedTriggers,
                                onTriggerToggled = viewModel::toggleTrigger
                            )
                            3 -> ReasonsStepContent(
                                reasons = state.changeReasons,
                                onReasonsChanged = viewModel::updateChangeReasons
                            )
                            4 -> MentorStepContent(
                                selectedType = state.mentorType,
                                onTypeSelected = viewModel::updateMentorType
                            )
                        }
                    }
                }

                // Validation error
                state.validationError?.let { errorKey ->
                    val errorText = when (errorKey) {
                        "validation_min_chars" -> stringResource(R.string.onboarding_validation_min_chars)
                        "validation_select_trigger" -> stringResource(R.string.onboarding_validation_select_trigger)
                        else -> stringResource(R.string.common_error)
                    }
                    Text(
                        text = errorText,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Navigation buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (state.currentStep > 0) {
                        OutlinedButton(
                            onClick = viewModel::previousStep,
                            border = BorderStroke(1.dp, MutedLilac),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.onboarding_back),
                                color = MutedLilac
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(80.dp))
                    }

                    Button(
                        onClick = viewModel::nextStep,
                        enabled = !state.isSaving,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WarmAmber,
                            contentColor = MidnightInk
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        if (state.isSaving) {
                            CircularProgressIndicator(
                                color = MidnightInk,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = if (state.currentStep == state.totalSteps - 1)
                                    stringResource(R.string.onboarding_finish)
                                else
                                    stringResource(R.string.onboarding_next)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StepIndicator(currentStep: Int, totalSteps: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        repeat(totalSteps) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == currentStep) 24.dp else 8.dp, 8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (index == currentStep) WarmAmber
                        else if (index < currentStep) SageGreen
                        else MidnightSurfaceVar
                    )
            )
        }
    }
}

@Composable
fun LanguageStepContent(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.onboarding_step_language),
            style = MaterialTheme.typography.titleLarge,
            color = CreamWhite,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        LanguageCard(
            emoji = "🇵🇱",
            label = "Polski",
            isSelected = selectedLanguage == "pl",
            onClick = { onLanguageSelected("pl") }
        )
        Spacer(modifier = Modifier.height(12.dp))
        LanguageCard(
            emoji = "🇬🇧",
            label = "English",
            isSelected = selectedLanguage == "en",
            onClick = { onLanguageSelected("en") }
        )
    }
}

@Composable
fun LanguageCard(emoji: String, label: String, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) WarmAmber else MidnightSurfaceVar
    val bgColor = if (isSelected) WarmAmber.copy(alpha = 0.1f) else MidnightSurface
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = emoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, style = MaterialTheme.typography.titleMedium, color = CreamWhite)
        }
        if (isSelected) {
            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = WarmAmber)
        }
    }
}

@Composable
fun GoalsStepContent(goals: String, onGoalsChanged: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.onboarding_goals_label),
            style = MaterialTheme.typography.titleLarge,
            color = CreamWhite,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "${goals.trim().length}/20 min",
            style = MaterialTheme.typography.labelSmall,
            color = if (goals.trim().length >= 20) SageGreen else MutedLilac,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = goals,
            onValueChange = onGoalsChanged,
            placeholder = { Text(stringResource(R.string.onboarding_goals_hint), color = DimText) },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            minLines = 4,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
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
}

@Composable
fun TriggersStepContent(
    selectedTriggers: Set<String>,
    onTriggerToggled: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.onboarding_triggers_label),
            style = MaterialTheme.typography.titleMedium,
            color = CreamWhite,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        triggerOptions.forEach { triggerKey ->
            val labelRes = when (triggerKey) {
                "boredom" -> R.string.trigger_boredom
                "stress" -> R.string.trigger_stress
                "loneliness" -> R.string.trigger_loneliness
                "anxiety" -> R.string.trigger_anxiety
                "anger" -> R.string.trigger_anger
                "sadness" -> R.string.trigger_sadness
                "social_pressure" -> R.string.trigger_social_pressure
                "tiredness" -> R.string.trigger_tiredness
                "procrastination" -> R.string.trigger_procrastination
                "celebration" -> R.string.trigger_celebration
                else -> R.string.trigger_other
            }
            val isSelected = triggerKey in selectedTriggers
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (isSelected) WarmAmber.copy(alpha = 0.12f) else Color.Transparent
                    )
                    .clickable { onTriggerToggled(triggerKey) }
                    .padding(vertical = 6.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { onTriggerToggled(triggerKey) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = WarmAmber,
                        uncheckedColor = MutedLilac
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(labelRes),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CreamWhite
                )
            }
        }
    }
}

@Composable
fun ReasonsStepContent(reasons: String, onReasonsChanged: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.onboarding_reasons_label),
            style = MaterialTheme.typography.titleLarge,
            color = CreamWhite,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "${reasons.trim().length}/20 min",
            style = MaterialTheme.typography.labelSmall,
            color = if (reasons.trim().length >= 20) SageGreen else MutedLilac,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = reasons,
            onValueChange = onReasonsChanged,
            placeholder = { Text(stringResource(R.string.onboarding_reasons_hint), color = DimText) },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            minLines = 4,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
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
}

@Composable
fun MentorStepContent(
    selectedType: MentorType,
    onTypeSelected: (MentorType) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.onboarding_mentor_label),
            style = MaterialTheme.typography.titleLarge,
            color = CreamWhite,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        listOf(
            Triple(MentorType.COACH, R.string.mentor_coach, R.string.mentor_coach_desc),
            Triple(MentorType.FRIEND, R.string.mentor_friend, R.string.mentor_friend_desc),
            Triple(MentorType.SAGE, R.string.mentor_sage, R.string.mentor_sage_desc)
        ).forEach { (type, nameRes, descRes) ->
            MentorTypeCard(
                name = stringResource(nameRes),
                description = stringResource(descRes),
                isSelected = selectedType == type,
                emoji = when (type) {
                    MentorType.COACH -> "💪"
                    MentorType.FRIEND -> "🤝"
                    MentorType.SAGE -> "🦉"
                },
                onClick = { onTypeSelected(type) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun MentorTypeCard(
    name: String,
    description: String,
    isSelected: Boolean,
    emoji: String,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) WarmAmber else MidnightSurfaceVar
    val bgColor = if (isSelected) WarmAmber.copy(alpha = 0.1f) else MidnightSurface
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = emoji, fontSize = 32.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, style = MaterialTheme.typography.titleMedium, color = CreamWhite)
            Text(text = description, style = MaterialTheme.typography.bodySmall, color = MutedLilac)
        }
        if (isSelected) {
            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = WarmAmber)
        } else {
            Icon(Icons.Outlined.Circle, contentDescription = null, tint = MidnightSurfaceVar)
        }
    }
}
