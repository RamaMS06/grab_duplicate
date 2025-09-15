@file:Suppress("DEPRECATION")

package com.example.grabduplicates.ui.screen.navbar

import RAFont
import RAText
import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.grabduplicates.R
import com.example.grabduplicates.navigation.Routes
import com.example.grabduplicates.state.AppState
import com.example.grabduplicates.ui.screen.home.HomeScreen
import com.example.grabduplicates.ui.theme.RAColor
import com.example.grabduplicates.util.capitalizeFirst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class NavBarItem(
    val label: String,
    val icon: Int,
    val selectedIcon: Int,
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavBar(navController: NavController) {

    val pages = listOf(
        "Hidden",
        "Home",
        "Activity",
        "Payment",
        "Inbox",
        "Account"
    )

    val pagerState = rememberPagerState(initialPage = 1, pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableIntStateOf(pagerState.currentPage - 1) }
    val controller = LocalSoftwareKeyboardController.current

    val showNavbar = pagerState.currentPage != 0

    BackHandler(enabled = true) {} // disable back

    LaunchedEffect(pagerState.currentPage - 1) {
        selectedItem = pagerState.currentPage - 1

        Log.d("Selected", selectedItem.toString())

        if (pagerState.currentPage == 0) {
            AppState.tooltipVisible = false
            controller?.hide()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            AnimatedVisibility(
                visible = showNavbar,
                enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(300)),
                exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300))
            ) {
                BottomNavBar(pagerState, scope, selectedItem)
            }
        }
    ) { _ ->
        HorizontalPager(
            pagerState,
            modifier = Modifier.fillMaxSize(),
            beyondViewportPageCount = 2
        ) { page ->
            when (page) {
                0 -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(RAColor.Grey),
                        contentAlignment = Alignment.Center
                    ) {
                        RAText("Hidden Page")
                    }
                }

                1 -> HomeScreen(navController)
                2 -> TestScreen()
                else -> Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Page $page")
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    pagerState: androidx.compose.foundation.pager.PagerState,
    scope: CoroutineScope,
    selectedItem: Int
) {
    val pages = listOf(
        NavBarItem(Routes.Home, R.drawable.ic_home, R.drawable.ic_home_selected),
        NavBarItem(Routes.Activity, R.drawable.ic_activity, R.drawable.ic_activity_selected),
        NavBarItem(Routes.Payment, R.drawable.ic_payment, R.drawable.ic_payment_selected),
        NavBarItem(Routes.Inbox, R.drawable.ic_inbox, R.drawable.ic_inbox_selected),
        NavBarItem(Routes.Account, R.drawable.ic_account, R.drawable.ic_account_selected)
    )

    NavigationBar(
        modifier = Modifier
            .drawBehind {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = -20f,
                        endY = size.height
                    ),
                    topLeft = Offset(0f, -20f)
                )
            }
            .imePadding(),
        containerColor = RAColor.White,
    ) {
        pages.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = pagerState.currentPage - 1 == index,
                onClick = {

                    scope.launch {
                        pagerState.animateScrollToPage(index + 1)
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(
                            if (selectedItem == index) item.selectedIcon else item.icon
                        ),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        item.label.capitalizeFirst(),
                        style = if (selectedItem == index)
                            RAFont.bodySemiBold.copy(fontSize = 12.sp)
                        else RAFont.body.copy(fontSize = 12.sp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = RAColor.Dark,
                    unselectedTextColor = RAColor.Grey,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


@Composable
fun TestScreen() {
    RAText("Test Screen")
}
