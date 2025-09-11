package com.example.grabduplicates.ui.components.loading

import RAText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.grabduplicates.ui.theme.RAColor

@Preview
@Composable
fun RALoading(
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = RAColor.Primary,
                    strokeWidth = 4.dp
                )
                Spacer(modifier = Modifier.height(24.dp))
                RAText(
                    text = "Loading...",
                    color = Color.Black
                )
            }
        }
    }
}
