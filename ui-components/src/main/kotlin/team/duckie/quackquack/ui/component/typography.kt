/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [typography.kt] created by Ji Sungbin on 22. 8. 21. 오후 2:07
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

/**
 * [QuackText] 에 [QuackTextStyle.HeadLine1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 이 컴포넌트에서 사용할 [Modifier].
 * 추후 필요한 정보만 받게 수정돼야 합니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 */
@Composable
@NonRestartableComposable
fun QuackHeadLine1(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) = QuackText(
    modifier = modifier,
    text = text,
    style = QuackTextStyle.HeadLine1.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.HeadLine2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 이 컴포넌트에서 사용할 [Modifier].
 * 추후 필요한 정보만 받게 수정돼야 합니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 */
@Composable
@NonRestartableComposable
fun QuackHeadLine2(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) = QuackText(
    modifier = modifier,
    text = text,
    style = QuackTextStyle.HeadLine2.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.Title1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 이 컴포넌트에서 사용할 [Modifier].
 * 추후 필요한 정보만 받게 수정돼야 합니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 */
@Composable
@NonRestartableComposable
fun QuackTitle1(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) = QuackText(
    modifier = modifier,
    text = text,
    style = QuackTextStyle.Title1.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.Title2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 이 컴포넌트에서 사용할 [Modifier].
 * 추후 필요한 정보만 받게 수정돼야 합니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 */
@Composable
@NonRestartableComposable
fun QuackTitle2(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) = QuackText(
    modifier = modifier,
    text = text,
    style = QuackTextStyle.Title2.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.Subtitle] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 이 컴포넌트에서 사용할 [Modifier].
 * 추후 필요한 정보만 받게 수정돼야 합니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 */
@Composable
@NonRestartableComposable
fun QuackSubtitle(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) = QuackText(
    modifier = modifier,
    text = text,
    style = QuackTextStyle.Subtitle.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.Body1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 이 컴포넌트에서 사용할 [Modifier].
 * 추후 필요한 정보만 받게 수정돼야 합니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 */
@Composable
@NonRestartableComposable
fun QuackBody1(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) = QuackText(
    modifier = modifier,
    text = text,
    style = QuackTextStyle.Body1.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.Body2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 이 컴포넌트에서 사용할 [Modifier].
 * 추후 필요한 정보만 받게 수정돼야 합니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 */
@Composable
@NonRestartableComposable
fun QuackBody2(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) = QuackText(
    modifier = modifier,
    text = text,
    style = QuackTextStyle.Body2.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.Body3] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 이 컴포넌트에서 사용할 [Modifier].
 * 추후 필요한 정보만 받게 수정돼야 합니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 */
@Composable
@NonRestartableComposable
fun QuackBody3(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) = QuackText(
    modifier = modifier,
    text = text,
    style = QuackTextStyle.Body3.change(
        color = color,
    ),
)
