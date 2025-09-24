package com.example.grabduplicates.ui.screen.home

import RAText
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.grabduplicates.R
import com.example.grabduplicates.ui.theme.RAColor
import com.example.grabduplicates.util.dropShadow
import kotlinx.coroutines.delay

@Composable
fun HomeHeader(viewModel: HomeViewModel, searchValue: String, innerPadding: PaddingValues) {
    val searchValue by viewModel.searchValue.collectAsState()
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(color = RAColor.Primary)
        )

        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            SearchTextField(
                searchValue,
                suggestions = viewModel.listOfSuggestionsSearch,
                onValueChange = { newValue ->
                    viewModel.setSearchValue(newValue)
                }
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    suggestions: List<String> = listOf(),
    modifier: Modifier = Modifier,
) {

    var currentIndex by remember { mutableIntStateOf(0) }
    val roundCorner = 10.dp
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            currentIndex = (currentIndex + 1) % suggestions.size
        }
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = RAFont.body,
        cursorBrush = SolidColor(RAColor.Dark),
        modifier = modifier
            .focusRequester(focusRequester)
            .dropShadow(
                shape = RoundedCornerShape(roundCorner),
                color = RAColor.Dark.copy(alpha = 0.05f),
                blur = 4.dp,
                offsetY = 4.dp,
                offsetX = 0.dp,
                spread = 0.dp
            )
            .clip(RoundedCornerShape(roundCorner))
            .background(RAColor.White)
            .fillMaxWidth()
            .height(54.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .height(IntrinsicSize.Max)
                    .width(IntrinsicSize.Max)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Icon Search"
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.weight(1f)
                ) {
                    innerTextField()
                    if (value.isEmpty()) {
                        AnimatedContent(
                            targetState = currentIndex,
                            transitionSpec = {
                                slideInVertically(
                                    animationSpec = tween(500),
                                    initialOffsetY = { height -> height }
                                ) with slideOutVertically(
                                    animationSpec = tween(500),
                                    targetOffsetY = { height -> -height }
                                )
                            },
                            label = "PlaceholderAnimation"
                        ) { index ->
                            RAText(text = suggestions[index],
                                color = RAColor.Grey)
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    VerticalDivider(thickness = 1.dp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_scan),
                        contentDescription = "Icon Scanner"
                    )
                }
            }
        }
    )
}

