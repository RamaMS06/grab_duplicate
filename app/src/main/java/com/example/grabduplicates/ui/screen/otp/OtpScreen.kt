package com.example.grabduplicates.ui.screen.otp

import RAText
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.grabduplicates.R
import com.example.grabduplicates.navigation.Routes
import com.example.grabduplicates.ui.components.loading.RALoading
import com.example.grabduplicates.ui.theme.RAColor
import kotlinx.coroutines.delay

@Composable
fun OTPScreen(navController: NavHostController, viewModel: OtpViewModel = viewModel()) {
    Scaffold(
        containerColor = RAColor.White,
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(12.dp)
                .fillMaxSize()
        ) {
            val otpValue by viewModel.otpValue.collectAsState()
            val controller = LocalSoftwareKeyboardController.current
            val isLoadingOtp by viewModel.isLoadingOtp.collectAsState()
            val isFailure by viewModel.isFailure.collectAsState()
            val isSuccess by viewModel.isSuccess.collectAsState()
            val showClearText by viewModel.showClearText.collectAsState()

            // Handling Logic
            LaunchedEffect(isLoadingOtp) {
                if (isLoadingOtp) {
                    delay(1500)
                    viewModel.setIsLoadingOtp(false)
                }
                if (otpValue != "") {
                    if (otpValue != "123123") {
                        viewModel.setFailure(true)
                        viewModel.setOtpValue("")
                        controller?.show()
                    } else {
                        viewModel.setSuccess(true)
                    }
                }
            }

            LaunchedEffect(isSuccess) {
                delay(1000)
                if (isSuccess) {
                    navController.navigate(Routes.Phone) {
                        popUpTo(Routes.Phone) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }

            if (isLoadingOtp)
                RALoading()

            Column {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(30.dp)
                        .offset(x = (-10).dp)
                )
                RAText(
                    "Enter the 6-digit code sent to :",
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                RAText(
                    "The code will appear in your device",
                    variant = RATextVariant.BodySmall
                )
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OtpTextField(
                        value = otpValue,
                        onValueChange = { newValue ->
                            viewModel.setOtpValue(newValue)
                            if (newValue != "" || newValue.isNotBlank()) {
                                viewModel.setFailure(false)
                                viewModel.setSuccess(false)
                                if (newValue.length >= 6) {
                                    viewModel.setIsLoadingOtp(true)
                                    controller?.hide()
                                }
                            }
                        },
                    )

                    OtpStatusIcon(
                        showClearText = showClearText,
                        isSuccess = isSuccess,
                        isFailure = isFailure
                    ) {
                        viewModel.clearOtp()
                    }
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                RAText("Didn't receive it?")
                Spacer(modifier = Modifier.height(14.dp))
                CountdownTimer()
                Spacer(modifier = Modifier.height(26.dp))
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OtpStatusIcon(
    showClearText: Boolean,
    isSuccess: Boolean,
    isFailure: Boolean,
    onClear: () -> Unit
) {
    // Decide the current "state"
    val state = when {
        showClearText && (!isSuccess && !isFailure) -> "clear"
        isSuccess -> "success"
        isFailure -> "failure"
        else -> "none"
    }

    AnimatedContent(
        targetState = state,
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(150)
            ).togetherWith(
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(150)
                )
            )
        },
        label = "OtpStateAnimation"
    ) { target ->
        when (target) {
            "clear" -> {
                Image(
                    painter = painterResource(R.drawable.ic_cross),
                    contentDescription = "Remove Otp",
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .size(32.dp)
                        .clickable { onClear() }
                )
            }

            "success" -> {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Otp Success",
                    tint = RAColor.Primary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .size(32.dp)
                        .clickable { onClear() }
                )
            }

            "failure" -> {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Otp Failure",
                    tint = Color.Red,
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .size(32.dp)
                        .clickable { onClear() }
                )
            }
        }
    }

}

@SuppressLint("DefaultLocale")
@Composable
fun CountdownTimer() {
    var timeLeft by remember { mutableIntStateOf(60) }
    var restartKey by remember { mutableIntStateOf(0) }

    LaunchedEffect(restartKey) {
        timeLeft = 60
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
    }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val timeText = String.format("%02d:%02d", minutes, seconds)

    RAText(
        if (timeLeft == 0) "Request the code here" else "Request the code here $timeText",
        variant = RATextVariant.BodyBold,
        color = if (timeLeft == 0) RAColor.Primary else RAColor.Grey,
        modifier = Modifier.clickable(timeLeft == 0, onClick = {
            if (timeLeft == 0) {
                restartKey++
            }
        })
    )
}

@Composable
fun OtpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    cursorColor: Color = Color.Black,
    maxLength: Int = 6
) {
    val transparentSelection = remember {
        TextSelectionColors(
            handleColor = Color.Black,
            backgroundColor = Color.Black
        )
    }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        delay(100)
        keyboardController?.show()
    }

    CompositionLocalProvider(LocalTextSelectionColors provides transparentSelection) {
        BasicTextField(
            value = value,
            onValueChange = { raw ->
                // Keep only digits and cap to maxLength
                val filtered = raw.filter { it.isDigit() }.take(maxLength)
                if (filtered != value) onValueChange(filtered)
            },
            singleLine = true,
            textStyle = RAFont.h1,
            cursorBrush = SolidColor(cursorColor),
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                autoCorrect = false
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = modifier
                .focusRequester(focusRequester)
                // ensure there's enough box to draw the caret even when empty
                .widthIn(min = 1.dp)
                .heightIn(min = 24.dp),
            decorationBox = { inner -> inner() } // no decoration/indicator/placeholder
        )
    }
}
