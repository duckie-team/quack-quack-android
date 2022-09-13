/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.modifier.quackClickable

/**
 * QuackTopAppBar 를 구현합니다.
 *
 */

private val QuackTopAppBarPadding = PaddingValues(
    horizontal = 16.dp,
)

/**
 * TODO : centerContent 의 width 를 어떻게 처리를 해줘야할듯
 */

@Composable
fun QuackTopAppBar(
    leadingIcon: QuackIcon,
    trailingIcon: QuackIcon? = null,
    secondTrailingIcon: QuackIcon? = null,
    trailingText: String? = null,
    headline: String? = null,
    centerContent: (@Composable () -> Unit)? = null,
    onClickLeadingIcon: (() -> Unit)? = null,
    onClickTrailingIcon: (() -> Unit)? = null,
    onClickSecondTrailingIcon: (() -> Unit)? = null,
    onClickTrailingText: (() -> Unit)? = null,
) {
    checkTopAppBarValidation(
        trailingIcon = trailingIcon,
        secondTrailingIcon = secondTrailingIcon,
        trailingText = trailingText,
        onClickTrailingIcon = onClickTrailingIcon,
        onClickSecondTrailingIcon = onClickSecondTrailingIcon,
        onClickTrailingText = onClickTrailingText,
    )
    Box(
        modifier = Modifier
            .applyQuackSize(
                width = QuackWidth.Fill,
                height = QuackHeight.Custom(48.dp),
            )
            .background(QuackColor.White.value)
            .padding(QuackTopAppBarPadding)
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackImage(
                icon = leadingIcon,
                onClick = onClickLeadingIcon,
            )
            if (headline != null) {
                QuackHeadLine2(
                    text = headline,
                )
            }
        }

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (secondTrailingIcon != null) {
                QuackImage(
                    icon = secondTrailingIcon,
                    onClick = onClickSecondTrailingIcon,
                )
            }

            if (trailingIcon != null) {
                QuackImage(
                    icon = trailingIcon,
                    onClick = onClickTrailingIcon,
                )
            }

            if (trailingText != null) {
                QuackSubtitle(
                    modifier = Modifier.quackClickable { onClickTrailingText?.invoke() },
                    text = trailingText,
                )
            }
        }

        Box(
            modifier = Modifier.align(Alignment.Center),
        ){
            centerContent?.invoke()
        }
    }
}

private fun checkTopAppBarValidation(
    trailingIcon: QuackIcon? = null,
    secondTrailingIcon: QuackIcon? = null,
    trailingText: String? = null,
    onClickTrailingIcon: (() -> Unit)? = null,
    onClickSecondTrailingIcon: (() -> Unit)? = null,
    onClickTrailingText: (() -> Unit)? = null,
){
    assert(trailingText == null || trailingIcon == null)
    if ( secondTrailingIcon != null){
        assert(trailingIcon != null && onClickSecondTrailingIcon != null)
    }
    if ( trailingIcon != null){
        assert(onClickTrailingIcon != null)
    }
    if ( trailingText != null){
        assert(onClickTrailingText != null)
    }
}
