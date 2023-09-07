package jp.android.app.data.source.remote.middleware

import jp.android.app.shared.logD
import jp.android.app.shared.logE
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor(
    private val isLog: Boolean,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().build()
        val response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            if (isLog) {
                logE(request.url().encodedPath() + " --> Error: $e")
            }
            throw e
        }
        if (isLog) {
            response.body()?.let {
                val body = it.source().apply { request(Long.MAX_VALUE) }.buffer.clone().readUtf8()
                logD(request.url().encodedPath() + " --> Response: $body")
            }
        }
        return response
    }
}