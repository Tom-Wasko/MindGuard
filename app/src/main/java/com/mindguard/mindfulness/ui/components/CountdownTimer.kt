package com.mindguard.mindfulness.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.mindguard.core.ui.theme.*

@Composable
fun CountdownTimer(
    remainingSeconds: Int,
    totalSeconds: Int,
    modifier: Modifier = Modifier,
    showArc: Boolean = true
) {
    if (totalSeconds <= 0) return

    val progress = if (totalSeconds > 0) remainingSeconds.toFloat() / totalSeconds.toFloat() else 0f

    val timerColor by animateColorAsState(
        targetValue = when {
            progress > 0.6f -> SageGreen
            progress > 0.3f -> WarmAmber
            else -> PanicRed
        },
        animationSpec = tween(1000),
        label = "timer_color"
    )

    Box(
        modifier = modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        if (showArc) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Background track
                drawArc(
                    color = MidnightSurfaceVar,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                )
                // Progress arc
                drawArc(
                    color = timerColor,
                    startAngle = -90f,
                    sweepAngle = 360f * progress,
                    useCenter = false,
                    style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTime(remainingSeconds),
                style = MaterialTheme.typography.headlineMedium,
                color = timerColor
            )
        }
    }
}

fun formatTime(totalSeconds: Int): String {
    return if (totalSeconds >= 60) {
        val m = totalSeconds / 60
        val s = totalSeconds % 60
        "$m:${s.toString().padStart(2, '0')}"
    } else {
        "${totalSeconds}s"
    }
}
