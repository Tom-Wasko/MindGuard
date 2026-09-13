package com.mindguard.cognitive.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mindguard.R
import com.mindguard.cognitive.domain.model.TaskType
import com.mindguard.core.ui.theme.*
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.text.KeyboardOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CognitiveTaskScreen(
    onBack: () -> Unit,
    viewModel: CognitiveTaskViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    AnimatedLofiBackground {
        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                stringResource(R.string.tasks_title),
                                style = MaterialTheme.typography.titleLarge,
                                color = CreamWhite
                            )
                            Text(
                                stringResource(R.string.tasks_streak, state.streak),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (state.streak > 0) SageGreen else MutedLilac
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = CreamWhite
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MidnightInk.copy(alpha = 0.85f)
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .filmGrain()
                    .padding(padding)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(Modifier.height(8.dp))

                state.currentTask?.let { task ->
                    // Task card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MidnightSurface)
                            .border(1.dp, WarmAmber.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                            .padding(20.dp)
                    ) {
                        Column {
                            // Task type badge
                            Text(
                                text = when (task.type) {
                                    TaskType.TRUE_FALSE -> "TRUE / FALSE"
                                    TaskType.MULTIPLE_CHOICE -> "MULTIPLE CHOICE"
                                    TaskType.OPEN_NUMERIC -> "OPEN ANSWER"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = WarmAmber
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = task.questionPl,
                                style = MaterialTheme.typography.bodyLarge,
                                color = CreamWhite
                            )
                        }
                    }

                    // Answer section
                    AnimatedContent(
                        targetState = state.feedbackState,
                        label = "feedback"
                    ) { feedback ->
                        if (feedback == FeedbackState.NONE) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                when (task.type) {
                                    TaskType.TRUE_FALSE -> {
                                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                            TrueFalseButton(
                                                label = stringResource(R.string.tasks_true),
                                                selected = state.userAnswer == "true",
                                                onClick = { viewModel.updateAnswer("true"); viewModel.submitAnswer() },
                                                modifier = Modifier.weight(1f)
                                            )
                                            TrueFalseButton(
                                                label = stringResource(R.string.tasks_false),
                                                selected = state.userAnswer == "false",
                                                onClick = { viewModel.updateAnswer("false"); viewModel.submitAnswer() },
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                    TaskType.MULTIPLE_CHOICE -> {
                                        task.options?.forEach { option ->
                                            val letter = option.substringBefore(")").trim()
                                            MCOptionButton(
                                                text = option,
                                                selected = state.userAnswer == letter,
                                                onClick = { viewModel.updateAnswer(letter); viewModel.submitAnswer() }
                                            )
                                        }
                                    }
                                    TaskType.OPEN_NUMERIC -> {
                                        OutlinedTextField(
                                            value = state.userAnswer,
                                            onValueChange = viewModel::updateAnswer,
                                            placeholder = { Text(stringResource(R.string.tasks_answer_placeholder), color = DimText) },
                                            modifier = Modifier.fillMaxWidth(),
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = WarmAmber,
                                                unfocusedBorderColor = MidnightSurfaceVar,
                                                focusedTextColor = CreamWhite,
                                                unfocusedTextColor = CreamWhite,
                                                cursorColor = WarmAmber
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        Button(
                                            onClick = viewModel::submitAnswer,
                                            enabled = state.userAnswer.isNotBlank(),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = WarmAmber,
                                                contentColor = MidnightInk
                                            ),
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text(stringResource(R.string.tasks_submit))
                                        }
                                    }
                                }
                            }
                        } else {
                            // Feedback card
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (feedback == FeedbackState.CORRECT)
                                                SageGreen.copy(alpha = 0.15f)
                                            else PanicRed.copy(alpha = 0.15f)
                                        )
                                        .border(
                                            1.dp,
                                            if (feedback == FeedbackState.CORRECT) SageGreen else PanicRed,
                                            RoundedCornerShape(12.dp)
                                        )
                                        .padding(16.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = if (feedback == FeedbackState.CORRECT)
                                                stringResource(R.string.tasks_correct)
                                            else stringResource(R.string.tasks_incorrect, task.correctAnswer),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = if (feedback == FeedbackState.CORRECT) SageGreen else PanicRed
                                        )
                                        if (task.explanationPl.isNotBlank()) {
                                            Spacer(Modifier.height(8.dp))
                                            Text(
                                                text = stringResource(R.string.tasks_explanation, task.explanationPl),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MutedLilac
                                            )
                                        }
                                    }
                                }
                                Button(
                                    onClick = viewModel::nextTask,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = WarmAmber,
                                        contentColor = MidnightInk
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(stringResource(R.string.tasks_next))
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun TrueFalseButton(label: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) WarmAmber else MidnightSurface,
            contentColor = if (selected) MidnightInk else CreamWhite
        ),
        border = BorderStroke(1.dp, WarmAmber.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(label, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun MCOptionButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) WarmAmber.copy(alpha = 0.15f) else MidnightSurface)
            .border(1.dp, if (selected) WarmAmber else MidnightSurfaceVar, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge, color = CreamWhite)
    }
}
