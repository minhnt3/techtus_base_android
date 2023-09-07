package jp.android.app.domain.use_case

import jp.android.app.domain.repository.Repository
import javax.inject.Inject

class GetAppThemeUseCase @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(): Int {
        return repository.getAppTheme()
    }
}