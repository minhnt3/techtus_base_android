package jp.android.app.features.main

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import jp.android.app.common_ui.util.Screen
import jp.android.app.common_ui.util.WindowWidth
import jp.android.app.common_ui.util.composable
import jp.android.app.common_ui.util.navigate
import jp.android.app.features.home.HomeScreen
import jp.android.app.features.login.LoginScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(
    navController: NavHostController = rememberAnimatedNavController(),
    isLoggedIn: Boolean,
    windowWidth: WindowWidth,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Screen.Home.route else Screen.Login.route,
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { -it / 4 } + fadeOut(targetAlpha = 0.3f) },
        popEnterTransition = { slideInHorizontally { -it / 4 } + fadeIn(initialAlpha = 0.3f) },
        popExitTransition = { slideOutHorizontally { it } },
    ) {

        composable(Screen.Login) {
            LoginScreen(
                windowWidth = windowWidth,
            ) {
                navController.navigate(Screen.Home) { popUpTo(0) }
            }
        }

        composable(Screen.Home) {
            HomeScreen(
                windowWidth = windowWidth,
            ) {
                navController.navigate(Screen.Login) { popUpTo(0) }
            }
        }
    }
}
