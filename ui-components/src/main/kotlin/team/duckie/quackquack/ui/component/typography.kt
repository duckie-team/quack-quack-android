/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

/**
 * [QuackText] 에 [QuackTextStyle.HeadLine1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
fun QuackHeadLine1(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.HeadLine1.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.HeadLine2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식

 */
@Composable
@NonRestartableComposable
fun QuackHeadLine2(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,

) = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.HeadLine2.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.Title1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
fun QuackTitle1(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Title1.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.Title2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
fun QuackTitle2(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Title2.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.Subtitle] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
fun QuackSubtitle(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Subtitle.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.Body1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
fun QuackBody1(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Body1.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.Body2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
fun QuackBody2(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Body2.change(
        color = color,
    ),
)

/**
 * [QuackText] 에 [QuackTextStyle.Body3] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
fun QuackBody3(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Body3.change(
        color = color,
    ),

)

/**
 * [QuackHeadLine2] 에 원하는 부분에 원하는 색깔로 강조하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param highlightText 강조할 텍스트
 * @param highlightColor 강조할 Text 의 색깔
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
fun QuackHighlightHeadLine2(
    text: String,
    highlightText: String,
    highlightColor: QuackColor = QuackColor.DuckieOrange,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val highlightStartIndex = text.indexOf(
        string = highlightText
    )
    QuackText(
        modifier = Modifier.quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = buildAnnotatedString {
            append(text)
            addStyle(
                style = SpanStyle(
                    color = highlightColor.composeColor,
                    textDecoration = TextDecoration.Underline,
                ),
                start = highlightStartIndex,
                end = highlightStartIndex + highlightText.length,
            )
        },
        style = QuackTextStyle.HeadLine2.change(
            color = color,
        ),
    )
}
