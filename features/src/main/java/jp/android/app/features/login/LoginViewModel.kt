package jp.android.app.features.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.android.app.common_ui.base.BaseEvent
import jp.android.app.common_ui.base.BaseState
import jp.android.app.common_ui.base.BaseViewModel
import jp.android.app.domain.data.AppException
import jp.android.app.domain.entity.User
import jp.android.app.domain.use_case.LoginUseCase
import jp.android.app.domain.use_case.validate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginState, LoginEvent>(LoginState()) {

    fun onLoginPressed() {
        viewModelScope.launch {
            validate {
                validateEmail(state.email)
                validatePassword(state.password)
            }.flatMap {
                emitState {
                    it.copy(
                        isLoginLoading = true,
                    )
                }
                loginUseCase(state.email, state.password, state.isRemember)
            }.onSuccess { user ->
                emitState {
                    it.copy(
                        isLoginLoading = false,
                        appException = null,
                    )
                }
                emitEvent(LoginEvent.LoginSuccess(user))
            }.onFailure { appException ->
                emitState {
                    it.copy(
                        appException = appException,
                        isLoginLoading = false,
                    )
                }
                emitEvent(LoginEvent.LoginFailure(appException))
            }
        }
    }

    fun onEmailOrPasswordChanged(email: String = state.email, password: String = state.password) {
        emitState {
            it.copy(
                email = email,
                password = password,
            )
        }
    }

    fun onRememberCheckedChanged(isChecked: Boolean) {
        emitState {
            it.copy(
                isRemember = isChecked,
            )
        }
    }

    fun onClearError() {
        emitState {
            it.copy(
                appException = null,
            )
        }
    }
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val appException: AppException? = null,
    val isRemember: Boolean = false,
    val isLoginLoading: Boolean = false,
) : BaseState

sealed interface LoginEvent : BaseEvent {
    data class LoginSuccess(val user: User) : LoginEvent
    data class LoginFailure(val appException: AppException) : LoginEvent
}
