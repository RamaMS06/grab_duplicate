package com.example.grabduplicates.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.grabduplicates.ui.screen.home.HomeScreen
import com.example.grabduplicates.ui.screen.splash.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String = "splash"){
    NavHost(navController = navController, startDestination = startDestination){
        composable("splash"){
            SplashScreen {
                navController.navigate("home"){
                    popUpTo("splash") {
                        inclusive = true
                    }
                }
            }
        }
        composable("home"){
            HomeScreen()
        }
    }
}