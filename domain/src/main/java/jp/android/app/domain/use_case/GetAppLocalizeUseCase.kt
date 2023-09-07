package jp.android.app.domain.use_case;

import javax.inject.Inject;

import jp.android.app.domain.repository.Repository;

class GetAppLocalizeUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke() = repository.getAppLocalize()
}