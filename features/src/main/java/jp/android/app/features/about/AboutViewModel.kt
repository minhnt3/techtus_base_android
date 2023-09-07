package jp.android.app.features.about

import jp.android.app.common_ui.base.BaseEvent
import jp.android.app.common_ui.base.BaseState
import jp.android.app.common_ui.base.BaseViewModel

class AboutViewModel : BaseViewModel<AboutState, AboutEvent>(AboutState()) {
}

sealed interface AboutEvent : BaseEvent

data class AboutState(
    val id: Int = 0,
) : BaseState