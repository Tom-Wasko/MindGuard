package com.mindguard.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.mindguard.R
import com.mindguard.core.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    var showResetDialog by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("pl") }

    AnimatedLofiBackground {
        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.settings_title),
                            style = MaterialTheme.typography.titleLarge,
                            color = CreamWhite
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = CreamWhite
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MidnightInk.copy(alpha = 0.85f)
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .filmGrain()
                    .padding(padding)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(Modifier.height(8.dp))

                // Language section
                SettingsSection(title = stringResource(R.string.settings_language)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        listOf("pl" to "🇵🇱 Polski", "en" to "🇬🇧 English").forEach { (code, label) ->
                            FilterChip(
                                selected = selectedLanguage == code,
                                onClick = {
                                    selectedLanguage = code
                                    AppCompatDelegate.setApplicationLocales(
                                        LocaleListCompat.forLanguageTags(code)
                                    )
                                },
                                label = { Text(label) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = WarmAmber,
                                    selectedLabelColor = MidnightInk,
                                    containerColor = MidnightSurface,
                                    labelColor = CreamWhite
                                )
                            )
                        }
                    }
                }

                HorizontalDivider(color = MidnightSurfaceVar)

                // Reset Profile
                SettingsSection(title = stringResource(R.string.settings_reset_profile)) {
                    OutlinedButton(
                        onClick = { showResetDialog = true },
                        border = BorderStroke(1.dp, PanicRed),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            stringResource(R.string.settings_reset_profile),
                            color = PanicRed
                        )
                    }
                }

                HorizontalDivider(color = MidnightSurfaceVar)

                // About
                SettingsSection(title = stringResource(R.string.settings_about)) {
                    Text(
                        text = "MindGuard v1.0.0\n" +
                            "Twoja forteca uważności\n" +
                            "Built with ❤️ and Jetpack Compose",
                        style = MaterialTheme.typography.bodySmall,
                        color = MutedLilac
                    )
                }

                Spacer(Modifier.height(32.dp))
            }
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text(stringResource(R.string.settings_reset_profile), color = CreamWhite) },
            text = { Text(stringResource(R.string.settings_reset_confirm), color = MutedLilac) },
            confirmButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text(stringResource(R.string.settings_reset_yes), color = PanicRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text(stringResource(R.string.settings_reset_no), color = CreamWhite)
                }
            },
            containerColor = MidnightSurface
        )
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MidnightSurface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = WarmAmber
        )
        content()
    }
}
