package com.mindguard.app.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mindguard.cognitive.ui.CognitiveTaskScreen
import com.mindguard.mentor.ui.MentorChatScreen
import com.mindguard.mindfulness.ui.MindfulnessScreen
import com.mindguard.onboarding.ui.OnboardingScreen
import com.mindguard.panic.ui.PanicScreen
import com.mindguard.ui.MainMenuScreen
import com.mindguard.ui.SettingsScreen

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Mindfulness : Screen("mindfulness")
    object MainMenu : Screen("main_menu")
    object Mentor : Screen("mentor")
    object CognitiveTasks : Screen("cognitive_tasks")
    object Panic : Screen("panic")
    object Settings : Screen("settings")
}

@Composable
fun MindGuardNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Mindfulness.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            fadeIn(animationSpec = tween(300)) +
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(200)) +
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(200))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300)) +
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(200)) +
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(200))
        }
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onOnboardingComplete = {
                    navController.navigate(Screen.Mindfulness.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Mindfulness.route) {
            MindfulnessScreen(
                onExerciseComplete = {
                    navController.navigate(Screen.MainMenu.route) {
                        popUpTo(Screen.Mindfulness.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.MainMenu.route) {
            MainMenuScreen(
                onNavigateToMentor = { navController.navigate(Screen.Mentor.route) },
                onNavigateToTasks = { navController.navigate(Screen.CognitiveTasks.route) },
                onNavigateToPanic = { navController.navigate(Screen.Panic.route) },
                onNavigateToMindfulness = { navController.navigate(Screen.Mindfulness.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.Mentor.route) {
            MentorChatScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.CognitiveTasks.route) {
            CognitiveTaskScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Panic.route) {
            PanicScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
