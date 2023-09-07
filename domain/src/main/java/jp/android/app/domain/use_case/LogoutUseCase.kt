package jp.android.app.domain.use_case

import jp.android.app.domain.data.AppResult
import jp.android.app.domain.repository.Repository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: Repository,
) : BaseUseCase() {
    suspend operator fun invoke(): AppResult<Unit> {
        return repository.logout()
    }
}