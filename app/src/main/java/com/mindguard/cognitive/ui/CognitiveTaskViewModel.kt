package com.mindguard.cognitive.ui

import androidx.lifecycle.ViewModel
import com.mindguard.cognitive.domain.model.CognitiveTask
import com.mindguard.cognitive.domain.model.TaskType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CognitiveTaskUiState(
    val currentTask: CognitiveTask? = null,
    val userAnswer: String = "",
    val feedbackState: FeedbackState = FeedbackState.NONE,
    val streak: Int = 0,
    val totalAnswered: Int = 0,
    val isLoading: Boolean = false
)

enum class FeedbackState { NONE, CORRECT, INCORRECT }

class CognitiveTaskViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CognitiveTaskUiState())
    val uiState: StateFlow<CognitiveTaskUiState> = _uiState.asStateFlow()

    private val tasks = BuiltInTaskBank.tasks.shuffled().toMutableList()
    private var taskIndex = 0

    init { loadNextTask() }

    private fun loadNextTask() {
        if (tasks.isEmpty()) return
        val task = tasks[taskIndex % tasks.size]
        _uiState.update { it.copy(currentTask = task, userAnswer = "", feedbackState = FeedbackState.NONE) }
    }

    fun updateAnswer(answer: String) {
        _uiState.update { it.copy(userAnswer = answer) }
    }

    fun submitAnswer() {
        val state = _uiState.value
        val task = state.currentTask ?: return
        val isCorrect = checkAnswer(state.userAnswer.trim(), task)
        _uiState.update {
            it.copy(
                feedbackState = if (isCorrect) FeedbackState.CORRECT else FeedbackState.INCORRECT,
                streak = if (isCorrect) it.streak + 1 else 0,
                totalAnswered = it.totalAnswered + 1
            )
        }
    }

    fun nextTask() {
        taskIndex++
        loadNextTask()
    }

    private fun checkAnswer(userAnswer: String, task: CognitiveTask): Boolean {
        return when (task.type) {
            TaskType.TRUE_FALSE -> userAnswer.lowercase() == task.correctAnswer.lowercase()
            TaskType.MULTIPLE_CHOICE -> userAnswer.uppercase() == task.correctAnswer.uppercase()
            TaskType.OPEN_NUMERIC -> {
                val ua = userAnswer.replace(",", ".").toDoubleOrNull()
                val ca = task.correctAnswer.replace(",", ".").toDoubleOrNull()
                ua != null && ca != null && Math.abs(ua - ca) < 0.01
            }
        }
    }
}
