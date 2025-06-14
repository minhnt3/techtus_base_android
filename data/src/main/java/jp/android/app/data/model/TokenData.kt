package jp.android.app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenData(

    @Json(name = "access_token")
    val accessToken: String,

    @Json(name = "refresh_token")
    val refreshToken: String,
) {
}