package com.example.grabduplicates.ui.screen.activity

import RAText
import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.grabduplicates.R
import com.example.grabduplicates.ui.theme.RAColor
import com.example.grabduplicates.util.dropShadow


@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(navController: NavController, viewModel: ActivityViewModel = viewModel()) {

    val activity = LocalContext.current as? ComponentActivity ?: return

    SideEffect {
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                RAColor.Primary.toArgb(),
                RAColor.Primary.toArgb()
            ),
        )
    }

    Scaffold(
        modifier = Modifier
            .background(color = RAColor.White)
            .fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .fillMaxWidth()
                .background(RAColor.White)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = RAColor.White)
                .padding(
                    start = 16.dp, end = 16.dp,
                    top = 28.dp,
                    bottom = 85.dp
                )
        ) {
            item {
                ActivityHeader()
                Spacer(Modifier.height(18.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(viewModel.listOfHeaderDataActivity.size) { index ->
                        ActivityHeaderCard(item = viewModel.listOfHeaderDataActivity[index])
                    }
                    item {
                        Spacer(Modifier.width(24.dp))
                    }
                }
                Spacer(Modifier.height(24.dp))
                RAText("Recent", variant = RATextVariant.H4)
                Spacer(Modifier.height(28.dp))

            }
            items(viewModel.listOfRecentDataActivity.size) { index ->
                ActivityRecentCard(viewModel.listOfRecentDataActivity[index])
                if (index != viewModel.listOfRecentDataActivity.lastIndex) {
                    Spacer(Modifier.height(24.dp))
                }
            }

        }
    }
}