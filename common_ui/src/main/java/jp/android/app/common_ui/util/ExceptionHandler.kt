package jp.android.app.common_ui.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import jp.android.app.domain.data.AppException
import jp.android.app.resources.R

object ExceptionHandler {

    @Composable
    fun getExceptionMessage(e: AppException?): String {
        return stringResource(getExceptionMessageResId(e))
    }

    fun getExceptionMessage(context: Context, e: AppException?): String {
        return context.resources.getString(getExceptionMessageResId(e))
    }

    private fun getExceptionMessageResId(e: AppException?) = when (e) {
        AppException.Local.ValidationException.EmptyEmail -> R.string.validation_exception_empty_email
        AppException.Local.ValidationException.InvalidEmail -> R.string.validation_exception_invalid_email
        AppException.Local.ValidationException.EmptyPassword -> R.string.validation_exception_empty_password
        AppException.Remote.NetworkException -> R.string.remote_exception_network
        else -> R.string.unknown_exception
    }
}