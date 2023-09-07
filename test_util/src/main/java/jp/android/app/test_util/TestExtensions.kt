package jp.android.app.test_util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.text.TextLayoutResult
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//inline fun <T> Flow<T>.runTest(crossinline test: (List<T>) -> Unit) = kotlinx.coroutines.test.runTest {
//    test(mutableListOf<T>().also { backgroundScope.launch(Dispatchers.Main) { toList(it) } })
//}

fun runTest(test: suspend CoroutineScope.() -> Unit) = kotlinx.coroutines.test.runTest {
    backgroundScope.launch(Dispatchers.Main, block = test)
}

fun SemanticsNodeInteraction.assertTextColor(color: Color) = assert(
    SemanticsMatcher("${SemanticsProperties.Text.name} is of color '$color'") {
        val textLayoutResults = mutableListOf<TextLayoutResult>()
        it.config.getOrNull(SemanticsActions.GetTextLayoutResult)
            ?.action
            ?.invoke(textLayoutResults)
        return@SemanticsMatcher if (textLayoutResults.isEmpty()) {
            false
        } else {
            textLayoutResults.first().layoutInput.style.color == color
        }
    }
)

fun stringRes(resId: Int) = InstrumentationRegistry.getInstrumentation().targetContext.getString(resId)