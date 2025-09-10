package com.example.grabduplicates.ui.screen.otp

import RAText
import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.grabduplicates.R
import com.example.grabduplicates.ui.theme.RAColor
import kotlinx.coroutines.delay

@Composable
fun OTPScreen() {
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .padding(12.dp)
            .fillMaxSize()) {
            var otpValue by remember { mutableStateOf("") }

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
                            otpValue = newValue
                        },
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_cross),
                        contentDescription = "Remove Otp",
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .clickable(true, onClick = {
                                otpValue = ""
                                print("test")
                            })
                    )
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
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CountdownTimer() {
    var timeLeft by remember { mutableIntStateOf(60) }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
    }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val timeText = String.format("%02d:%02d", minutes, seconds)

    RAText("Request the code here $timeText", variant = RATextVariant.BodyBold, color = RAColor.Grey)
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
            singleLine = true, // so IME shows Done, not newline
            textStyle = RAFont.h1,
            cursorBrush = SolidColor(cursorColor),
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, // numeric keypad
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
