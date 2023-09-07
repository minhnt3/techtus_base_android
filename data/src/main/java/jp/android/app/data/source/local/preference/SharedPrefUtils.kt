package jp.android.app.data.source.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

object SharedPrefUtils {
    private var sharedPref: SharedPreferences? = null
    fun getSharedPreferences(context: Context): SharedPreferences {
        if (sharedPref == null)
            sharedPref = EncryptedSharedPreferences.create(
                SharedPrefKey.PREFS_NAME,
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
            )
        return sharedPref!!
    }
}

inline fun <reified T> SharedPreferences.observeKey(
    key: String,
    default: T
): Flow<T> = callbackFlow {
    val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, k ->
        if (key == k) {
            trySend(getValue(key, default))
        }
    }
    send(getValue(key, default))
    registerOnSharedPreferenceChangeListener(listener)
    awaitClose {
        unregisterOnSharedPreferenceChangeListener(listener)
    }
}.buffer(capacity = 1).distinctUntilChanged()

@Suppress("IMPLICIT_CAST_TO_ANY")
inline fun <reified T> SharedPreferences.getValue(
    key: String,
    default: T
): T = when (val clazz = T::class) {
    String::class -> getString(key, default as String?)
    Set::class -> getStringSet(
        key, (default as Set<*>?)?.filterIsInstanceTo(LinkedHashSet())
    )
    Boolean::class -> getBoolean(key, default as Boolean)
    Int::class -> getInt(key, default as Int)
    Long::class -> getLong(key, default as Long)
    Float::class -> getFloat(key, default as Float)
    else -> error("Not support for type $clazz")
} as T
