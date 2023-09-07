package jp.android.app.data.repository

import jp.android.app.data.model.LoginResponseData
import jp.android.app.data.model.TokenData
import jp.android.app.data.model.UserData
import jp.android.app.domain.data.AppException
import jp.android.app.domain.data.AppResult

const val USERNAME = "thinhtrai1"
const val PASSWORD = "2"

class FakeRepository {

    fun login(username: String, password: String): AppResult<LoginResponseData> {
        return if (username == USERNAME && password == PASSWORD) {
            AppResult.Success(
                LoginResponseData(
                    tokenInfo = TokenData(
                        accessToken = "",
                        refreshToken = ""
                    ),
                    user = UserData(
                        id = 1,
                        username = USERNAME,
                        email = "thinhnd@nal.vn",
                        firstName = "Thinh",
                        lastName = "Nguyen Duc",
                        avatar = null
                    ),
                )
            )
        } else {
            AppResult.Failure(
                AppException.Remote.UnauthorizedException
            )
        }
    }
}