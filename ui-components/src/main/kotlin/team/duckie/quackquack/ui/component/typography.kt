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
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
public fun QuackHeadLine1(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = true,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.HeadLine1.change(
        color = color,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.HeadLine2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackHeadLine2(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = true,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.HeadLine2.change(
        color = color,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Title1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackTitle1(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = true,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Title1.change(
        color = color,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Title2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackTitle2(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = true,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Title2.change(
        color = color,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Subtitle] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackSubtitle(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = true,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Subtitle.change(
        color = color,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Body1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackBody1(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = true,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Body1.change(
        color = color,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Body2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackBody2(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
    singleLine: Boolean = true,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Body2.change(
        color = color,
    ),
    singleLine = singleLine,
)

/**
 * [QuackText] 에 [QuackTextStyle.Body3] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄만 사용할지 여부
 * @param onClick 텍스트이 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackBody3(
    text: String,
    color: QuackColor = QuackColor.Black,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
    singleLine: Boolean = true,
): Unit = QuackText(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    text = text,
    style = QuackTextStyle.Body3.change(
        color = color,
    ),
    singleLine = singleLine,
)
