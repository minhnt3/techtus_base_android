package jp.android.app.data.source.local.preference

import jp.android.app.shared.AppCoroutineDispatchers
import jp.android.app.shared.Constants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn

class AppPreferences(
    private val sharedPrefApi: SharedPrefApi,
    appCoroutineDispatchers: AppCoroutineDispatchers
) {
    var accessToken by sharedPrefApi.delegate(
        null as String?,
        SharedPrefKey.PREF_ACCESS_TOKEN,
        commit = true
    )
    var refreshToken by sharedPrefApi.delegate(
        null as String?,
        SharedPrefKey.PREF_REFRESH_TOKEN,
        commit = true
    )
    var email by sharedPrefApi.delegate(
        null as String?,
        SharedPrefKey.PREF_USERNAME,
        commit = true
    )
    var password by sharedPrefApi.delegate(
        null as String?,
        SharedPrefKey.PREF_PASSWORD,
        commit = true
    )
    var isRemember by sharedPrefApi.delegate(
        false,
        SharedPrefKey.PREF_IS_REMEMBER,
        commit = true
    )
    var appTheme by sharedPrefApi.delegate(
        defaultValue = -1,
        SharedPrefKey.PREF_APP_THEME,
        commit = true
    )

    var language by sharedPrefApi.delegate(
        Constants.LANGUAGE_DEFAULT,
        SharedPrefKey.PREF_APP_LOCALIZE,
        commit = true
    )

    fun clearData() {
        sharedPrefApi.clear()
    }

    fun setLoginInfo(email: String, password: String) {
        this.email = email
        this.password = password
    }

    private var userNameObservable = sharedPrefApi.observeString(SharedPrefKey.PREF_USERNAME)
        .flowOn(appCoroutineDispatchers.io)

    private var tokenObservable = sharedPrefApi.observeString(SharedPrefKey.PREF_ACCESS_TOKEN)
        .flowOn(appCoroutineDispatchers.io)

    private var passwordObservable = sharedPrefApi.observeString(SharedPrefKey.PREF_PASSWORD)
        .flowOn(appCoroutineDispatchers.io)

    private val _forceLogoutFlow = MutableSharedFlow<String?>()
    val forceLogoutFlow = _forceLogoutFlow.asSharedFlow()

    suspend fun forceLogout() {
        _forceLogoutFlow.emit(email)
    }
}
