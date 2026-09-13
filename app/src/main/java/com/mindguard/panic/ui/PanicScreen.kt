package com.mindguard.panic.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.mindguard.R
import com.mindguard.core.ui.theme.*

@Composable
fun PanicScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var secondsRemaining by remember { mutableIntStateOf(200) }
    var isComplete by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (secondsRemaining > 0) {
            delay(1000L)
            secondsRemaining--
        }
        isComplete = true
    }

    val infiniteTransition = rememberInfiniteTransition(label = "panic_bg")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            tween(2000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ), label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        PanicRed.copy(alpha = pulseAlpha * 0.4f),
                        MidnightInk
                    )
                )
            )
            .filmGrain(0.06f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top: back + title
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (isComplete) {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = CreamWhite
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.panic_title),
                    style = MaterialTheme.typography.headlineLarge.copy(letterSpacing = 3.sp),
                    color = PanicRed,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = if (isComplete)
                        stringResource(R.string.panic_complete)
                    else
                        stringResource(R.string.panic_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MutedLilac,
                    textAlign = TextAlign.Center
                )
            }

            // Center: timer
            Box(
                modifier = Modifier.size(200.dp),
                contentAlignment = Alignment.Center
            ) {
                // Pulsing circle
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(PanicRed.copy(alpha = pulseAlpha * 0.15f))
                )
                Box(
                    modifier = Modifier
                        .size(170.dp)
                        .clip(CircleShape)
                        .background(MidnightSurface)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = secondsRemaining.toString(),
                        style = MaterialTheme.typography.displayLarge,
                        color = if (isComplete) SageGreen else PanicRed
                    )
                    Text(
                        text = "sec",
                        style = MaterialTheme.typography.labelLarge,
                        color = MutedLilac
                    )
                }
            }

            // Bottom: emergency buttons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    stringResource(R.string.panic_emergency),
                    style = MaterialTheme.typography.labelMedium,
                    color = MutedLilac
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Call button
                    ElevatedButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:112")
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = PanicRed.copy(alpha = 0.2f),
                            contentColor = PanicRed
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.Call, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text(stringResource(R.string.panic_call_emergency))
                    }

                    // Camera button
                    ElevatedButton(
                        onClick = {
                            val intent = Intent("android.media.action.STILL_IMAGE_CAMERA").apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MidnightSurface,
                            contentColor = CreamWhite
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text(stringResource(R.string.panic_open_camera))
                    }
                }

                if (isComplete) {
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SageGreen,
                            contentColor = MidnightInk
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.common_ok))
                    }
                }
            }
        }
    }
}
