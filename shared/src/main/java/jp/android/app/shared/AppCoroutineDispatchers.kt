package jp.android.app.shared

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppCoroutineDispatchers {
    val default: CoroutineDispatcher = Dispatchers.Default
    val main: CoroutineDispatcher = Dispatchers.Main
    val mainImmediate: CoroutineDispatcher = Dispatchers.Main.immediate
    val io: CoroutineDispatcher = Dispatchers.IO
}