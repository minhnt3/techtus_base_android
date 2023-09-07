package jp.android.app.data.source.local.database.config

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.android.app.data.model.UserDataLocal
import jp.android.app.data.source.local.database.config.DatabaseConfig.DATABASE_VERSION
import jp.android.app.data.source.local.database.dao.UserDao

@Database(
    entities = [UserDataLocal::class],
    version = DATABASE_VERSION,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class DatabaseManager : RoomDatabase() {
    abstract fun userDao(): UserDao
}
