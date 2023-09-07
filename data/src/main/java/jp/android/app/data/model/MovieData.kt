package jp.android.app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import jp.android.app.domain.entity.Movie

@JsonClass(generateAdapter = true)
data class MovieData(

    @Json(name = "id")
    val id: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "overview")
    val overview: String,

    @Json(name = "backdrop_path")
    val backdropPath: String?,

    ) {

    fun toEntity() = Movie(
        id = id,
        title = title,
        overview = overview,
        backDropPath = backdropPath
    )
}
