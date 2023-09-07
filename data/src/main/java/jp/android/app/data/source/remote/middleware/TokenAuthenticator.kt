package jp.android.app.data.source.remote.middleware

import jp.android.app.data.source.local.preference.AppPreferences
import jp.android.app.data.source.remote.api_service.RefreshTokenApiService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val appPreferences: AppPreferences,
    private val refreshTokenApiService: RefreshTokenApiService,
) : Authenticator {
    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = appPreferences.refreshToken
        if (refreshToken.isNullOrBlank()) {
            return null
        }
        return runBlocking {
            mutex.withLock {
                val currentToken = appPreferences.refreshToken
                if (currentToken != refreshToken) { // Token is refreshed in another thread
                    currentToken
                } else {
                    try {
                        refreshTokenApiService.refreshToken(currentToken).data?.tokenInfo?.also {
                            appPreferences.accessToken = it.accessToken
                            appPreferences.refreshToken = it.refreshToken
                        }?.accessToken
                    } catch (e: Exception) {
                        appPreferences.forceLogout()
                        null
                    }
                }
            }
        }?.let { accessToken ->
            response.request().newBuilder()
                .header(HeaderInterceptor.AUTHORIZATION, "${HeaderInterceptor.BEARER} $accessToken")
                .build()
        }
    }
}

