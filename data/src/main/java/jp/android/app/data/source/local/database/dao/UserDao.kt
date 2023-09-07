package jp.android.app.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import jp.android.app.data.model.UserDataLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id = :id")
    fun getById(id: String): Flow<UserDataLocal?>

    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<UserDataLocal>>

    @Query("SELECT * FROM user WHERE id <> :id")
    fun getAllNotById(id: String): Flow<List<UserDataLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserDataLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserDataLocal>)

    @Delete
    suspend fun delete(user: UserDataLocal)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Update
    suspend fun update(vararg user: UserDataLocal)
}
