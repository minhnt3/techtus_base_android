package jp.android.app.domain.use_case

import jp.android.app.domain.repository.Repository
import javax.inject.Inject

class ChangeAppThemeUseCase @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(mode: Int) {
        return repository.changeAppTheme(mode)
    }
}
