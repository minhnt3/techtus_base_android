package jp.android.app.data.source.local.database.config

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@ProvidedTypeConverter
class Converters(private val moshi: Moshi) {
    @TypeConverter
    fun jsonFromListString(list: List<String>?): String {
        return moshi.adapter<List<String>>(
            Types.newParameterizedType(
                MutableList::class.java,
                String::class.java
            )
        ).toJson(list)
    }

    @TypeConverter
    fun listStringFromJson(value: String): List<String>? {
        return moshi
            .adapter<List<String>>(
                Types.newParameterizedType(
                    MutableList::class.java,
                    String::class.java
                )
            )
            .fromJson(value)
    }
}
