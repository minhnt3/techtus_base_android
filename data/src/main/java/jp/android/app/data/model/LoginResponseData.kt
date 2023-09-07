package jp.android.app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponseData(

    @Json(name = "token_info")
    val tokenInfo: TokenData,

    @Json(name = "user")
    val user: UserData,
)
