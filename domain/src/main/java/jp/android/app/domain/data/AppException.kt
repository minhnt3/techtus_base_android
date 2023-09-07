package jp.android.app.domain.data

sealed class AppException : Exception() {
    sealed class Remote : AppException() {
        data class ApiException(val code: String? = null, override val message: String? = null) : Remote()
        object DataException : Remote()
        object UnauthorizedException : Remote()
        object NetworkException : Remote()
        object ServerException : Remote()
        data class UnknownException(val root: Exception) : Remote()
    }

    sealed class Local : AppException() {
        object ParseException : Local()
        object UnsupportedException : Local()
        sealed class ValidationException : Local() {
            object InvalidEmail : ValidationException()
            object EmptyEmail : ValidationException()
            object InvalidPassword : ValidationException()
            object EmptyPassword : ValidationException()
        }
    }
}