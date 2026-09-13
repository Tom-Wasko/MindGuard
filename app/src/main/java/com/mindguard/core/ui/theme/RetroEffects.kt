package com.mindguard.core.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import kotlin.random.Random

/**
 * Adds a procedural film grain effect overlay.
 * Works on all Android versions (API 26+).
 * @param alpha grain opacity (default 0.04f = 4%)
 */
fun Modifier.filmGrain(alpha: Float = 0.04f): Modifier = this.drawWithContent {
    drawContent()
    val random = Random(System.currentTimeMillis() / 80)
    val dotCount = (size.width * size.height * 0.0015f).toInt().coerceIn(200, 3000)
    for (i in 0 until dotCount) {
        val x = random.nextFloat() * size.width
        val y = random.nextFloat() * size.height
        val radius = random.nextFloat() * 1.2f + 0.3f
        drawCircle(
            color = Color.Black.copy(alpha = alpha * (0.5f + random.nextFloat() * 0.5f)),
            radius = radius,
            center = Offset(x, y)
        )
    }
}

/**
 * Adds a vignette effect (darkened edges) to focus attention on center.
 */
fun Modifier.vignette(intensity: Float = 0.6f): Modifier = this.drawWithContent {
    drawContent()
    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(
                Color.Transparent,
                Color.Transparent,
                MidnightInk.copy(alpha = intensity)
            ),
            center = Offset(size.width / 2f, size.height / 2f),
            radius = maxOf(size.width, size.height) * 0.72f
        )
    )
}

/**
 * Adds a warm amber glow around an element.
 */
fun Modifier.warmGlow(color: Color = WarmAmber, radius: Float = 60f, alpha: Float = 0.15f): Modifier =
    this.drawWithContent {
        drawContent()
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(color.copy(alpha = alpha), Color.Transparent),
                center = Offset(size.width / 2f, size.height / 2f),
                radius = radius.dp.toPx()
            )
        )
    }

/**
 * Animated Lo-Fi gradient background.
 * Creates a slowly shifting gradient effect reminiscent of lo-fi aesthetics.
 */
@Composable
fun AnimatedLofiBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "lofi_bg")

    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradient_offset"
    )

    val alpha1 by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha1"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .drawWithContent {
                // Base dark background
                drawRect(color = MidnightInk)
                // Animated warm gradient overlay
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            SoftLavender.copy(alpha = alpha1 * 0.4f),
                            WarmAmber.copy(alpha = alpha1 * 0.2f),
                            SageGreen.copy(alpha = alpha1 * 0.15f),
                            MidnightSurface.copy(alpha = 0.8f)
                        ),
                        start = Offset(size.width * offset, 0f),
                        end = Offset(size.width * (1f - offset), size.height)
                    )
                )
                drawContent()
            }
    ) {
        content()
    }
}

/**
 * Pulsing breathing animation dot for retro lofi feel on screen.
 */
@Composable
fun PulsingDot(
    color: Color = WarmAmber,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )
    Box(
        modifier = modifier
            .background(
                color = color.copy(alpha = alpha),
                shape = CircleShape
            )
    )
}
