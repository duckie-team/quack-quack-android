/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackTagDefaults.RoundingTag.TextPadding
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.DpSize
import team.duckie.quackquack.ui.util.runIf

/**
 * QuackLazyTag 에 사용할 태그들의 타입을 정의합니다.
 */
public enum class QuackTagType {
    Grayscale,
    Circle,
    Rounding,
}

private object QuackTagDefaults {
    object GrayscaleTag {
        val TagTextPadding = PaddingValues(
            top = 8.dp,
            bottom = 8.dp,
            start = 12.dp,
            end = 8.dp, // 텍스트 오른쪽에 trailing text 가 있음
        )

        // trailing text 는 TagTextPadding 를 기준으로 Vertical Center 에 배치돼야 함
        val TrailingTextPadding = PaddingValues(
            end = 15.dp,
        )

        val TagTypography = QuackTextStyle.Body1
        val TrailingTextTypography = QuackTextStyle.Subtitle2.change(
            color = QuackColor.DuckieOrange,
        )

        val Shape = RoundedCornerShape(
            size = 12.dp,
        )
        val BackgroundColor = QuackColor.Gray4
    }

    object CircleTag {
        /**
         * 조건에 맞게 태그 텍스트의 패딩 값을 계산합니다.
         *
         * @param hasTrailingIcon trailing icon 을 가지고 있는지 여부
         *
         * @return [hasTrailingIcon] 여부에 따른 패딩 값
         */
        fun tagTextPaddingFor(
            hasTrailingIcon: Boolean,
        ) = PaddingValues(
            top = 8.dp,
            bottom = 8.dp,
            start = 12.dp,
            end = if (hasTrailingIcon) 8.dp else 12.dp,
        )

        // trailing icon 은 TagTextPadding 를 기준으로 Vertical Center 에 배치돼야 함
        val TrailingIconPadding = PaddingValues(
            end = 10.dp,
        )

        /**
         * 조건에 맞게 태그 텍스트의 [QuackTextStyle] 을 계산합니다.
         *
         * @param isSelected 현재 선택된 상태로 있는지 여부
         * @param hasTrailingIcon trailing icon 을 가지고 있는지 여부
         *
         * @return 현재 조건에 맞게 사용할 [QuackTextStyle]
         */
        fun typographyFor(
            isSelected: Boolean,
            hasTrailingIcon: Boolean,
        ) = QuackTextStyle.Title2.runIf(
            condition = isSelected,
        ) {
            change(
                color = when (hasTrailingIcon) {
                    true -> QuackColor.White
                    else -> QuackColor.DuckieOrange
                },
            )
        }

        /**
         * 조건에 맞게 태그의 테두리를 계산합니다.
         *
         * @param isSelected 현재 선택된 상태로 있는지 여부
         *
         * @return 현재 조건에 맞게 사용할 [QuackBorder]
         */
        fun borderFor(
            isSelected: Boolean,
        ) = QuackBorder(
            color = when (isSelected) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray3
            },
        )

        /**
         * 조건에 맞게 태그의 배경 색상을 계산합니다.
         *
         * @param isSelected 현재 선택된 상태로 있는지 여부
         * @param hasTrailingIcon trailing icon 을 가지고 있는지 여부
         *
         * @return 현재 조건에 맞게 사용할 [QuackColor]
         */
        fun backgroundColorFor(
            isSelected: Boolean,
            hasTrailingIcon: Boolean,
        ) = when (isSelected && hasTrailingIcon) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.White
        }

        /**
         * 조건에 맞게 trailing icon 의 틴트를 계산합니다.
         *
         * @param isSelected 현재 선택된 상태로 있는지 여부
         *
         * @return 현재 조건에 맞게 사용할 [QuackColor]
         */
        fun trailingIconTintFor(
            isSelected: Boolean,
        ) = when (isSelected) {
            true -> QuackColor.White
            else -> QuackColor.Gray2
        }

        val TrailingIconSize = DpSize(
            all = 16.dp,
        )

        val Shape = RoundedCornerShape(
            size = 18.dp,
        )
    }

    object RoundingTag {
        val TextPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 8.dp,
        )

        /**
         * 조건에 맞게 태그 텍스트의 [QuackTextStyle] 을 계산합니다.
         *
         * @param isSelected 현재 선택된 상태로 있는지 여부
         *
         * @return 현재 조건에 맞게 사용할 [QuackTextStyle]
         */
        fun typographyFor(
            isSelected: Boolean,
        ) = QuackTextStyle.Body1.runIf(
            condition = isSelected,
        ) {
            change(
                color = QuackColor.DuckieOrange,
            )
        }

        /**
         * 조건에 맞게 태그의 테두리를 계산합니다.
         *
         * @param isSelected 현재 선택된 상태로 있는지 여부
         *
         * @return 현재 조건에 맞게 사용할 [QuackBorder]
         */
        fun borderFor(
            isSelected: Boolean,
        ) = QuackBorder(
            color = when (isSelected) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray3
            },
        )

        val BackgroundColor = QuackColor.White

        val Shape = RoundedCornerShape(
            size = 12.dp,
        )
    }

    object LazyTag {
        val VerticalSpacedBy = 8.dp
        val HorizontalSpacedBy = 8.dp
    }
}

@Composable
public fun QuackGrayscaleTag(
    text: String,
    trailingText: String,
    onClick: (() -> Unit)? = null,
): Unit = with(
    receiver = QuackTagDefaults.GrayscaleTag,
) {
    QuackSurface(
        modifier = Modifier.wrapContentSize(),
        backgroundColor = BackgroundColor,
        shape = Shape,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackText(
                modifier = Modifier.padding(
                    paddingValues = TextPadding,
                ),
                text = text,
                style = TagTypography,
                singleLine = true,
            )
        }
    }
}