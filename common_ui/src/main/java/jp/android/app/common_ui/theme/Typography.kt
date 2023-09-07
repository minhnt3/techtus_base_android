package jp.android.app.common_ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import jp.android.app.resources.R

val FontNormal = FontFamily(Font(R.font.montserrat_medium, FontWeight.Normal))
val FontBold = FontFamily(Font(R.font.montserrat_bold, FontWeight.Bold))

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontNormal,
        fontSize = 16.sp,
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val TextStyle10 = TextStyle(
    fontFamily = FontNormal,
    fontSize = 10.sp,
)

val TextStyle12 = TextStyle(
    fontFamily = FontNormal,
    fontSize = 12.sp,
)

val TextStyle14 = TextStyle(
    fontFamily = FontNormal,
    fontSize = 14.sp,
)

val TextStyle16 = TextStyle(
    fontFamily = FontNormal,
    fontSize = 16.sp,
)

val TextStyle14Bold = TextStyle(
    fontFamily = FontBold,
    fontSize = 14.sp,
)

val TextStyle16Bold = TextStyle(
    fontFamily = FontBold,
    fontSize = 16.sp,
)