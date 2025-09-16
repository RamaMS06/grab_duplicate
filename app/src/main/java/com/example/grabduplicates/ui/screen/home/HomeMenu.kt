package com.example.grabduplicates.ui.screen.home

import RAText
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun HomeMenu(viewModel: HomeViewModel){
    val columns = 4
    val itemHeight = 100.dp

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .heightIn( max =
                ((viewModel.listOfMenuItem.size + columns - 1) / columns) * itemHeight
            )
            .padding(horizontal = 24.dp),
        columns = GridCells.Fixed(4),
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(viewModel.listOfMenuItem.size) { index ->
            val data = viewModel.listOfMenuItem[index]
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(data.image),
                    contentDescription = "Image of ${data.title}",
                    modifier = Modifier.size(55.dp)
                )
                Spacer(Modifier.height(10.dp))
                RAText(data.title)
            }
        }
    }
}