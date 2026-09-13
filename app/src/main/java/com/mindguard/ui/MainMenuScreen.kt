package com.mindguard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindguard.R
import com.mindguard.core.ui.theme.*
import java.util.Calendar

@Composable
fun MainMenuScreen(
    onNavigateToMentor: () -> Unit,
    onNavigateToTasks: () -> Unit,
    onNavigateToPanic: () -> Unit,
    onNavigateToMindfulness: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val greeting = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when {
            hour < 12 -> "menu_greeting_morning"
            hour < 18 -> "menu_greeting_afternoon"
            else -> "menu_greeting_evening"
        }
    }

    AnimatedLofiBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .filmGrain(0.04f)
                .vignette(0.6f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(horizontal = 20.dp)
            ) {
                // TopBar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = stringResource(
                                when (greeting) {
                                    "menu_greeting_morning" -> R.string.menu_greeting_morning
                                    "menu_greeting_afternoon" -> R.string.menu_greeting_afternoon
                                    else -> R.string.menu_greeting_evening
                                }
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MutedLilac
                        )
                        Text(
                            text = "MindGuard",
                            style = MaterialTheme.typography.headlineMedium,
                            color = WarmAmber
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        PulsingDot(
                            color = SageGreen,
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        IconButton(onClick = onNavigateToSettings) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = stringResource(R.string.menu_settings),
                                tint = MutedLilac
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Feature Cards Grid
                val features = listOf(
                    FeatureCard("🧘", R.string.menu_mindfulness, WarmAmber, onNavigateToMindfulness),
                    FeatureCard("🦉", R.string.menu_mentor, SoftLavender, onNavigateToMentor),
                    FeatureCard("🧮", R.string.menu_tasks, SageGreen, onNavigateToTasks),
                    FeatureCard("🚨", R.string.menu_panic, PanicRed, onNavigateToPanic)
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    features.chunked(2).forEach { rowFeatures ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowFeatures.forEach { feature ->
                                MenuCard(
                                    emoji = feature.emoji,
                                    titleRes = feature.titleRes,
                                    accentColor = feature.color,
                                    onClick = feature.onClick,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Bottom tagline
                Text(
                    text = stringResource(R.string.app_tagline),
                    style = MaterialTheme.typography.labelSmall,
                    color = DimText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

data class FeatureCard(
    val emoji: String,
    val titleRes: Int,
    val color: androidx.compose.ui.graphics.Color,
    val onClick: () -> Unit
)

@Composable
fun MenuCard(
    emoji: String,
    titleRes: Int,
    accentColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
            .background(MidnightSurface)
            .border(1.dp, accentColor.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = emoji, fontSize = 42.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(titleRes),
                style = MaterialTheme.typography.labelLarge,
                color = CreamWhite,
                textAlign = TextAlign.Center
            )
        }
    }
}
