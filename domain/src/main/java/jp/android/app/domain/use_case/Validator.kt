package jp.android.app.domain.use_case

import jp.android.app.domain.data.AppException
import jp.android.app.domain.data.AppResult

private val validator = Validator()

fun validate(validate: Validator.() -> Unit): AppResult<Unit> {
    return try {
        AppResult.Success(validate(validator))
    } catch (e: AppException.Local.ValidationException) {
        AppResult.Failure(e)
    }
}

class Validator {

    object Constant {

    }

    fun validateEmail(email: String?) {
        if (email.isNullOrBlank()) {
            throw AppException.Local.ValidationException.EmptyEmail
        }
        val emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
        if (!emailRegex.toRegex().matches(email)) {
            throw AppException.Local.ValidationException.InvalidEmail
        }
    }

    fun validatePassword(password: String?) {
        if (password.isNullOrBlank()) {
            throw AppException.Local.ValidationException.EmptyPassword
        }
    }
}
