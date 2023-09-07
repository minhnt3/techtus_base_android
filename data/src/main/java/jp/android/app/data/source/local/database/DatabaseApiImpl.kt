package jp.android.app.data.source.local.database

import jp.android.app.data.model.UserDataLocal
import jp.android.app.data.source.local.database.config.DatabaseManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class DatabaseApiImpl(private val databaseManager: DatabaseManager) : DatabaseApi {

    override fun getAllUsers(): Flow<List<UserDataLocal>> {
        return databaseManager
            .userDao()
            .getAll()
            .distinctUntilChanged()
    }

    override fun getAllUserNotById(id: String): Flow<List<UserDataLocal>> {
        return databaseManager
            .userDao()
            .getAllNotById(id)
            .distinctUntilChanged()
    }

    override suspend fun saveUser(user: UserDataLocal) {
        databaseManager
            .userDao()
            .insert(user)
    }

    override suspend fun saveAllUser(users: List<UserDataLocal>) {
        return databaseManager
            .userDao()
            .insertAll(users)
    }

    override suspend fun deleteAllUser() {
        return databaseManager
            .userDao()
            .deleteAll()
    }

    override suspend fun updateUser(userData: UserDataLocal) {
        databaseManager
            .userDao()
            .update(userData)
    }

    override suspend fun deleteUser(userData: UserDataLocal) {
        databaseManager
            .userDao()
            .delete(userData)
    }

    override fun getUserById(id: String): Flow<UserDataLocal?> {
        return databaseManager.userDao()
            .getById(id)
            .distinctUntilChanged()
    }
}
