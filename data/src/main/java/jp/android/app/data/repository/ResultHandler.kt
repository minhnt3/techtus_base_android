package jp.android.app.data.repository

import com.squareup.moshi.JsonDataException
import jp.android.app.data.model.base.BaseResponse
import jp.android.app.domain.data.AppException
import jp.android.app.domain.data.AppResult
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

open class ResultHandler {
//    suspend inline fun <T, S> getResult(
//        crossinline action: suspend () -> Response<S>,
//        dispatcher: CoroutineDispatcher,
//        retryCount: Int = 0,
//        retryDelayTime: Long = 0,
//        crossinline mapper: (S) -> T,
//    ): AppResult<T> {
//        return withContext(dispatcher) {
//            var result: AppResult<T>? = null
//            repeat(retryCount) {
//                result = getResult(action, mapper)
//                result!!.let {
//                    if (it !is AppResult.Failure || it.exception !is AppException.Remote.NetworkException) {
//                        return@withContext it
//                    }
//                }
//                delay(retryDelayTime)
//            }
//            result!!
//        }
//    }

    protected inline fun <T> getResult(
        action: () -> BaseResponse<T>,
    ): AppResult<T> {
        return try {
            action().data?.let { AppResult.Success(it) } ?: AppResult.Failure(AppException.Remote.DataException)
        } catch (e: Exception) {
            e.mapToResult()
        }
    }

    protected fun Exception.mapToResult(): AppResult.Failure {
        return when (this) {
            is IOException -> AppResult.Failure(AppException.Remote.NetworkException)
            is JsonDataException -> AppResult.Failure(AppException.Remote.DataException)
            is HttpException -> {
                if (code() == 401) {
                    AppResult.Failure(AppException.Remote.UnauthorizedException)
                } else if (code() in 500..511) {
                    AppResult.Failure(AppException.Remote.ServerException)
                } else try {
                    val errorJson = response()?.errorBody()?.string()?.let { JSONObject(it) }
                    val errors = errorJson?.getJSONArray("errors")
                    if (errors != null && errors.length() > 0) {
                        val error = errors.getJSONObject(0)
                        val code = if (error.has("code")) error.getString("code") else null
                        val message = if (error.has("message")) error.getString("message") else null
                        AppResult.Failure(AppException.Remote.ApiException(code, message))
                    } else {
                        AppResult.Failure(AppException.Remote.ApiException())
                    }
                } catch (e: JSONException) {
                    AppResult.Failure(AppException.Remote.UnknownException(e))
                }
            }
            else -> AppResult.Failure(AppException.Remote.UnknownException(this))
        }
    }

    protected fun downloadFile(url: String?, filePath: String): AppResult<Unit> {
        return try {
            URL(url).openStream().use { inputStream ->
                BufferedInputStream(inputStream).use { bufferedInputStream ->
                    FileOutputStream(filePath).use { outputStream ->
                        val data = ByteArray(1024)
                        var count: Int
                        while (bufferedInputStream.read(data, 0, 1024).also { count = it } != -1) {
                            outputStream.write(data, 0, count)
                        }
                    }
                }
            }
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Failure(AppException.Remote.UnknownException(e))
        }
    }
}
