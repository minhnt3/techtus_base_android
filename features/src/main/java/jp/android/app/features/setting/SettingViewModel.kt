package jp.android.app.features.setting

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.android.app.common_ui.base.BaseEvent
import jp.android.app.common_ui.base.BaseState
import jp.android.app.common_ui.base.BaseViewModel
import jp.android.app.domain.use_case.ChangeAppLocalizeUseCase
import jp.android.app.domain.use_case.ChangeAppThemeUseCase
import jp.android.app.domain.use_case.GetAppLocalizeUseCase
import jp.android.app.domain.use_case.GetAppThemeUseCase
import jp.android.app.domain.use_case.LogoutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getAppThemeUseCase: GetAppThemeUseCase,
    private val changeAppThemeUseCase: ChangeAppThemeUseCase,
    private val getAppLocalizeUseCase: GetAppLocalizeUseCase,
    private val changeAppLocalizeUseCase: ChangeAppLocalizeUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<BaseState, SettingEvent>(NO_STATE) {

    fun logout() {
        viewModelScope.launch {
            logoutUseCase().onSuccess {
                emitEvent(SettingEvent.LogoutSuccess)
            }
        }
    }
}

sealed interface SettingEvent : BaseEvent {
    object LogoutSuccess: SettingEvent
}
