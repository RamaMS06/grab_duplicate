// RATypography.kt
@file:Suppress("unused")

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.grabduplicates.R

/**
 * Sanomat Sans family.
 * Make sure these files exist in res/font:
 *  - sanomatsans_regular.ttf
 *  - sanomatsans_medium.ttf
 *  - sanomatsans_semibold.ttf
 *  - sanomatsans_bold.ttf
 */
val SanomatSans = FontFamily(
    Font(R.font.sanomatsans_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(R.font.sanomatsans_medium,  weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(R.font.sanomatsans_medium, weight = FontWeight.SemiBold, style = FontStyle.Normal),
    Font(R.font.sanomatsans_bold,    weight = FontWeight.Bold, style = FontStyle.Normal),
)

/**
 * Central place for all text styles (sizes/weights/line-heights/letter-spacing).
 * Tweak numbers to your visual baseline as needed.
 */
object RAFont {
    val h1 = TextStyle(
        fontFamily = SanomatSans,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.2).sp
    )

    val h2 = TextStyle(
        fontFamily = SanomatSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.1).sp
    )

    val h3 = TextStyle(
        fontFamily = SanomatSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    )

    val h4 = TextStyle(
        fontFamily = SanomatSans,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )

    val h5 = TextStyle(
        fontFamily = SanomatSans,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )

    val bodyBold = TextStyle(
        fontFamily = SanomatSans,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )

    val bodySemiBold = TextStyle(
        fontFamily = SanomatSans,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )

    val body = TextStyle(
        fontFamily = SanomatSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )

    val bodySmall = TextStyle(
        fontFamily = SanomatSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    )

    val bodyXSmall = TextStyle(
        fontFamily = SanomatSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    )
}
