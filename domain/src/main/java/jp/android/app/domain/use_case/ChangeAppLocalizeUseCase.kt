package jp.android.app.domain.use_case;

import javax.inject.Inject;

import jp.android.app.domain.repository.Repository

class ChangeAppLocalizeUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(mode: String) = repository.changeAppLocalize(mode)
}