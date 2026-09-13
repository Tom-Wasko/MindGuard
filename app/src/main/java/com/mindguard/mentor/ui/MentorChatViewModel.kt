package com.mindguard.mentor.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindguard.mentor.domain.model.ChatMessage
import com.mindguard.mentor.domain.model.MessageRole
import com.mindguard.mentor.domain.repository.MentorRepository
import com.mindguard.mentor.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class MentorChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val streamingMessage: String = ""  // Currently streaming response
)

class MentorChatViewModel(
    private val repository: MentorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MentorChatUiState())
    val uiState: StateFlow<MentorChatUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getMessagesFlow().collect { messages ->
                _uiState.update { it.copy(messages = messages) }
            }
        }
        // Insert welcome message if chat is empty
        viewModelScope.launch {
            kotlinx.coroutines.delay(500L)
            if (_uiState.value.messages.isEmpty()) {
                repository.saveMessage(
                    ChatMessage(
                        role = MessageRole.MENTOR,
                        content = "Cześć. Jestem tu dla Ciebie. Jak się teraz czujesz?"
                    )
                )
            }
        }
    }

    fun updateInput(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun sendMessage() {
        val text = _uiState.value.inputText.trim()
        if (text.isBlank() || _uiState.value.isLoading) return

        _uiState.update { it.copy(inputText = "", isLoading = true, streamingMessage = "") }

        viewModelScope.launch {
            try {
                val flow = repository.sendMessage(text, "")
                var fullResponse = ""
                flow.collect { chunk ->
                    fullResponse += chunk
                    _uiState.update { it.copy(streamingMessage = fullResponse) }
                }
                // Save complete mentor response
                if (fullResponse.isNotBlank()) {
                    repository.saveMessage(
                        ChatMessage(role = MessageRole.MENTOR, content = fullResponse)
                    )
                }
                _uiState.update { it.copy(isLoading = false, streamingMessage = "") }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, streamingMessage = "") }
            }
        }
    }
}
