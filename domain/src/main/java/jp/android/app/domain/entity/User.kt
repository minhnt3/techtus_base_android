package jp.android.app.domain.entity

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String?,
) {
    val fullName get() = "$firstName $lastName"
}