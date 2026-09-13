package com.mindguard.mindfulness.ui.components

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindguard.R
import com.mindguard.core.ui.theme.*
import com.mindguard.mindfulness.domain.model.BreathingPattern
import com.mindguard.mindfulness.domain.model.BreathingShape
import kotlinx.coroutines.delay

enum class BreathPhase { INHALE, HOLD_IN, EXHALE, HOLD_OUT }

@Composable
fun BreathingAnimation(
    pattern: BreathingPattern,
    modifier: Modifier = Modifier,
    onComplete: () -> Unit = {}
) {
    val context = LocalContext.current
    var phase by remember { mutableStateOf(BreathPhase.INHALE) }
    var phaseCountdown by remember { mutableIntStateOf(pattern.inhaleDuration) }
    var cyclesRemaining by remember { mutableIntStateOf(pattern.cycles) }

    val phaseColor by animateColorAsState(
        targetValue = when (phase) {
            BreathPhase.INHALE -> InhaleBlue
            BreathPhase.HOLD_IN, BreathPhase.HOLD_OUT -> HoldYellow
            BreathPhase.EXHALE -> ExhaleGreen
        },
        animationSpec = tween(800),
        label = "phase_color"
    )

    val animatedScale by animateFloatAsState(
        targetValue = when (phase) {
            BreathPhase.INHALE, BreathPhase.HOLD_IN -> 1.0f
            BreathPhase.EXHALE, BreathPhase.HOLD_OUT -> 0.55f
        },
        animationSpec = when (phase) {
            BreathPhase.INHALE -> tween((pattern.inhaleDuration * 1000), easing = FastOutSlowInEasing)
            BreathPhase.HOLD_IN, BreathPhase.HOLD_OUT -> tween(300)
            BreathPhase.EXHALE -> tween((pattern.exhaleDuration * 1000), easing = FastOutSlowInEasing)
        },
        label = "breath_scale"
    )

    // Timer coroutine
    LaunchedEffect(phase) {
        val duration = when (phase) {
            BreathPhase.INHALE -> pattern.inhaleDuration
            BreathPhase.HOLD_IN -> pattern.holdDuration
            BreathPhase.EXHALE -> pattern.exhaleDuration
            BreathPhase.HOLD_OUT -> if (pattern.shape == BreathingShape.SQUARE) pattern.holdDuration else 0
        }
        // Haptic on phase change
        hapticPulse(context)

        phaseCountdown = duration
        for (i in duration downTo 1) {
            phaseCountdown = i
            delay(1000L)
        }
        // Advance phase
        phase = when (phase) {
            BreathPhase.INHALE -> BreathPhase.HOLD_IN
            BreathPhase.HOLD_IN -> BreathPhase.EXHALE
            BreathPhase.EXHALE -> {
                if (pattern.shape == BreathingShape.SQUARE) BreathPhase.HOLD_OUT
                else {
                    // End of cycle
                    if (cyclesRemaining <= 1) {
                        onComplete()
                        return@LaunchedEffect
                    } else {
                        cyclesRemaining--
                        BreathPhase.INHALE
                    }
                }
            }
            BreathPhase.HOLD_OUT -> {
                if (cyclesRemaining <= 1) {
                    onComplete()
                    return@LaunchedEffect
                } else {
                    cyclesRemaining--
                    BreathPhase.INHALE
                }
            }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Phase label
        Text(
            text = when (phase) {
                BreathPhase.INHALE -> stringResource(R.string.mindfulness_inhale)
                BreathPhase.HOLD_IN, BreathPhase.HOLD_OUT -> stringResource(R.string.mindfulness_hold)
                BreathPhase.EXHALE -> stringResource(R.string.mindfulness_exhale)
            },
            style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 4.sp),
            color = phaseColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Animated breathing shape
        Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            if (pattern.shape == BreathingShape.CIRCLE) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = (size.minDimension / 2f) * animatedScale
                    drawCircle(
                        color = phaseColor.copy(alpha = 0.2f),
                        radius = radius
                    )
                    drawCircle(
                        color = phaseColor,
                        radius = radius,
                        style = Stroke(width = 3.dp.toPx())
                    )
                    // Inner glow dot
                    drawCircle(
                        color = phaseColor.copy(alpha = 0.8f),
                        radius = 6.dp.toPx()
                    )
                }
            } else {
                // Square (Box breathing)
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val side = (size.minDimension * 0.85f) * animatedScale
                    val left = (size.width - side) / 2
                    val top = (size.height - side) / 2
                    drawRect(
                        color = phaseColor.copy(alpha = 0.2f),
                        topLeft = Offset(left, top),
                        size = Size(side, side)
                    )
                    drawRect(
                        color = phaseColor,
                        topLeft = Offset(left, top),
                        size = Size(side, side),
                        style = Stroke(width = 3.dp.toPx())
                    )
                }
            }

            // Countdown number in center
            Text(
                text = "$phaseCountdown",
                style = MaterialTheme.typography.displaySmall,
                color = CreamWhite,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cycles remaining
        Text(
            text = stringResource(R.string.breathing_cycles_remaining, cyclesRemaining),
            style = MaterialTheme.typography.labelMedium,
            color = MutedLilac
        )
    }
}

fun hapticPulse(context: Context) {
    try {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE))
        }
    } catch (_: Exception) {}
}
