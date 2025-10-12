package com.example.grabduplicates.ui.screen.activity

import RAText
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.grabduplicates.R
import com.example.grabduplicates.ui.theme.RAColor

@Composable
fun ActivityRecentCard(recentItem: HeaderRecentItem) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Image(
                modifier = Modifier.size(34.dp),
                painter = painterResource(recentItem.icon ?: R.drawable.img_car),
                contentDescription = ""
            )
            Spacer(Modifier.width(14.dp))

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                if (recentItem.isCancel)
                    RAText(
                        "Cancelled", color = RAColor.Danger, variant = RATextVariant.BodySmall,
                        styleOverride = TextStyle(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                RAText(recentItem.title, variant = RATextVariant.H4)
                Spacer(Modifier.height(8.dp))
                RAText(recentItem.date)

                if (recentItem.rebook ?: false)
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        RAText("Rate & tip", color = RAColor.Secondary,
                            variant = RATextVariant.BodySemiBold)
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Back Button",
                            modifier = Modifier.size(18.dp),
                            tint = RAColor.Secondary
                        )
                    }
            }
        }

        Column(
            horizontalAlignment = Alignment.End,
        ) {
            if (recentItem.total != null)
                RAText(recentItem.total)
            Spacer(Modifier.height(4.dp))
            if (recentItem.points != null)
                RAText(recentItem.points, variant = RATextVariant.BodySmall)
        }

    }
}