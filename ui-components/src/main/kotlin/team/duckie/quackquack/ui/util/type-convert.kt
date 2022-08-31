/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [type-convert.kt] created by Ji Sungbin on 22. 8. 31. 오후 3:26
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:OptIn(ExperimentalUnitApi::class)

package team.duckie.quackquack.ui.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

/**
 * Float 를 Sp 로 변환합니다.
 */
@Stable
fun Float.toSp() = TextUnit(
    value = this,
    type = TextUnitType.Sp,
)
