package jp.android.app.ktlint_rules

import com.pinterest.ktlint.core.RuleSet
import com.pinterest.ktlint.core.RuleSetProvider

class CustomRuleSetProvider : RuleSetProvider {
    override fun get() = RuleSet(
        "ktlint-rules",
        NoElseIfConditionRule(),
        NoSingleNullableCheckRule(),
        NoVarCompanionRule(),
        NoExclExclRule(),
    )
}
