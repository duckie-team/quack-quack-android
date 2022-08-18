/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [operators.kt] created by Ji Sungbin on 22. 8. 18. 오후 10:04
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.common.lint

import com.android.tools.lint.detector.api.JavaContext
import org.jetbrains.uast.UMethod

fun JavaContext.isOperator(node: UMethod) = evaluator.isOperator(node)

fun JavaContext.hasOperator(node: UMethod) = node.findSuperMethods().any { method ->
    evaluator.isOperator(method)
}
