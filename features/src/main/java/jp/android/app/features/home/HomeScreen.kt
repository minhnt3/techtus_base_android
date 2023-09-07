package jp.android.app.features.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import jp.android.app.common_ui.theme.Dimens
import jp.android.app.common_ui.theme.colorBackground
import jp.android.app.common_ui.util.Function
import jp.android.app.common_ui.util.Screen
import jp.android.app.common_ui.util.WindowWidth
import jp.android.app.common_ui.util.navigate
import jp.android.app.resources.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    windowWidth: WindowWidth,
    onLogout: Function,
) {
    val navController = rememberAnimatedNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorBackground)
            .navigationBarsPadding(),
    ) {
        Box(
            modifier = Modifier
                .weight(weight = 1f)
        ) {
            HomeNavHost(
                navController = navController,
                windowWidth = windowWidth,
                onLogout = onLogout,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.1f),
                            )
                        )
                    ),
            )
        }
        HomeBottomNavigation(navController = navController)
    }
}

@Composable
fun HomeBottomNavigation(navController: NavController) {
    var currentRoute by rememberSaveable { mutableStateOf(Screen.MyPage) }
    val onBottomItemClick: (Screen) -> Unit = { screen ->
        currentRoute = screen
        navController.navigate(screen) {
            popUpTo(0) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Surface {
        Row(
            Modifier
                .fillMaxWidth()
                .height(Dimens.homeBottomBarHeight)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HomeBottomNavigationItem(
                icon = R.drawable.ic_home,
                title = R.string.title_my_page,
                isSelected = currentRoute == Screen.MyPage,
            ) {
                onBottomItemClick(Screen.MyPage)
            }
            HomeBottomNavigationItem(
                icon = R.drawable.ic_users,
                title = R.string.title_user,
                isSelected = currentRoute == Screen.User,
            ) {
                onBottomItemClick(Screen.User)
            }
            HomeBottomNavigationItem(
                icon = R.drawable.ic_setting,
                title = R.string.title_setting,
                isSelected = currentRoute == Screen.Setting,
            ) {
                onBottomItemClick(Screen.Setting)
            }
        }
    }
}

@Composable
fun RowScope.HomeBottomNavigationItem(
    icon: Int,
    title: Int,
    isSelected: Boolean,
    onClick: Function,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val ripple = rememberRipple(bounded = false)
    Column(
        modifier = Modifier
            .clickable(
                enabled = !isSelected,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = ripple,
            )
            .weight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .size(Dimens.homeBottomBarIconSize),
            painter = painterResource(icon),
            contentDescription = null,
            tint = if (isSelected) Color.Black else Color.Gray,
        )
        Text(
            text = stringResource(id = title),
            fontSize = Dimens.homeBottomBarTextSize,
            color = if (isSelected) Color.Black else Color.Gray,
        )
    }
}