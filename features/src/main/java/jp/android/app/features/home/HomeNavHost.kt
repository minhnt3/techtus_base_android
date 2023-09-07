package jp.android.app.features.home

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import jp.android.app.common_ui.util.*
import jp.android.app.common_ui.util.Function
import jp.android.app.features.about.AboutScreen
import jp.android.app.features.my_page.MyPageScreen
import jp.android.app.features.setting.SettingScreen
import jp.android.app.features.user.UserScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeNavHost(
    navController: NavHostController,
    windowWidth: WindowWidth,
    onLogout: Function,
) {
    AnimatedNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Screen.MyPage.route,
        enterTransition = {
            if (targetState.destination.previousRoute == initialState.destination.route) {
                slideInHorizontally { it }
            } else {
                EnterTransition.None
            }
        },
        exitTransition = {
            if (targetState.destination.previousRoute == initialState.destination.route) {
                slideOutHorizontally { -it / 4 } + fadeOut(targetAlpha = 0.3f)
            } else {
                ExitTransition.None
            }
        },
        popEnterTransition = {
            if (initialState.destination.previousRoute == targetState.destination.route) {
                slideInHorizontally { -it / 4 } + fadeIn(initialAlpha = 0.3f)
            } else {
                EnterTransition.None
            }
        },
        popExitTransition = {
            if (initialState.destination.previousRoute == targetState.destination.route) {
                slideOutHorizontally { it }
            } else {
                ExitTransition.None
            }
        },
    ) {
        composable(Screen.MyPage) {
            MyPageScreen {
                navController.navigate(
                    Screen.About,
                    Screen.Args.KEY_MOVIE_ID to it.id,
                    Screen.Args.KEY_MOVIE_TITLE to it.title,
                )
            }
        }
        composable(Screen.User) {
            UserScreen()
        }
        composable(Screen.Setting) {
            SettingScreen(
                onLogout = onLogout,
            )
        }
        composable(Screen.About) {
            AboutScreen(
                movieId = getInt(Screen.Args.KEY_MOVIE_ID),
                movieTitle = getString(Screen.Args.KEY_MOVIE_TITLE) ?: "",
            )
        }
    }
}