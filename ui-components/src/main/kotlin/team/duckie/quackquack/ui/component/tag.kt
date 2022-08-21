/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [tag.kt] created by Ji Sungbin on 22. 8. 21. 오후 2:42
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.runtime.Composable

@Composable
fun QuackSimpleTag(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
) {

}

@Composable
fun QuackBoldTag(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
) {

}

@Composable
fun QuackIconTag(
    isSelected: Boolean,
    text: String,
    icon: QuackIcon,
    onClickTag: () -> Unit,
    onClickIcon: () -> Unit,
) {

}
