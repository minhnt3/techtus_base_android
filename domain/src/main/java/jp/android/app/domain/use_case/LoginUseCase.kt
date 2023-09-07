package jp.android.app.domain.use_case

import jp.android.app.domain.data.AppResult
import jp.android.app.domain.entity.User
import jp.android.app.domain.repository.Repository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: Repository,
) : BaseUseCase() {
    suspend operator fun invoke(email: String, password: String, isRemember: Boolean): AppResult<User> {
        return repository.login(email, password, isRemember)
    }
}