package com.example.grabduplicates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.grabduplicates.navigation.AppNavHost
import com.example.grabduplicates.ui.theme.GrapDuplicatesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        GrapDuplicatesTheme {
               val controller = rememberNavController()
                AppNavHost(navController = controller)
            }
        }
    }
}