package jp.android.app.domain.data

sealed class AppResult<out T> {
    data class Success<out T>(val data: T) : AppResult<T>()
    data class Failure(val exception: AppException) : AppResult<Nothing>()
//    object Loading : AppResult<Nothing>()

    inline fun onSuccess(action: (T) -> Unit): AppResult<T> {
        if (this is Success) action(data)
        return this
    }

    inline fun onFailure(action: (AppException) -> Unit): AppResult<T> {
        if (this is Failure) action(exception)
        return this
    }

//    inline fun onLoading(action: (Boolean) -> Unit): AppResult<T> {
//        action(this is Loading)
//        return this
//    }

    inline fun <R> map(transform: (T) -> R): AppResult<R> {
        return if (this is Success) Success(transform(data)) else this as Failure
    }

    inline fun <R> flatMap(transform: (T) -> AppResult<R>): AppResult<R> {
        return if (this is Success) transform(data) else this as Failure
    }

    inline val dataOrNull get() = (this as? Success)?.data

    inline val exceptionOrNull get() = (this as? Failure)?.exception
}
