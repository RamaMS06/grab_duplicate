package com.example.grabduplicates.ui.screen.activity

import RAText
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.grabduplicates.ui.theme.RAColor
import com.example.grabduplicates.util.dropShadow

@Composable
fun ActivityHeaderCard(
    modifier: Modifier = Modifier, item: HeaderActivityItem
) {
    Row(
        modifier = modifier
            .dropShadow(
                shape = RoundedCornerShape(8.dp),
                color = RAColor.Dark.copy(alpha = 0.025f)
            )
            .border(1.dp, RAColor.StrokeLight, shape = RoundedCornerShape(size = 12.dp))
            .background(
                color = RAColor.White
            )
            .clip(RoundedCornerShape(12.dp))
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            modifier = Modifier.size(38.dp),
            painter = painterResource(item.icon),
            contentDescription = ""
        )
        Spacer(Modifier.width(16.dp))
        Column(
            modifier = Modifier.padding(
                top = 4.dp
            )
        ) {
            RAText(
                item.title,
                variant = RATextVariant.BodySemiBold
            )
            if (item.desc != null)
                RAText(
                    item.desc,
                    modifier = Modifier.padding(top = 12.dp),
                    variant = RATextVariant.BodySmall
                )
            if (item.link != null)
                RAText(
                    item.link,
                    modifier = Modifier.padding(top = 24.dp),
                    styleOverride = RAFont.bodySmall.copy(fontWeight = FontWeight.W600),
                    color = RAColor.Secondary
                )
        }

    }
}