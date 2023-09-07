package jp.android.app.data.source.remote.middleware

import jp.android.app.data.source.local.preference.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val appPreferences: AppPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        appPreferences.accessToken?.let { accessToken ->
            requestBuilder.addHeader(AUTHORIZATION, "$BEARER $accessToken")
        }
        return chain.proceed(requestBuilder.build())
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer"
    }
}
