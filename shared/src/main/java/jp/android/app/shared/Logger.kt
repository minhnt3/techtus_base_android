package jp.android.app.shared

import android.util.Log

private val ENABLED = BuildConfig.DEBUG
private const val LOGGER_TAG = "LOGGER_TAG"

fun logD(message: String?) = logD(LOGGER_TAG, message)

fun logD(clazz: Any, message: String?) = logD(clazz::class.java.name, message)

fun logD(tag: String, action: () -> String?) {
    if (ENABLED) {
        logD(tag, action())
    }
}

fun logD(tag: String, message: String?) {
    if (ENABLED) {
        Log.d(tag, message.toString())
    }
}

fun logE(message: String?) = logE(LOGGER_TAG, message)

fun logE(clazz: Any, message: String?) = logE(clazz::class.java.name, message)

fun logE(tag: String, action: () -> String?) {
    if (ENABLED) {
        logE(tag, action())
    }
}

fun logE(tag: String, message: String?) {
    if (ENABLED) {
        Log.e(tag, message.toString())
    }
}