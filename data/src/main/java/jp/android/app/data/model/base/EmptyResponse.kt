package jp.android.app.data.model.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmptyResponse(

    @Json(name = "status")
    val status: String,

    @Json(name = "message")
    val message: String,
)
