/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [CustomRule.kt] created by Ji Sungbin on 22. 8. 29. 오전 3:12
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground

import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import team.duckie.quackquack.lint.custom.rule.annotation.NewImmutableCollection
import team.duckie.quackquack.lint.custom.rule.annotation.NewModifier

@NewModifier
@JvmInline
value class ModifierWrapper(val modifier: Modifier)

@NewImmutableCollection
@Immutable
@JvmInline
value class ImmutableListWrapper(val list: List<Any>)
