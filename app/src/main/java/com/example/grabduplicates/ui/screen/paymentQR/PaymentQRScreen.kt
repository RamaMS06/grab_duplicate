package com.example.grabduplicates.ui.screen.paymentQR

import RAText
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.grabduplicates.R
import com.example.grabduplicates.ui.theme.RAColor

data class QRllustrationItem(
    val img: Int,
    val label: String
)

data class PayMerchantItem(
    val img: Int? = null,
    val label: String,
    val desc: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentQRScreen(navController: NavController) {
    val verticalState = rememberScrollState()
    val listDataIllustration = listOf<QRllustrationItem>(
        QRllustrationItem(
            R.drawable.img_cashless,
            "Go cashless effortlessly - just scan QR code to pay."
        ),
        QRllustrationItem(
            R.drawable.img_leavecash,
            "Leave cash behind - all you need is your Grab app!"
        ),
        QRllustrationItem(
            R.drawable.img_perk,
            "Enjoy perks from GrabRewards whenever you pay."
        ),
    )

    val listDataPayMerchant = listOf<PayMerchantItem>(
        PayMerchantItem(
            R.drawable.img_illu_pay_qr,
            "Find the Grab QR Code in Stores",
            "Merchants accepting GrabPay will displays this QR code.",
        ),
        PayMerchantItem(
            label = "Scan QR Code to Pay",
            desc = "Tap Pay in the Grab app to start scanning.",
        ),
        PayMerchantItem(
            label = "Enter Amount",
            desc = "Ensure the correct amount is entered to continue"
        )

    )

    Scaffold(
        modifier = Modifier
            .background(color = RAColor.White)
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = RAColor.Dark,
                            contentDescription = "Localized description",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = RAColor.White,
                    titleContentColor = RAColor.White,
                    actionIconContentColor = RAColor.White
                ),
                title =
                    {
                        RAText(
                            "How to pay", variant = RATextVariant.H5,
                            color = RAColor.Dark
                        )
                    })
        }) { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = RAColor.White)
                .padding(horizontal = 16.dp, vertical = 28.dp)
                .verticalScroll(verticalState)
        ) {

            RAText(
                "Pay in Stores Using Grabpay",
                color = RAColor.Primary,
                variant = RATextVariant.H4
            )
            Spacer(Modifier.height(10.dp))
            RAText("You can pay for food and goods with GrabPay!")
            Spacer(Modifier.height(45.dp))
            listDataIllustration.forEach { item ->
                CardPayment(img = item.img, label = item.label)
                Spacer(Modifier.height(16.dp))
            }
            Spacer(Modifier.height(28.dp))
            RAText(
                "Where can I use GrabPay?",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color(0xFF3478F6),
                variant = RATextVariant.BodySemiBold
            )
            Spacer(Modifier.height(46.dp))
            RAText(
                "How to Pay Merchants",
                variant = RATextVariant.H4,
                color = RAColor.Primary,
                styleOverride = RAFont.h4.copy(
                    fontWeight = FontWeight.W400
                )
            )
            Spacer(Modifier.height(38.dp))
            listDataPayMerchant.forEachIndexed { index, data ->
                CardStepPayment(
                    index + 1,
                    data.label,
                    data.desc,
                    data.img,
                    index == listDataPayMerchant.lastIndex
                )
            }
        }
    }
}

@Composable
fun CardPayment(img: Int, label: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(img),
            contentDescription = "Image $img",
            modifier = Modifier
                .size(75.dp),
            contentScale = ContentScale.Fit,
        )
        Spacer(Modifier.width(24.dp))
        RAText(label, color = Color(0xFF676767))
    }
}

@Composable
fun CardStepPayment(
    step: Int,
    title: String,
    desc: String,
    img: Int? = null,
    isLast: Boolean = false
) {
    Row(modifier = Modifier.height(IntrinsicSize.Max)) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Step circle
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFDBF2E2)),
                contentAlignment = Alignment.Center
            ) {
                RAText(
                    text = step.toString(),
                    color = Color(0xFF00A86B), // Grab green
                    styleOverride = TextStyle(fontWeight = FontWeight.Bold)
                )
            }

            // Draw dashed line only if NOT last step
            if (!isLast) {
                VerticalDashedDivider(
                    color = Color(0xFF00A86B),
                    thickness = 3.dp,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 8.dp)
                )
            }
        }

        Spacer(Modifier.width(16.dp))
        Column {
            RAText(
                title,
                variant = RATextVariant.H4,
                styleOverride = RAFont.h4.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(Modifier.height(6.dp))
            RAText(
                desc,
                color = RAColor.Grey
            )
            Spacer(Modifier.height(18.dp))
            if (img != null)
                Image(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(R.drawable.img_illu_pay_qr),
                    contentDescription = "Pay Merchants"
                )
            else
                Spacer(
                    Modifier.height(40.dp)
                )
        }
    }
}

@Composable
fun VerticalDashedDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    thickness: Dp = 1.dp,
    dashLength: Dp = 1.dp,
    gapLength: Dp = 9.dp,
) {
    val density = LocalDensity.current
    val strokePx = with(density) { thickness.toPx() }
    val dashPx = with(density) { dashLength.toPx() }
    val gapPx = with(density) { gapLength.toPx() }

    val pathEffect = remember(dashPx, gapPx) {
        PathEffect.dashPathEffect(floatArrayOf(dashPx, gapPx), 0f)
    }

    Box(
        modifier = modifier
            .width(thickness)
            .drawBehind {
                val x = size.width / 2f
                drawLine(
                    color = color,
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    strokeWidth = strokePx,
                    cap = StrokeCap.Round, // ✅ Rounded ends
                    pathEffect = pathEffect
                )
            }
    )
}
