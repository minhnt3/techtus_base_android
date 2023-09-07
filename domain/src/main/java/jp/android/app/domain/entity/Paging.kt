package jp.android.app.domain.entity

data class Paging<T>(
    val list: List<T>,
    val page: Int,
    val perPage: Int,
    val totalItems: Long,
    val hasPrev: Boolean,
    val hasNext: Boolean,
)