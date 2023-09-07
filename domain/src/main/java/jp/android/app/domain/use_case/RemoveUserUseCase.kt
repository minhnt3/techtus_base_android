package jp.android.app.domain.use_case

import jp.android.app.domain.entity.User
import jp.android.app.domain.repository.Repository
import javax.inject.Inject

class RemoveUserUseCase @Inject constructor(
    private val repository: Repository,
): BaseUseCase() {
    suspend operator fun invoke(user: User) {
        repository.removeUser(user)
    }
}