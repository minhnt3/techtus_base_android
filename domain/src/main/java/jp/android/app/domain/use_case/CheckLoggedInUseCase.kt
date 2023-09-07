package jp.android.app.domain.use_case

import jp.android.app.domain.repository.Repository
import javax.inject.Inject

class CheckLoggedInUseCase @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(): Boolean {
        return repository.isLoggedIn()
    }
}