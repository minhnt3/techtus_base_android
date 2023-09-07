package jp.android.app.common_ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.android.app.common_ui.util.WindowWidth

class Dimensions private constructor(
    val padding16: Dp = 16.dp,
    val pullRefreshIndicatorSize: Dp = 24.dp,
    val loginLogoSize: Dp = 32.dp,
    val homeBottomBarHeight: Dp = 56.dp,
    val homeBottomBarIconSize: Dp = 24.dp,
    val homeBottomBarTextSize: TextUnit = 14.sp,
    val myPagePaddingTop: Dp = 40.dp,
    val movieItemImageSize: Dp = 64.dp,
) {
    companion object {
        val Small = Dimensions(
            loginLogoSize = 28.dp,
            movieItemImageSize = 40.dp,
        )
        val Large = Dimensions(
            loginLogoSize = 48.dp,
            homeBottomBarHeight = 64.dp,
            homeBottomBarIconSize = 32.dp,
            homeBottomBarTextSize = 15.sp,
            movieItemImageSize = 80.dp,
        )
        val Default = Dimensions()
    }
}

fun WindowWidth.toDimensions() = when (this) {
    WindowWidth.Small -> Dimensions.Small
    WindowWidth.Large -> Dimensions.Large
    WindowWidth.Normal -> Dimensions.Default
}

@Composable
fun ProvideDimens(
    dimensions: Dimensions,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalAppDimens provides dimensions, content = content)
}

private val LocalAppDimens = compositionLocalOf { Dimensions.Default }

val Dimens: Dimensions @Composable get() = LocalAppDimens.current