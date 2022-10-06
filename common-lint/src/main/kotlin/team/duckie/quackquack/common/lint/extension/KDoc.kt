/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.common.lint.extension

import org.jetbrains.kotlin.kdoc.psi.impl.KDocTag

val KDocTag.content get() = getContent()
