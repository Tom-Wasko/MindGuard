package com.mindguard.mindfulness.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindguard.mindfulness.data.MindfulnessLogDao
import com.mindguard.mindfulness.data.MindfulnessLogEntity
import com.mindguard.mindfulness.domain.model.MindfulnessExercise
import com.mindguard.mindfulness.domain.usecase.GetRandomExerciseUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MindfulnessUiState(
    val exercise: MindfulnessExercise? = null,
    val currentStepIndex: Int = 0,
    val timerRemainingSeconds: Int = 0,
    val isTimerRunning: Boolean = false,
    val isComplete: Boolean = false,
    val logId: Long? = null,
    val stepAnswers: Map<Int, String> = emptyMap(),  // stepIndex -> answer text
    val stepTapCounts: Map<Int, Int> = emptyMap()    // stepIndex -> taps so far
)

class MindfulnessViewModel(
    private val getRandomExerciseUseCase: GetRandomExerciseUseCase,
    private val mindfulnessLogDao: MindfulnessLogDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(MindfulnessUiState())
    val uiState: StateFlow<MindfulnessUiState> = _uiState.asStateFlow()

    init {
        loadExercise()
    }

    private fun loadExercise() {
        val exercise = getRandomExerciseUseCase()
        val firstStepDuration = exercise.steps.firstOrNull()?.durationSeconds ?: exercise.totalDurationSeconds ?: 0
        _uiState.update {
            it.copy(
                exercise = exercise,
                timerRemainingSeconds = firstStepDuration
            )
        }
        logStart(exercise.id)
        if (firstStepDuration > 0) startTimer()
    }

    private fun logStart(exerciseId: String) {
        viewModelScope.launch {
            val logId = mindfulnessLogDao.insertLog(
                MindfulnessLogEntity(exerciseId = exerciseId)
            )
            _uiState.update { it.copy(logId = logId) }
        }
    }

    fun recordStepAnswer(stepIndex: Int, answer: String) {
        _uiState.update { state ->
            state.copy(stepAnswers = state.stepAnswers + (stepIndex to answer))
        }
    }

    fun recordTap(stepIndex: Int): Boolean {
        val state = _uiState.value
        val exercise = state.exercise ?: return false
        val step = exercise.steps.getOrNull(stepIndex) ?: return false
        val required = step.tapCount ?: return false
        val current = (state.stepTapCounts[stepIndex] ?: 0) + 1
        _uiState.update { it.copy(stepTapCounts = state.stepTapCounts + (stepIndex to current)) }
        return current >= required  // true when step is complete
    }

    fun goToNextStep() {
        val state = _uiState.value
        val exercise = state.exercise ?: return
        val nextIndex = state.currentStepIndex + 1
        if (nextIndex >= exercise.steps.size) {
            completeExercise()
            return
        }
        val nextStep = exercise.steps[nextIndex]
        val nextDuration = nextStep.durationSeconds ?: 0
        _uiState.update {
            it.copy(
                currentStepIndex = nextIndex,
                timerRemainingSeconds = nextDuration,
                isTimerRunning = false
            )
        }
        if (nextDuration > 0) startTimer()
    }

    private fun startTimer() {
        _uiState.update { it.copy(isTimerRunning = true) }
        viewModelScope.launch {
            while (_uiState.value.isTimerRunning && _uiState.value.timerRemainingSeconds > 0) {
                delay(1000L)
                _uiState.update { it.copy(timerRemainingSeconds = (it.timerRemainingSeconds - 1).coerceAtLeast(0)) }
            }
            if (_uiState.value.timerRemainingSeconds == 0 && _uiState.value.isTimerRunning) {
                _uiState.update { it.copy(isTimerRunning = false) }
            }
        }
    }

    fun completeExercise() {
        _uiState.update { it.copy(isComplete = true, isTimerRunning = false) }
        viewModelScope.launch {
            _uiState.value.logId?.let { id ->
                mindfulnessLogDao.markCompleted(id)
            }
        }
    }

    fun skipExercise() {
        completeExercise()
    }

    override fun onCleared() {
        super.onCleared()
        _uiState.update { it.copy(isTimerRunning = false) }
    }
}
