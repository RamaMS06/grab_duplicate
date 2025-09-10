package com.example.grabduplicates.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.grabduplicates.R
import com.example.grabduplicates.ui.theme.RAColor
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(onTimeOut: () -> Unit){
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeOut()
    }
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().background(color = RAColor.Primary),
        contentAlignment = Alignment.Center,
    ){
        val w = maxWidth
        val logoWidth = w * 0.28f

        Image(
            painter = painterResource(id = R.drawable.grab_logo),
            modifier = Modifier.size(width = logoWidth, height = 47.dp).offset(y = (-50).dp),
            contentDescription = "Logo"
        )

        Image(
            painter = painterResource(id = R.drawable.footer_splash),
            contentDescription = "Footer",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}