package jp.android.app.features.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun AboutScreen(
    movieId: Int,
    movieTitle: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            fontSize = 32.sp,
            text = "About movie $movieId: $movieTitle",
        )
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    AboutScreen(
        movieId = 1,
        movieTitle = "Kotlin",
    )
}