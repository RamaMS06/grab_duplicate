// RAText.kt
@file:Suppress("unused")

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

/**
 * Variant list for easy, consistent usage across the app.
 * Extend as needed (e.g., Caption, Overline).
 */
enum class RATextVariant {
    H1, H2, H3, H4, H5,
    BodyBold, Body, BodySmall
}

/**
 * Reusable text component wired to RAFont + Sanomat Sans.
 * Provides consistent defaults but allows overrides when necessary.
 */
@Composable
fun RAText(
    text: String,
    variant: RATextVariant = RATextVariant.Body,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    // Optional style override merged on top of the selected variant:
    styleOverride: TextStyle? = null
) {
    val base = when (variant) {
        RATextVariant.H1 -> RAFont.h1
        RATextVariant.H2 -> RAFont.h2
        RATextVariant.H3 -> RAFont.h3
        RATextVariant.H4 -> RAFont.h4
        RATextVariant.H5 -> RAFont.h5
        RATextVariant.BodyBold -> RAFont.bodyBold
        RATextVariant.Body -> RAFont.body
        RATextVariant.BodySmall -> RAFont.bodySmall
    }

    val finalStyle = if (styleOverride != null) base.merge(styleOverride) else base

    Text(
        text = text,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        softWrap = softWrap,
        style = finalStyle
    )
}
