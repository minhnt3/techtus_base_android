package jp.android.app.features.user

import dagger.hilt.android.lifecycle.HiltViewModel
import jp.android.app.common_ui.base.BaseEvent
import jp.android.app.common_ui.base.BaseState
import jp.android.app.common_ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
) : BaseViewModel<BaseState, BaseEvent>(NO_STATE) {
}
