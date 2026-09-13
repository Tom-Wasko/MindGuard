package com.mindguard.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val MindGuardDarkColorScheme = darkColorScheme(
    primary = WarmAmber,
    onPrimary = MidnightInk,
    primaryContainer = Color(0xFF3D3520),
    onPrimaryContainer = WarmAmber,
    secondary = SageGreen,
    onSecondary = MidnightInk,
    secondaryContainer = Color(0xFF243326),
    onSecondaryContainer = SageGreen,
    tertiary = DustyTerracotta,
    onTertiary = MidnightInk,
    tertiaryContainer = Color(0xFF3D2820),
    onTertiaryContainer = DustyTerracotta,
    error = PanicRed,
    onError = CreamWhite,
    errorContainer = Color(0xFF3D0F1A),
    onErrorContainer = PanicRed,
    background = MidnightInk,
    onBackground = CreamWhite,
    surface = MidnightSurface,
    onSurface = CreamWhite,
    surfaceVariant = MidnightSurfaceVar,
    onSurfaceVariant = MutedLilac,
    outline = Color(0xFF4A4660),
    outlineVariant = Color(0xFF2E2D42),
    scrim = Color(0xCC000000),
    inverseSurface = CreamWhite,
    inverseOnSurface = MidnightInk,
    inversePrimary = Color(0xFF7B5E2A)
)

val MindGuardLightColorScheme = lightColorScheme(
    primary = EucalyptusGreen,
    onPrimary = PaperCream,
    primaryContainer = Color(0xFFD0E8DC),
    onPrimaryContainer = InkSlate,
    secondary = CaramelMilk,
    onSecondary = PaperCream,
    secondaryContainer = Color(0xFFEDD5C0),
    onSecondaryContainer = InkSlate,
    background = PaperCream,
    onBackground = InkSlate,
    surface = PaperSurface,
    onSurface = InkSlate,
    surfaceVariant = Color(0xFFDDD5CE),
    onSurfaceVariant = WarmGray,
    error = PanicRed,
    onError = CreamWhite
)

@Composable
fun MindGuardTheme(
    darkTheme: Boolean = true, // Default to dark for lo-fi aesthetic
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) MindGuardDarkColorScheme else MindGuardLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MindGuardTypography,
        content = content
    )
}
