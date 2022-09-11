/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [KDoc.kt] created by ricky_0_k on 22. 9, 11. 오후 3:21
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.common.lint

import org.jetbrains.kotlin.kdoc.psi.impl.KDocTag

val KDocTag.content get() = this.getContent()
