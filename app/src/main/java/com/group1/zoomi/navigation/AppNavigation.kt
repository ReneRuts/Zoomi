package com.group1.zoomi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.group1.zoomi.ui.screens.LoginScreen
import com.group1.zoomi.ui.home.OverviewScreen
//import com.group1.zoomi.ui.screens.DetailsScreen


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
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("overview") { inclusive = true}
                    }
                }

//                getDetails = {
//                    navController.navigate("details") {
//                        popUpTo("overview") {inclusive = true}
//                    }
//                }
            )
        }

//        composable("details") {
//            DetailsScreen()
    //
//        }
    }
}
