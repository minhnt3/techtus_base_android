package jp.android.app.common_ui.util

import android.os.Bundle
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable

typealias Function = () -> Unit
typealias ComposableContent = @Composable () -> Unit

fun Modifier.clickableNoRipple(
    enabled: Boolean = true,
    onClick: Function,
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled,
        onClick = onClick,
    )
}

fun Modifier.contentDescription(value: String) = semantics {
    contentDescription = value
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.composable(
    screen: Screen,
    content: @Composable Bundle.(AnimatedVisibilityScope) -> Unit
) = composable(screen.route) {
    (it.arguments ?: Bundle.EMPTY).content(this)
}

fun NavController.navigate(
    screen: Screen,
    vararg args: Pair<String, Any?>,
    builder: (NavOptionsBuilder.() -> Unit)? = null,
) {
    val request = NavDeepLinkRequest.Builder
        .fromUri(NavDestination.createRoute(screen.route).toUri())
        .build()
    val deepLinkMatch = graph.matchDeepLink(request)
    if (deepLinkMatch != null) {
        navigate(deepLinkMatch.destination.id, bundleOf(*args), builder?.let(::navOptions))
    }
}

val NavDestination.previousRoute
    get() = Screen.values().find { it.route == route }?.previousScreen?.invoke()?.route