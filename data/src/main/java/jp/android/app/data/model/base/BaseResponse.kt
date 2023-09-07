package jp.android.app.data.model.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class BaseResponse<T>(

    @Json(name = "data")
    open val data: T? = null,

    @Json(name = "status")
    open val status: String? = null,

    @Json(name = "message")
    open val message: String? = null,
)
