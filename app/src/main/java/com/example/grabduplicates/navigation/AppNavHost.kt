package com.example.grabduplicates.navigation

import LoginScreen
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.grabduplicates.ui.screen.otp.OTPScreen
import com.example.grabduplicates.ui.screen.splash.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String = Routes.Otp){
    NavHost(navController = navController, startDestination = startDestination
    , enterTransition = {
            slideIntoContainer(
                towards = SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = SlideDirection.Right,
                animationSpec = tween(400)
            )
        }
    ){

        composable(Routes.Splash, enterTransition = {
            EnterTransition.None
        }, exitTransition = {
            ExitTransition.None
        }){
            SplashScreen {
                navController.navigate(Routes.Login){
                    popUpTo(Routes.Splash) {
                        inclusive = true
                    }
                }
            }
        }
        composable(Routes.Login, enterTransition = {
            EnterTransition.None
        }, exitTransition = {
            ExitTransition.None
        }){
            LoginScreen(navController)
        }
        composable(Routes.Otp){
            OTPScreen(navController)
        }
    }
}