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

private val QuackTopAppBarPadding = PaddingValues(
    horizontal = 16.dp,
)
private val QuackTopAppBarHeight = 48.dp
private val QuackTopAppBarLeadingIconSpacedBy = 12.dp
private val QuackTopAppBarTrailingIconSpacedBy = 8.dp

/**
 * TODO : centerContent 의 width 를 어떻게 처리를 해줘야할듯
 * QuackTopAppBar 를 구현합니다
 *
 * @param modifier 다양한 Align 으로 사용하기 위해 Modifier 를 열어줍니다.
 * @param leadingIcon start 위치에 존재하는 icon
 * @param trailingIcon end 위치에 존재하는 icon
 * @param secondTrailingIcon end 왼쪽에 존재하는 icon
 * @param trailingText end 위치에 존재하는 text
 * @param headline leadingIcon 오른쪽에 위치하는 headline
 * @param centerContent 가운데 위치하는 Composable 람다 함수
 * @param onClickLeadingIcon leadingIcon 클릭 이벤트
 * @param onClickTrailingIcon trailingIcon 클릭 이벤트
 * @param onClickSecondTrailingIcon secondTrailingIcon 클릭 이벤트
 * @param onClickTrailingText trailingText 클릭 이벤트
 *
 * 여러 가지 형태의 TopAppBar 가 존재하지만 하나의 함수로 구현하였고,
 * 필요한 데이터만 파라미터로 넘겨받아 Composable 함수 호출가능
 *
 * 또한 데이터만 넣고 onClick 이벤트를 넣지 않을 경우에는 [checkTopAppBarValidation] 에서 Assert 로 체크
 *
 */

@Composable
fun QuackTopAppBar(
    modifier: Modifier = Modifier,
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
        modifier = modifier
            .applyQuackSize(
                width = QuackWidth.Fill,
                height = QuackHeight.Custom(QuackTopAppBarHeight),
            )
            .background(color = QuackColor.White.value)
            .padding(paddingValues = QuackTopAppBarPadding)
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            horizontalArrangement = Arrangement.spacedBy(QuackTopAppBarLeadingIconSpacedBy),
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
            horizontalArrangement = Arrangement.spacedBy(QuackTopAppBarTrailingIconSpacedBy),
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
            modifier = Modifier.align(alignment = Alignment.Center),
        ) {
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
) {
    assert(trailingText == null || trailingIcon == null)
    if (secondTrailingIcon != null) {
        assert(trailingIcon != null && onClickSecondTrailingIcon != null)
    }
    if (trailingIcon != null) {
        assert(onClickTrailingIcon != null)
    }
    if (trailingText != null) {
        assert(onClickTrailingText != null)
    }
}
