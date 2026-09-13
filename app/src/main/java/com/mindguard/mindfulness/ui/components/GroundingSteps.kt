package com.mindguard.mindfulness.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mindguard.R
import com.mindguard.core.ui.theme.*
import com.mindguard.mindfulness.domain.model.ExerciseStep

@Composable
fun GroundingStepItem(
    step: ExerciseStep,
    stepIndex: Int,
    currentTapCount: Int,
    onTap: () -> Unit,
    isCurrentStep: Boolean
) {
    val required = step.tapCount ?: 0
    val isComplete = currentTapCount >= required

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                when {
                    isComplete -> SageGreen.copy(alpha = 0.15f)
                    isCurrentStep -> WarmAmber.copy(alpha = 0.1f)
                    else -> MidnightSurface
                }
            )
            .border(
                width = 1.dp,
                color = when {
                    isComplete -> SageGreen
                    isCurrentStep -> WarmAmber
                    else -> MidnightSurfaceVar
                },
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(enabled = isCurrentStep && !isComplete) { onTap() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Step number circle
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    if (isComplete) SageGreen else if (isCurrentStep) WarmAmber else MidnightSurfaceVar
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isComplete) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = MidnightInk,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(
                    text = "${stepIndex + 1}",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isCurrentStep) MidnightInk else MutedLilac
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = step.instructionPl,  // TODO: use locale-aware string
                style = MaterialTheme.typography.bodyMedium,
                color = if (isComplete) SageGreen else if (isCurrentStep) CreamWhite else MutedLilac
            )
            if (required > 0 && isCurrentStep && !isComplete) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.mindfulness_tap_to_confirm),
                    style = MaterialTheme.typography.labelSmall,
                    color = WarmAmber
                )
                // Tap progress dots
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    repeat(required) { i ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (i < currentTapCount) WarmAmber else MidnightSurfaceVar
                                )
                        )
                    }
                }
            }
        }
    }
}
