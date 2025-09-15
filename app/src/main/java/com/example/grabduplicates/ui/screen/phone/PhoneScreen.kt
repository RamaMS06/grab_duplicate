package com.example.grabduplicates.ui.screen.phone

import RAButton
import RAText
import RATextVariant
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.grabduplicates.R
import com.example.grabduplicates.navigation.Routes
import com.example.grabduplicates.ui.components.loading.RALoading
import com.example.grabduplicates.ui.theme.RAColor
import kotlinx.coroutines.delay

@Composable
fun PhoneScreen(navController: NavController, viewModel: PhoneViewModel = viewModel()) {
    Scaffold(
        containerColor = RAColor.White,
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        val phoneValue by viewModel.phoneValue.collectAsState()
        val isLoadingPhone by viewModel.isLoadingPhone.collectAsState()
        val isSuccess by viewModel.isSuccess.collectAsState()

        LaunchedEffect(isLoadingPhone) {
            if (isLoadingPhone) {
                delay(1500)
                viewModel.setLoadingPhone(false)
            }
            if (isSuccess) {
                navController.navigate(Routes.Navbar) {
                    popUpTo(Routes.Navbar) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }

        if (isLoadingPhone)
            RALoading()

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(12.dp)
                .fillMaxSize(),
        ) {
            Column {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(30.dp)
                        .offset(x = (-10).dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                RAText("Welcome! What's your mobile number?", variant = RATextVariant.BodySemiBold)
                Spacer(modifier = Modifier.height(16.dp))
                RAText(
                    "With a valid number, you can access reides, deliceries, and our other services.",
                    variant = RATextVariant.BodySmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Row(
                        modifier = Modifier
                            .border(1.dp, RAColor.Dark, shape = RoundedCornerShape(size = 5.dp))
                            .padding(horizontal = 14.dp)
                            .height(48.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(R.drawable.ic_id),
                            contentDescription = "Icon Id",
                            modifier = Modifier.size(24.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        RAText("+62", variant = RATextVariant.Body)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    PhoneTextField(
                        value = phoneValue,
                        onValueChange = { newValue ->
                            viewModel.setPhoneNumber(newValue)
                            if (newValue.length >= 10) {
                                viewModel.setSuccess(true)
                            }
                        }
                    )
                }

            }
            RAButton(
                "Next",
                modifier = Modifier.align(Alignment.BottomCenter),
                enabled = phoneValue.length >= 8,
                onClick = {
                    if (phoneValue.length >= 8) {
                        viewModel.setSuccess(true)
                        viewModel.setLoadingPhone(true)
                    }
                }
            )
        }
    }
}

@Composable
fun PhoneTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = RAFont.h3.copy(color = Color(0xFF000000)), // black text
        cursorBrush = SolidColor(Color.Black),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done,
            autoCorrect = false
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        modifier = modifier
            .focusRequester(focusRequester)
            .fillMaxWidth(),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .height(48.dp) // fixed height
                    .border(
                        1.dp,
                        RAColor.Dark,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .background(Color.White, RoundedCornerShape(5.dp))
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty()) {
                    RAText(
                        text = "Enter phone number",
                        variant = RATextVariant.Body,
                        color = RAColor.Grey
                    )
                }
                innerTextField()
            }
        })
}