/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.sugar.processor.poet

import team.duckie.quackquack.sugar.processor.ir.QuackComponentPrefix
import team.duckie.quackquack.sugar.processor.ir.SugarIrData
import team.duckie.quackquack.sugar.processor.ir.SugarTokenName

// TODO: Testing
internal fun SugarIrData.toSugarComponentName(tokenFqExpression: String): String {
  val tokenExpression = tokenFqExpression
    .substringAfterLast(".")
    .replaceFirstChar(Char::titlecase)
  return sugarName?.replace(SugarTokenName, tokenExpression)
    ?: referFqn
      .shortName()
      .asString()
      .toMutableList()
      .apply { addAll(QuackComponentPrefix.length, tokenExpression.toList()) }
      .joinToString("")
}
