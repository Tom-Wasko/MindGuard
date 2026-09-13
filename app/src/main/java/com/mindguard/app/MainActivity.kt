package com.mindguard.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.mindguard.app.navigation.MindGuardNavGraph
import com.mindguard.app.navigation.Screen
import com.mindguard.core.ui.theme.MindGuardTheme
import com.mindguard.onboarding.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.map
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val onboardingRepository: OnboardingRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MindGuardTheme(darkTheme = true) {
                val navController = rememberNavController()

                // Determine start destination based on onboarding status
                val hasProfile by onboardingRepository.getUserProfileFlow()
                    .map { it != null }
                    .collectAsStateWithLifecycle(initialValue = null)

                // Wait for profile check before rendering nav
                val startDestination = when (hasProfile) {
                    null -> null  // Still loading
                    false -> Screen.Onboarding.route
                    true -> Screen.Mindfulness.route
                }

                if (startDestination != null) {
                    MindGuardNavGraph(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}
