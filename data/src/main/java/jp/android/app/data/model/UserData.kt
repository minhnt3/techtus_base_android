package jp.android.app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import jp.android.app.domain.entity.User

@JsonClass(generateAdapter = true)
data class UserData(

    @Json(name = "id")
    val id: Int,

    @Json(name = "username")
    val username: String,

    @Json(name = "email")
    val email: String,

    @Json(name = "first_name")
    val firstName: String?,

    @Json(name = "last_name")
    val lastName: String?,

    @Json(name = "avatar")
    val avatar: String?,
) {

    fun toEntity() = User(
        id = id,
        username = username,
        email = email,
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        avatar = avatar,
    )

    fun toDataLocal() = UserDataLocal(
        id = id,
        username = username,
        email = email,
        firstname = firstName ?: "",
        lastname = lastName ?: "",
        avatar = avatar,
    )
}
