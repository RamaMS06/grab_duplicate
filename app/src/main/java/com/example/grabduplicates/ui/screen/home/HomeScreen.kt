package com.example.grabduplicates.ui.screen.home

import RAText
import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.grabduplicates.ui.theme.RAColor
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.grabduplicates.state.AppState
import com.example.grabduplicates.util.dropShadow

@SuppressLint("ContextCastToActivity")
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {

    val activity = LocalContext.current as? ComponentActivity ?: return
    val searchValue by viewModel.searchValue.collectAsState()

    SideEffect {
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                RAColor.Primary.toArgb(),
                RAColor.Primary.toArgb()
            ),
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // body background
        ) {
            if (AppState.tooltipVisible)
                LeftArrowTooltip(
                    "Swipe to pay",
                    modifier = Modifier.padding(start = 4.dp)
                )
            // Paint only the status bar area
            Box(
                modifier = Modifier
                    .windowInsetsTopHeight(WindowInsets.statusBars)
                    .fillMaxWidth()
                    .background(RAColor.Primary)
            )

            HomeHeader(viewModel, searchValue, innerPadding)

            HomeMenu(viewModel)
        }
    }
}

@Composable
fun LeftArrowTooltip(
    text: String,
    modifier: Modifier = Modifier
) {
    val arrowWidth = 8.dp
    val arrowHeight = 8.dp
    val backgroundColor = Color(0xFF61BD79)

    // Horizontal floating animation
    val infiniteTransition = rememberInfiniteTransition()
    val offsetX = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 5f, // move 10px right
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .offset(x = offsetX.value.dp)
    ) {
        Box(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            // Arrow
            Box(
                modifier = Modifier
                    .size(arrowWidth, arrowHeight)
                    .align(Alignment.CenterStart)
                    .drawBehind {
                        val path = Path().apply {
                            moveTo(size.width, 0f) // top right
                            lineTo(0f, size.height / 2) // middle left
                            lineTo(size.width, size.height) // bottom right
                            close()
                        }
                        drawPath(path, color = backgroundColor)
                    }
            )

            // Tooltip box
            Box(
                modifier = Modifier
                    .padding(start = arrowWidth)
                    .size(135.dp, 45.dp)
                    .dropShadow(
                        shape = RoundedCornerShape(10.dp),
                        color = RAColor.Dark.copy(alpha = 0.3f),
                        blur = 10.dp,
                        offsetY = 4.dp,
                        offsetX = 0.dp,
                        spread = 0.dp
                    )
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                RAText(
                    text = text,
                    color = RAColor.White,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }
        }
    }
}
