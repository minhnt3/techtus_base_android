package jp.android.app.features.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.android.app.common_ui.util.Function
import jp.android.app.common_ui.util.collectEvent
import jp.android.app.resources.R

@Composable
fun SettingScreen(
    onLogout: Function,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    viewModel.event.collectEvent {
        when (it) {
            is SettingEvent.LogoutSuccess -> {
                onLogout()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            fontSize = 32.sp,
            text = stringResource(id = R.string.title_setting)
        )
        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onClick = viewModel::logout,
        ) {
            Text(text = "Logout")
        }
    }
}