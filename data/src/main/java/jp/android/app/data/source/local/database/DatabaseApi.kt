package jp.android.app.data.source.local.database

import jp.android.app.data.model.UserDataLocal
import kotlinx.coroutines.flow.Flow

interface DatabaseApi {
    fun getAllUsers(): Flow<List<UserDataLocal>>
    fun getAllUserNotById(id: String): Flow<List<UserDataLocal>>
    suspend fun saveUser(user: UserDataLocal)
    suspend fun saveAllUser(users: List<UserDataLocal>)
    suspend fun deleteAllUser()
    suspend fun updateUser(userData: UserDataLocal)
    suspend fun deleteUser(userData: UserDataLocal)
    fun getUserById(id: String): Flow<UserDataLocal?>
}
