package jp.android.app.domain.entity

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val backDropPath: String?,
)
