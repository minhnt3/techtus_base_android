package jp.android.app.features.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.android.app.common_ui.base.BaseEvent
import jp.android.app.common_ui.base.BaseState
import jp.android.app.common_ui.base.BaseViewModel
import jp.android.app.domain.use_case.CheckLoggedInUseCase
import jp.android.app.domain.use_case.ForceLogoutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkLoggedInUseCase: CheckLoggedInUseCase,
    forceLogoutUseCase: ForceLogoutUseCase,
) : BaseViewModel<MainState, MainEvent>(MainState()) {

    init {
        viewModelScope.launch {
            forceLogoutUseCase().collect {
                emitEvent(MainEvent.ForceLogout)
            }
        }
    }

    fun isLoggedIn() = checkLoggedInUseCase()

    fun logout() {
        viewModelScope.launch {
            emitEvent(MainEvent.LogoutSuccess)
        }
    }
}

data class MainState(
    val isDarkMode: Boolean = false,
): BaseState

sealed class MainEvent: BaseEvent {
    object ForceLogout : MainEvent()
    object LogoutSuccess : MainEvent()
}
