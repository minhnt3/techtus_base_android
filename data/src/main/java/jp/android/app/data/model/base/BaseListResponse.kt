package jp.android.app.data.model.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import jp.android.app.domain.entity.Paging

@JsonClass(generateAdapter = true)
class BaseListResponse<T>(
    @Json(name = "data")
    override val data: PagingData<T>?,

    @Json(name = "status")
    override val status: String?,

    @Json(name = "message")
    override val message: String?,
) : BaseResponse<BaseListResponse.PagingData<T>>() {

    @JsonClass(generateAdapter = true)
    data class PagingData<T>(
        @Json(name = "items")
        val items: List<T>,
        @Json(name = "page")
        val page: Int,
        @Json(name = "per_page")
        val perPage: Int,
        @Json(name = "total_items")
        val totalItems: Long,
        @Json(name = "has_prev")
        val hasPrev: Boolean,
        @Json(name = "has_next")
        val hasNext: Boolean,
    ) {

        fun <E> mapToEntity(transform: (T) -> E) = Paging(
            list = items.map(transform),
            page = page,
            perPage = perPage,
            totalItems = totalItems,
            hasPrev = hasPrev,
            hasNext = hasNext,
        )
    }
}