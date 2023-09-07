package jp.android.app.common_ui.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import jp.android.app.common_ui.theme.TextStyle14
import jp.android.app.common_ui.theme.colorText
import jp.android.app.common_ui.util.ExceptionHandler
import jp.android.app.domain.data.AppException

@Composable
fun LoadingDialog(vararg isShow: Boolean) {
    if (isShow.any { it }) {
        Dialog({}) {
            (LocalView.current.parent as? DialogWindowProvider)?.window?.setDimAmount(0.5f)
            SpinningCircleProgressIndicator()
        }
    }
}

@Composable
fun ErrorDialog(
    appException: AppException?,
    onDismissRequest: () -> Unit,
) {
    appException?.let {
        ErrorDialog(
            message = ExceptionHandler.getExceptionMessage(it),
            onDismissRequest = onDismissRequest,
        )
    }
}

@Composable
fun ErrorDialog(
    message: String,
    onDismissRequest: () -> Unit,
) {
    Dialog({}) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = 32.dp,
                        horizontal = 16.dp
                    ),
                textAlign = TextAlign.Center,
                text = message,
                style = TextStyle14,
                color = colorText,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color(0xFFD5D5D5))
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onDismissRequest)
                    .padding(vertical = 10.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = android.R.string.ok),
                style = TextStyle14,
                color = colorText,
            )
        }
    }
}

/**
 * @see <a href="https://github.com/SmartToolFactory/Compose-ProgressIndicator">Refer source</a>
 */
@Composable
fun SpinningCircleProgressIndicator(
    modifier: Modifier = Modifier,
    indicatorSize: Dp = 32.dp,
    color: Color = Color.White,
    durationMillis: Int = 1000,
) {
    val count = 10

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = count.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(
        modifier
            .progressSemantics()
            .size(indicatorSize)
    ) {

        var canvasWidth = size.width
        var canvasHeight = size.height

        if (canvasHeight < canvasWidth) {
            canvasWidth = canvasHeight
        } else {
            canvasHeight = canvasWidth
        }

        val radius = canvasWidth * .08f

        val horizontalOffset = (size.width - size.height).coerceAtLeast(0f) / 2
        val verticalOffset = (size.height - size.width).coerceAtLeast(0f) / 2
        val center = Offset(
            x = horizontalOffset + canvasWidth - radius,
            y = verticalOffset + canvasHeight / 2
        )

        val coefficient = 360f / count
        for (i in 0..8) {
            rotate((angle.toInt() + i) * coefficient) {
                drawCircle(
                    color = color.copy(
                        alpha = (i.toFloat() / 9).coerceIn(
                            0f, 1f
                        )
                    ),
                    radius = radius,
                    center = center,
                )
            }
        }
    }
}

@Composable
fun PlaceholderBox(
    modifier: Modifier,
) {
    Box(modifier = modifier.then(placeholderModifier))
}

private val colorPlaceholder = Color(0xFFD5D5D5)
private val colorPlaceholderHighlight = Color(0xFFE5E5E5)
private val placeholderModifier = Modifier.placeholder(
    visible = true,
    color = colorPlaceholder,
    highlight = PlaceholderHighlight.fade(
        highlightColor = colorPlaceholderHighlight,
    ),
)