package jp.android.app.common_ui.util

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VMargin(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun VMargin2() {
    Spacer(modifier = Modifier.height(2.dp))
}

@Composable
fun VMargin4() {
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun VMargin8() {
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun VMargin16() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun VMargin40() {
    Spacer(modifier = Modifier.height(40.dp))
}

@Composable
fun HMargin(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun HMargin4() {
    Spacer(modifier = Modifier.width(4.dp))
}

@Composable
fun HMargin8() {
    Spacer(modifier = Modifier.width(8.dp))
}

@Composable
fun HMargin16() {
    Spacer(modifier = Modifier.width(16.dp))
}