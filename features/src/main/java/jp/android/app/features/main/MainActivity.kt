package jp.android.app.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import jp.android.app.common_ui.theme.AppTheme
import jp.android.app.common_ui.theme.ProvideDimens
import jp.android.app.common_ui.theme.toDimensions
import jp.android.app.common_ui.util.rememberWindowWidth

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val windowWidth = rememberWindowWidth()
            ProvideDimens(windowWidth.toDimensions()) {
                AppTheme(
                    darkTheme = viewModel.state.isDarkMode,
                ) {
                    MainNavHost(
                        windowWidth = windowWidth,
                        isLoggedIn = viewModel.isLoggedIn(),
                    )
                }
            }
        }
    }
}
