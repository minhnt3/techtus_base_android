package jp.android.app.domain.repository

import jp.android.app.domain.data.AppResult
import jp.android.app.domain.entity.Movie
import jp.android.app.domain.entity.Paging
import jp.android.app.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun login(email: String, password: String, isRemember: Boolean): AppResult<User>

    suspend fun logout(): AppResult<Unit>

    suspend fun getMovies(page: Int, perPage: Int): AppResult<Paging<Movie>>

    suspend fun insertUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun removeUser(user: User)

    fun isLoggedIn(): Boolean

    fun getAppTheme(): Int

    fun changeAppTheme(mode: Int)

    val forceLogoutEvent: Flow<String?>

    fun getAppLocalize(): String

    fun changeAppLocalize(mode: String)
}
