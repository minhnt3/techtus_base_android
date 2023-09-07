package jp.android.app.ktlint_rules

import com.pinterest.ktlint.core.Rule
import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.kotlin.KtNodeTypes.ELSE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

class NoElseIfConditionRule : Rule("no-else-if-condition") {
    override fun visit(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (node.elementType == KtNodeTypes.IF) {
            node.findChildByType(ELSE)?.let {
                if (it.firstChildNode.elementType == KtNodeTypes.IF) {
                    emit(
                        node.firstChildNode.startOffset,
                        "Please use syntax WHEN to replace ELSE IF",
                        false
                    )
                }
            }
        }
    }
}
