package jp.android.app.common_ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<S : BaseState, E : BaseEvent>(initialState: S) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()
    val state get() = uiState.value

    private val _event = MutableSharedFlow<E>()
    val event: Flow<E> = _event.asSharedFlow()

    protected suspend fun emitEvent(event: E) {
        _event.emit(event)
    }

    protected fun emitState(handler: (oldState: S) -> S) {
        _uiState.update(handler)
    }

    companion object {
        val NO_STATE = object : BaseState {}
    }
}
