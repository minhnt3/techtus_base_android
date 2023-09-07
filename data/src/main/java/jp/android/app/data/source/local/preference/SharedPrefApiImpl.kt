package jp.android.app.data.source.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPrefApiImpl(context: Context, private val moshi: Moshi) : SharedPrefApi {
    private val sharedPreferences = SharedPrefUtils.getSharedPreferences(context)

    private inline fun <reified T> SharedPreferences.delegate(
        crossinline getter: SharedPreferences.(key: String, defaultValue: T) -> T,
        crossinline setter: SharedPreferences.Editor.(key: String, value: T) -> SharedPreferences.Editor,
        defaultValue: T,
        key: String,
        commit: Boolean = false,
    ): ReadWriteProperty<Any, T> = object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>) = getter(key, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = edit(commit) { setter(key, value) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> delegate(
        defaultValue: T,
        key: String,
        commit: Boolean
    ): ReadWriteProperty<Any, T> {
        return when (defaultValue) {
            is String? -> sharedPreferences.delegate(
                SharedPreferences::getString,
                SharedPreferences.Editor::putString,
                defaultValue,
                key,
                commit
            )

            is Set<*>? -> sharedPreferences.delegate(
                SharedPreferences::getStringSet,
                SharedPreferences.Editor::putStringSet,
                defaultValue?.filterIsInstanceTo(mutableSetOf()),
                key,
                commit
            )

            is Boolean -> sharedPreferences.delegate(
                SharedPreferences::getBoolean,
                SharedPreferences.Editor::putBoolean,
                defaultValue,
                key,
                commit
            )

            is Int -> sharedPreferences.delegate(
                SharedPreferences::getInt,
                SharedPreferences.Editor::putInt,
                defaultValue,
                key,
                commit
            )

            is Long -> sharedPreferences.delegate(
                SharedPreferences::getLong,
                SharedPreferences.Editor::putLong,
                defaultValue,
                key,
                commit
            )

            is Float -> sharedPreferences.delegate(
                SharedPreferences::getFloat,
                SharedPreferences.Editor::putFloat,
                defaultValue,
                key,
                commit
            )

            else -> {
                error("Not support for type clazz")
            }
        } as ReadWriteProperty<Any, T>
    }

    override fun removeKey(key: String) {
        sharedPreferences.edit { remove(key) }
    }

    override fun clear() {
        sharedPreferences.edit { clear() }
    }

    override fun <T> putList(key: String, clazz: Class<T>, list: List<T>) {
        val listMyData = Types.newParameterizedType(MutableList::class.java, clazz)
        val adapter: JsonAdapter<List<T>> = moshi.adapter(listMyData)
        sharedPreferences.edit { putString(key, adapter.toJson(list)) }
    }

    override fun <T> getList(key: String, clazz: Class<T>): List<T>? {
        val listMyData = Types.newParameterizedType(MutableList::class.java, clazz)
        val adapter: JsonAdapter<List<T>> = moshi.adapter(listMyData)
        return sharedPreferences.getString(key, null)?.let { adapter.fromJson(it) }
    }

    override fun <T> jsonFormObject(value: T?, clazz: Class<T>): String {
        return moshi.adapter(clazz).toJson(value)
    }

    override fun <T> jsonFromList(value: List<T>?, clazz: Class<T>): String {
        return moshi.adapter<List<T>>(
            Types.newParameterizedType(
                MutableList::class.java,
                clazz
            )
        ).toJson(value)
    }

    override fun <T> jsonFromHashSet(value: Set<T>?, clazz: Class<T>): String {
        return moshi.adapter<Set<T>>(
            Types.newParameterizedType(Set::class.java, clazz)
        ).toJson(value)
    }

    override fun <K, V> jsonFromHashMap(
        value: Map<K, V>?,
        clazzKey: Class<K>,
        clazzValue: Class<V>
    ): String {
        return moshi.adapter<Map<K, V>>(
            Types.newParameterizedType(
                Map::class.java,
                clazzKey,
                clazzValue
            )
        ).toJson(value)
    }

    override fun <T> objectFromJson(value: String?, clazz: Class<T>): T? {
        return value?.let {
            moshi.adapter(clazz).parse(it)
        }
    }

    override fun <T> listFromJson(value: String?, clazz: Class<T>): List<T>? {
        return value?.let {
            moshi.adapter<List<T>>(
                Types.newParameterizedType(MutableList::class.java, clazz)
            ).parse(it)
        }
    }

    override fun <T> hashSetFromJson(value: String?, clazz: Class<T>): Set<T>? {
        return value?.let {
            moshi.adapter<HashSet<T>>(
                Types.newParameterizedType(HashSet::class.java, clazz)
            ).parse(it)
        }
    }

    override fun <K, V> hashMapFromJson(
        value: String?,
        clazzKey: Class<K>,
        clazzValue: Class<V>
    ): Map<K, V>? {
        return value?.let {
            moshi.adapter<Map<K, V>>(
                Types.newParameterizedType(Map::class.java, clazzKey, clazzValue)
            ).parse(it)
        }
    }

    private fun <T> JsonAdapter<T>.parse(value: String) = try {
        fromJson(value)
    } catch (e: Exception) {
        null
    }

    override fun observeString(key: String, defValue: String?): Flow<String?> =
        sharedPreferences.observeKey(key, defValue)

    override fun observeStringSet(key: String, defValue: Set<String>): Flow<Set<String>> =
        sharedPreferences.observeKey(key, defValue)

    override fun observeBoolean(key: String, defValue: Boolean): Flow<Boolean> =
        sharedPreferences.observeKey(key, defValue)

    override fun observeInt(key: String, defValue: Int): Flow<Int> =
        sharedPreferences.observeKey(key, defValue)

    override fun observeLong(key: String, defValue: Long): Flow<Long> =
        sharedPreferences.observeKey(key, defValue)

    override fun observeFloat(key: String, defValue: Float): Flow<Float> =
        sharedPreferences.observeKey(key, defValue)

    override fun <T> observeObject(key: String, clazz: Class<T>): Flow<T?> =
        observeString(key, null).map { objectFromJson(it, clazz) }
}
