package com.group1.zoomi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.group1.zoomi.ui.login.LoginScreen
import com.group1.zoomi.ui.home.OverviewScreen
import com.group1.zoomi.ui.workout.WorkoutEntryScreen
import com.group1.zoomi.ui.detail.DetailsEntryScreen



@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("overview") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("overview") {
            OverviewScreen(
                navController = navController,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("overview") { inclusive = true}
                    }
                },
                onAddWorkoutClick = {
                    navController.navigate("addWorkout")
                }
            )
        }
        composable("addWorkout") {
            WorkoutEntryScreen(
                navigateBack = {
                    navController.navigate("overview") {
                        popUpTo("addWorkout") { inclusive = true }
                    }
                }
            )
        }
        composable("workoutDetails/{workoutId}") { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getString("workoutId")?.toIntOrNull()
            DetailsEntryScreen(
                workoutId = workoutId ?: 0,
                navigateBack = {
                    navController.navigate("overview") {
                        popUpTo("workoutDetails/{workoutId}") { inclusive = true }
                    }
                }
            )
        }
    }


}
