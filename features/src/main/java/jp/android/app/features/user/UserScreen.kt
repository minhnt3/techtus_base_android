package jp.android.app.features.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.android.app.resources.R

@Composable
fun UserScreen(
    viewModel: UserViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            fontSize = 32.sp,
            text = stringResource(id = R.string.title_user),
        )
    }
}