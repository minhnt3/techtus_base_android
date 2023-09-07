package jp.android.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.android.app.domain.entity.User

@Entity(tableName = "user")
data class UserDataLocal(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "firstname")
    var firstname: String,

    @ColumnInfo(name = "lastname")
    var lastname: String,

    @ColumnInfo(name = "avatar")
    var avatar: String?,
) {

    fun toEntity() = User(
        id = id ?: -1,
        username = username,
        email = email,
        firstName = firstname,
        lastName = lastname,
        avatar = avatar,
    )

    companion object {
        fun fromEntity(user: User) = UserDataLocal(
            id = user.id,
            username = user.username,
            email = user.email,
            firstname = user.firstName,
            lastname = user.lastName,
            avatar = user.avatar,
        )
    }
}
