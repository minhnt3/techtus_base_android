package jp.android.app.data.repository

import jp.android.app.data.model.MovieData
import jp.android.app.data.model.UserDataLocal
import jp.android.app.data.model.request.LoginRequest
import jp.android.app.data.source.local.database.DatabaseApi
import jp.android.app.data.source.local.preference.AppPreferences
import jp.android.app.data.source.remote.api_service.AppApiService
import jp.android.app.data.source.remote.api_service.AuthApiService
import jp.android.app.domain.data.AppResult
import jp.android.app.domain.entity.Movie
import jp.android.app.domain.entity.Paging
import jp.android.app.domain.entity.User
import jp.android.app.domain.repository.Repository
import jp.android.app.shared.AppBuildConfig
import jp.android.app.shared.AppCoroutineDispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val appApiService: AppApiService,
    private val authApiService: AuthApiService,
    private val databaseApi: DatabaseApi,
    private val appPreferences: AppPreferences,
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val appBuildConfig: AppBuildConfig,
) : Repository, ResultHandler() {

    override suspend fun login(email: String, password: String, isRemember: Boolean): AppResult<User> {
        return getResult {
            authApiService.login(LoginRequest(email, password))
        }.map {
            appPreferences.accessToken = it.tokenInfo.accessToken
            appPreferences.refreshToken = it.tokenInfo.refreshToken
            appPreferences.isRemember = isRemember
            it.user.toEntity()
        }
    }

    override suspend fun logout(): AppResult<Unit> {
        return AppResult.Success(appPreferences.clearData())
    }

    override suspend fun getMovies(page: Int, perPage: Int): AppResult<Paging<Movie>> {
        delay(200)
        return getResult {
            appApiService.getMovies()
        }.map {
            it.mapToEntity(MovieData::toEntity)
        }
    }

    override suspend fun insertUser(user: User) {
        databaseApi.saveUser(UserDataLocal.fromEntity(user).copy(id = null))
    }

    override suspend fun updateUser(user: User) {
        databaseApi.updateUser(UserDataLocal.fromEntity(user))
    }

    override suspend fun removeUser(user: User) {
        databaseApi.deleteUser(UserDataLocal.fromEntity(user))
    }

    override fun isLoggedIn(): Boolean {
        return appPreferences.isRemember && appPreferences.accessToken != null
    }

    override fun getAppTheme() = appPreferences.appTheme

    override fun changeAppTheme(mode: Int) {
        appPreferences.appTheme = mode
    }

    override val forceLogoutEvent: Flow<String?> = appPreferences.forceLogoutFlow

    override fun getAppLocalize(): String = appPreferences.language

    override fun changeAppLocalize(mode: String) {
        appPreferences.language = mode
    }
}
