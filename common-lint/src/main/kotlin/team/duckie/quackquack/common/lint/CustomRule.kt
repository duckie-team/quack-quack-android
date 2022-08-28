/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [CustomRule.kt] created by Ji Sungbin on 22. 8. 29. 오전 6:17
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.common.lint

internal object CustomRule {
    @Suppress("UNCHECKED_CAST")
    private fun getAllowList(
        customRuleClass: Class<*>,
        customRule: Any,
    ): List<String> {
        val getAllowList = customRuleClass.getDeclaredMethod("getAllowList")
        return getAllowList(customRule) as List<String>
    }

    val Modifier = try {
        val customRuleClass =
            Class.forName("team.duckie.quackquack.lint.custom.rule.Custom_Modifier")
        val customRule = customRuleClass.getDeclaredConstructor().newInstance()
        getAllowList(
            customRuleClass = customRuleClass,
            customRule = customRule,
        ).also { allowModifiers ->
            println("사용자 추가 Modifier: ${allowModifiers.joinToString()}")
        }
    } catch (ignored: ClassNotFoundException) {
        println("사용자 추가 Modifier 를 찾지 못했습니다. 기본값을 그대로 사용합니다.")
        emptyList()
    }

    val Collection = try {
        val customRuleClass =
            Class.forName("team.duckie.quackquack.lint.custom.rule.Custom_Collection")
        val customRule = customRuleClass.getDeclaredConstructor().newInstance()
        getAllowList(
            customRuleClass = customRuleClass,
            customRule = customRule,
        ).also { allowCollections ->
            println("사용자 추가 Collection: ${allowCollections.joinToString()}")
        }
    } catch (ignored: ClassNotFoundException) {
        println("사용자 추가 MutableCollection 을 찾지 못했습니다. 기본값을 그대로 사용합니다.")
        emptyList()
    }

    val ImmutableCollection = try {
        val customRuleClass =
            Class.forName("team.duckie.quackquack.lint.custom.rule.Custom_ImmutableCollection")
        val customRule = customRuleClass.getDeclaredConstructor().newInstance()
        getAllowList(
            customRuleClass = customRuleClass,
            customRule = customRule,
        ).also { allowImmutableCollections ->
            println("사용자 추가 ImmutableCollection: ${allowImmutableCollections.joinToString()}")
        }
    } catch (ignored: ClassNotFoundException) {
        println("사용자 추가 ImmutableCollection 을 찾지 못했습니다. 기본값을 그대로 사용합니다.")
        emptyList()
    }
}
