package com.example.grabduplicates.ui.screen.activity

import RAText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.grabduplicates.R
import com.example.grabduplicates.ui.theme.RAColor

@Composable
fun ActivityHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RAText("Activity", variant = RATextVariant.H3)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        999f
                    )
                )
                .background(Color(0xffEDF8F8))
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_history),
                contentDescription = "Icon History"
            )
            Spacer(Modifier.width(4.dp))
            RAText("History", variant = RATextVariant.BodySemiBold)
        }
    }
}