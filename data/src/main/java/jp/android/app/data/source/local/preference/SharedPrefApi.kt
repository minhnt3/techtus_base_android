package jp.android.app.data.source.local.preference

import kotlinx.coroutines.flow.Flow
import kotlin.properties.ReadWriteProperty

interface SharedPrefApi {
    fun <T : Any?> delegate(
        defaultValue: T,
        key: String,
        commit: Boolean = false
    ): ReadWriteProperty<Any, T>
    fun removeKey(key: String)
    fun clear()
    fun <T> putList(key: String, clazz: Class<T>, list: List<T>)
    fun <T> getList(key: String, clazz: Class<T>): List<T>?
    fun <T> jsonFormObject(value: T?, clazz: Class<T>): String
    fun <T> jsonFromList(value: List<T>?, clazz: Class<T>): String
    fun <T> jsonFromHashSet(value: Set<T>?, clazz: Class<T>): String
    fun <K, V> jsonFromHashMap(value: Map<K, V>?, clazzKey: Class<K>, clazzValue: Class<V>): String
    fun <T> objectFromJson(value: String?, clazz: Class<T>): T?
    fun <T> listFromJson(value: String?, clazz: Class<T>): List<T>?
    fun <T> hashSetFromJson(value: String?, clazz: Class<T>): Set<T>?
    fun <K, V> hashMapFromJson(value: String?, clazzKey: Class<K>, clazzValue: Class<V>): Map<K, V>?

    // flow
    fun observeString(key: String, defValue: String? = null): Flow<String?>
    fun observeStringSet(key: String, defValue: Set<String> = emptySet()): Flow<Set<String>>
    fun observeBoolean(key: String, defValue: Boolean = false): Flow<Boolean>
    fun observeInt(key: String, defValue: Int = 0): Flow<Int>
    fun observeLong(key: String, defValue: Long = 0L): Flow<Long>
    fun observeFloat(key: String, defValue: Float = 0f): Flow<Float>
    fun <T> observeObject(key: String, clazz: Class<T>): Flow<T?>
}
