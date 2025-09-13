// RAButton.kt
@file:Suppress("unused")

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.grabduplicates.ui.theme.RAColor

enum class RAButtonVariant { Filled, Outline }

object RAButtonDefaults {
    val Height = 48.dp
    val CornerRadius = 8.dp
    val ContentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
    val FilledContainer = RAColor.Primary
    val FilledContent = RAColor.White
    val FilledDisabledContainer = RAColor.Primary.copy(alpha = 0.5f)
    val FilledDisabledContent = RAColor.White.copy(alpha = 0.7f)

    val OutlineContainer = RAColor.White
    val OutlineText = Color(0xFF676767)
    val OutlineBorder = Color(0xFF676767)
    val OutlineDisabledText = Color(0xFF9E9E9E)
    val OutlineDisabledBorder = Color(0xFFCECECE)

    val InnerShadowColor: Color = Color.Black
    val InnerShadowY: Dp = 4.dp
    val InnerShadowBlur: Dp = 4.dp
    val InnerShadowSpread: Dp = 1.dp
}

private fun Modifier.clickable(
    onClick: () -> Unit,
    enabled: Boolean,
    interactionSource: MutableInteractionSource
): Modifier = composed {
    clickable(
        enabled = enabled,
        role = Role.Button,
        indication = null,
        interactionSource = interactionSource
    ) { onClick() }
}

/**
 * Refactored RAButton: custom container (Box) + RAText.
 * - Single shared interaction source (so press state works)
 * - Press "depress" via translationY
 * - Internal vertical inner shadow (y-only), drawn last so it’s visible
 */
@Composable
fun RAButton(
    text: String,
    variant: RAButtonVariant = RAButtonVariant.Filled,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    leading: (@Composable (() -> Unit))? = null,
    trailing: (@Composable (() -> Unit))? = null,
) {
    val shape = RoundedCornerShape(RAButtonDefaults.CornerRadius)

    val interaction = remember { MutableInteractionSource() }
    val isPressed by interaction.collectIsPressedAsState()

    val density = LocalDensity.current
    val pressDyPx = with(density) { 1.dp.toPx() }

    val translateY by animateFloatAsState(
        targetValue = if (isPressed && enabled && !loading) pressDyPx else 0f,
        animationSpec = tween(durationMillis = 120, easing = LinearOutSlowInEasing),
        label = "pressTranslateY"
    )
    val pressProgress by animateFloatAsState(
        targetValue = if (isPressed && enabled && !loading) 1f else 0f,
        animationSpec = tween(durationMillis = 120),
        label = "innerShadowProgress"
    )

    val containerColor =
        when (variant) {
            RAButtonVariant.Filled ->
                if (!enabled) RAColor.Grey
                else if (isPressed) RAColor.PrimaryDarker
                else RAButtonDefaults.FilledContainer

            RAButtonVariant.Outline ->
                RAButtonDefaults.OutlineContainer
        }

    val contentColor =
        when (variant) {
            RAButtonVariant.Filled ->
                if (!enabled) RAButtonDefaults.FilledDisabledContent else RAButtonDefaults.FilledContent

            RAButtonVariant.Outline ->
                if (!enabled) RAButtonDefaults.OutlineDisabledText else RAButtonDefaults.OutlineText
        }

    val borderStroke: BorderStroke? =
        when (variant) {
            RAButtonVariant.Filled -> null
            RAButtonVariant.Outline ->
                BorderStroke(
                    width = 1.dp,
                    color = if (!enabled) RAButtonDefaults.OutlineDisabledBorder else RAButtonDefaults.OutlineBorder
                )
        }

    Box(
        modifier = modifier
            .height(RAButtonDefaults.Height)
            .fillMaxWidth()
            .graphicsLayer { translationY = translateY }
            .clip(shape)
            .background(containerColor, shape)
            .then(if (borderStroke != null) Modifier.border(borderStroke, shape) else Modifier)
            .clickable(onClick = onClick, enabled = enabled && !loading, interactionSource = interaction)
            .innerShadowYOnPress(
                cornerRadius = RAButtonDefaults.CornerRadius,
                progress = pressProgress,
                y = RAButtonDefaults.InnerShadowY,
                blur = RAButtonDefaults.InnerShadowBlur,
                spread = RAButtonDefaults.InnerShadowSpread,
                color = RAButtonDefaults.InnerShadowColor
            ),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = contentColor
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(RAButtonDefaults.ContentPadding)
            ) {
                if (leading != null) {
                    leading()
                    Spacer(Modifier.width(12.dp))
                }
                RAText(
                    text = text,
                    color = contentColor,
                    variant = RATextVariant.BodyBold
                )
                if (trailing != null) {
                    Spacer(Modifier.width(12.dp))
                    trailing()
                }
            }
        }
    }
}

/**
 * Modifier for inner shadow when
 * user pressing the button
 */
private fun Modifier.innerShadowYOnPress(
    cornerRadius: Dp,
    progress: Float, // 0f → none, 1f → full thickness
    y: Dp,
    blur: Dp,
    spread: Dp,
    color: Color
): Modifier = drawWithContent {
    drawContent()
    if (progress <= 0f) return@drawWithContent

    val r = cornerRadius.toPx()
    val oy = y.toPx()
    val blurPx = blur.toPx().coerceAtLeast(0f)
    val spreadPx = spread.toPx().coerceAtLeast(0f)

    // Thickness grows with progress; color stays intact
    val thickness = ((blurPx + spreadPx) * progress.coerceIn(0f, 1f)).coerceAtLeast(0.1f)

    val clip = Path().apply {
        addRoundRect(RoundRect(0f, 0f, size.width, size.height, CornerRadius(r, r)))
    }

    clipPath(clip) {
        if (oy > 0f) {
            // Bottom inner shadow
            val h = thickness + spreadPx
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(color, Color.Transparent)
                ),
                topLeft = Offset(0f, size.height - h),
                size = Size(size.width, h)
            )
        } else if (oy < 0f) {
            // Top inner shadow
            val h = thickness + spreadPx
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, color)
                ),
                topLeft = Offset(0f, 0f),
                size = Size(size.width, h)
            )
        }
        // y == 0.dp → no vertical inner shadow
    }
}
