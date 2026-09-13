package com.mindguard.mentor.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mindguard.R
import com.mindguard.core.ui.theme.*
import com.mindguard.mentor.domain.model.MessageRole
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MentorChatScreen(
    onBack: () -> Unit,
    viewModel: MentorChatViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(state.messages.size, state.streamingMessage) {
        if (state.messages.isNotEmpty() || state.streamingMessage.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size)
        }
    }

    AnimatedLofiBackground {
        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🦉", fontSize = 24.sp)
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(
                                    stringResource(R.string.mentor_title),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = CreamWhite
                                )
                                Text(
                                    if (state.isLoading) stringResource(R.string.mentor_thinking)
                                    else "Online",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (state.isLoading) WarmAmber else SageGreen
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.common_close),
                                tint = CreamWhite
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MidnightInk.copy(alpha = 0.85f)
                    )
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .background(MidnightSurface)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .navigationBarsPadding()
                        .imePadding(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    OutlinedTextField(
                        value = state.inputText,
                        onValueChange = viewModel::updateInput,
                        placeholder = {
                            Text(
                                stringResource(R.string.mentor_input_placeholder),
                                color = DimText,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = { viewModel.sendMessage() }),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WarmAmber,
                            unfocusedBorderColor = MidnightSurfaceVar,
                            focusedTextColor = CreamWhite,
                            unfocusedTextColor = CreamWhite,
                            cursorColor = WarmAmber,
                            focusedContainerColor = MidnightSurface,
                            unfocusedContainerColor = MidnightSurface
                        ),
                        shape = RoundedCornerShape(20.dp),
                        maxLines = 4
                    )
                    Spacer(Modifier.width(8.dp))
                    IconButton(
                        onClick = viewModel::sendMessage,
                        enabled = state.inputText.isNotBlank() && !state.isLoading,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                if (state.inputText.isNotBlank() && !state.isLoading)
                                    WarmAmber else MidnightSurfaceVar
                            )
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = stringResource(R.string.mentor_send),
                            tint = if (state.inputText.isNotBlank() && !state.isLoading)
                                MidnightInk else MutedLilac
                        )
                    }
                }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .filmGrain(0.04f)
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(state.messages) { message ->
                    ChatBubble(
                        content = message.content,
                        isUser = message.role == MessageRole.USER
                    )
                }
                // Streaming response
                if (state.isLoading && state.streamingMessage.isNotEmpty()) {
                    item {
                        ChatBubble(content = state.streamingMessage, isUser = false, isStreaming = true)
                    }
                } else if (state.isLoading) {
                    item { TypingIndicator() }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(content: String, isUser: Boolean, isStreaming: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp, topEnd = 16.dp,
                        bottomStart = if (isUser) 16.dp else 4.dp,
                        bottomEnd = if (isUser) 4.dp else 16.dp
                    )
                )
                .background(if (isUser) WarmAmber.copy(alpha = 0.9f) else MidnightSurface)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = content + if (isStreaming) "|" else "",
                style = MaterialTheme.typography.bodyMedium,
                color = if (isUser) MidnightInk else CreamWhite
            )
        }
    }
}

@Composable
fun TypingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "typing")
    val dot1 by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(600),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse,
            initialStartOffset = androidx.compose.animation.core.StartOffset(0)
        ), label = "d1"
    )
    val dot2 by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(600),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse,
            initialStartOffset = androidx.compose.animation.core.StartOffset(200)
        ), label = "d2"
    )
    val dot3 by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(600),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse,
            initialStartOffset = androidx.compose.animation.core.StartOffset(400)
        ), label = "d3"
    )
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 4.dp))
            .background(MidnightSurface)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf(dot1, dot2, dot3).forEach { alpha ->
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(MutedLilac.copy(alpha = alpha))
            )
        }
    }
}
