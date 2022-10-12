/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

// [Note] @NonRestartableComposable 안한 이유: 인자로 받은 Text 는 동적으로 바뀔 수 있음

/**
 * [QuackText] 에 [QuackTextStyle.HeadLine1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
public fun QuackHeadLine1(
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.HeadLine1.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.HeadLine2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackHeadLine2(
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.HeadLine2.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Title1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackTitle1(
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Title1.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Title2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackTitle2(
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Title2.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Subtitle] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackSubtitle(
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Subtitle.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Subtitle2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackSubtitle2(
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Subtitle2.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Body1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackBody1(
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Body1.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Body2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackBody2(
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
    singleLine: Boolean = false,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Body2.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Body3] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackBody3(
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
    singleLine: Boolean = false,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Body3.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
)

/**
 * [QuackHeadLine2] 에 원하는 부분에 원하는 색깔로 강조하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param highlightTextList 강조할 텍스트 리스트
 * @param highlightColor 강조할 Text 의 색깔
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param singleLine 텍스트를 한 줄로 사용할지 여부
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
public fun QuackHighlightHeadLine2(
    text: String,
    highlightTextList: PersistentList<String>,
    highlightColor: QuackColor = QuackColor.DuckieOrange,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    singleLine: Boolean = false,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    QuackText(
        modifier = Modifier.quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = buildAnnotatedString {
            append(text)
            highlightTextList.forEach { highlightText ->
                val highlightStartIndex = text.indexOf(
                    string = highlightText
                )
                addStyle(
                    style = SpanStyle(
                        color = highlightColor.composeColor,
                        textDecoration = TextDecoration.Underline,
                    ),
                    start = highlightStartIndex,
                    end = highlightStartIndex + highlightText.length,
                )
            }
        },
        style = QuackTextStyle.HeadLine2.change(
            color = color,
            textAlign = align,
        ),
        singleLine = singleLine,
    )
}


/**
 * [QuackHighlightBody3] 에 원하는 부분에 원하는 색깔로 강조하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param highlightTextList 강조할 텍스트 리스트들
 * @param highlightColor 강조할 Text 의 색깔
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 * @param singleLine 텍스트를 한 줄로 사용할지 여부
 */
@Composable
@NonRestartableComposable
public fun QuackHighlightBody3(
    text: String,
    highlightTextList: PersistentList<String>,
    highlightColor: QuackColor = QuackColor.DuckieOrange,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
    singleLine: Boolean = false,
) {

    QuackText(
        modifier = Modifier.quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = buildAnnotatedString {
            append(text)
            highlightTextList.forEach { highlightText ->
                val highlightStartIndex = text.indexOf(
                    string = highlightText
                )
                addStyle(
                    style = SpanStyle(
                        color = highlightColor.composeColor,
                        textDecoration = TextDecoration.Underline,
                    ),
                    start = highlightStartIndex,
                    end = highlightStartIndex + highlightText.length,
                )
            }
        },
        style = QuackTextStyle.Body3.change(
            color = color,
            textAlign = align,
        ),
        singleLine = singleLine,
    )
}