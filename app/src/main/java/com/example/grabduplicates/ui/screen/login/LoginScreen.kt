import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.delay
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.grabduplicates.R
import com.example.grabduplicates.navigation.Routes
import com.example.grabduplicates.ui.theme.RAColor

@Composable
fun LoginScreen(navController: NavHostController) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(RAColor.Primary)
            .clipToBounds()
    ) {
        val h = maxHeight
        val w = maxWidth
        val density = LocalDensity.current

        val topPaddingDp = h * 0.09f
        val textTopDp = h * 0.02f
        val footerLiftDp = h * 0.18f

        val footerLiftPx = with(density) { footerLiftDp.toPx() }

        var play by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            delay(16)
            play = true
        }

        /// Adding all animation here
        val headerAnim by animateFloatAsState(
            targetValue = if (play) 0f else -0.4f * with(density) { h.toPx() },
            animationSpec = tween(700, delayMillis = 50, easing = FastOutSlowInEasing),
            label = "headerAnim"
        )
        val waveAnim by animateFloatAsState(
            targetValue = if (play) 0f else 0.30f * with(density) { h.toPx() },
            animationSpec = tween(650, delayMillis = 460, easing = FastOutSlowInEasing),
            label = "waveAnim"
        )
        val illuAnim by animateFloatAsState(
            targetValue = if (play) 0f else 0.33f * with(density) { h.toPx() },
            animationSpec = tween(700, delayMillis = 260, easing = FastOutSlowInEasing),
            label = "illuAnim"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = topPaddingDp)
                .graphicsLayer {
                    translationY = headerAnim // pixel-based, smooth
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.grab_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(w * 0.28f, w * 0.28f * 0.48f)
            )
            RAText(
                text = "Your everyday everything app",
                color = RAColor.White,
                modifier = Modifier.padding(top = textTopDp)
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
        ) {
            Image(
                painter = painterResource(R.drawable.footer_login),
                contentDescription = "Footer Illustration Login",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .graphicsLayer {
                        translationY = illuAnim - footerLiftPx
                    },
                contentScale = ContentScale.FillWidth
            )

            Image(
                painter = painterResource(R.drawable.footer_wave),
                contentDescription = "Footer Login Wave",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .graphicsLayer {
                        translationY = waveAnim
                    },
                contentScale = ContentScale.FillWidth
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.9f)
                    .padding(bottom = h * 0.03f)
                    .navigationBarsPadding()
                    .graphicsLayer {
                        translationY = waveAnim
                    }
                    .zIndex(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                RAButton(text = "Log In", variant = RAButtonVariant.Filled, onClick = {
                    navController.navigate(Routes.Otp){
                        popUpTo(Routes.Otp) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                })
                RAButton(text = "New to Grab? Sign up!", modifier = Modifier.padding(top = 16.dp), variant = RAButtonVariant.Outline, onClick = {} )
            }

        }
    }
}
