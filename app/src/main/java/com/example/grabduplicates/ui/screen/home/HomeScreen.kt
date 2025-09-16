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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.grabduplicates.R
import com.example.grabduplicates.state.AppState
import com.example.grabduplicates.util.dropShadow

@SuppressLint("ContextCastToActivity")
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {

    val activity = LocalContext.current as? ComponentActivity ?: return
    val searchValue by viewModel.searchValue.collectAsState()
    val verticalState = rememberScrollState()

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
            // Paint only the status bar area
            Box(
                modifier = Modifier
                    .windowInsetsTopHeight(WindowInsets.statusBars)
                    .fillMaxWidth()
                    .background(RAColor.Primary)
            )

            HomeHeader(viewModel, searchValue, innerPadding)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalState)
                    .padding(top = 130.dp)
            ) {
                HomeMenu(viewModel)
                Spacer(Modifier.height(28.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CardCurrentUser(
                        modifier = Modifier.weight(1f),
                        "Activate", "GrabPay",
                        R.drawable.img_gpay
                    )
                    Spacer(Modifier.width(10.dp))
                    CardCurrentUser(
                        modifier = Modifier.weight(1f),
                        "Use Points",
                        "758",
                        R.drawable.img_crown
                    )
                }

                Spacer(Modifier.height(38.dp))

                RAText(
                    "Celebrate Mid-Autmnm Festival",
                    variant = RATextVariant.H5,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(Modifier.height(18.dp))

                Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                    CardMidAutmn(Modifier.weight(1f),
                        "Order mooncakes to gift & to enjoy",
                        "Until 21 Sep",
                        R.drawable.img_mid1)
                    Spacer(Modifier.width(12.dp))
                    CardMidAutmn(Modifier.weight(1f),
                        "Plus an Extra $20 OFF on groceries",
                        "Until 31 Aug",
                        R.drawable.img_mid2)
                }
            }

            if (AppState.tooltipVisible)
                LeftArrowTooltip(
                    "Swipe to pay",
                    modifier = Modifier.padding(start = 4.dp, bottom = 224.dp)
                )
        }
    }
}


@Composable
fun CardCurrentUser(modifier: Modifier, title: String, desc: String, img: Int) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(color = Color(0xFFF9F9F9))
            .padding(horizontal = 10.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column {
            RAText(text = title, variant = RATextVariant.BodySmall)
            Spacer(Modifier.height(6.dp))
            RAText(
                text = desc,
                variant = RATextVariant.BodySmall,
                styleOverride = RAFont.bodySmall.copy(fontWeight = FontWeight.W600)
            )
        }

        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(img),
            contentDescription = "Image $img"
        )

    }
}

@Composable
fun CardMidAutmn(modifier: Modifier, title: String, date: String, img: Int) {
    Column(modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .height(180.dp),
            painter = painterResource(img),
            contentDescription = "Img $img",
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(8.dp))
        RAText(title, variant = RATextVariant.BodySmall)
        Spacer(Modifier.height(5.dp))
        Row {
            Icon(
                painter = painterResource(R.drawable.ic_calendar),
                contentDescription = "Icon Calendar"
            )
            Spacer(Modifier.width(4.dp))
            RAText(date, variant = RATextVariant.BodyXSmall)
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
