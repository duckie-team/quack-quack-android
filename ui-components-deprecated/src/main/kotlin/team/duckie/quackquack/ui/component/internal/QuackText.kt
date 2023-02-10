/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component.internal

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.util.fastForEach
import kotlinx.collections.immutable.ImmutableList
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.component.QuackHighlightTextInfo
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.textstyle.animatedQuackTextStyleAsState

// TODO: overlay-ux-writing
// public val quackTexts: SnapshotStateList<QuackWrting> = mutableStateListOf()

// NOTE: 스타일 가이드에 없는 typography 로 텍스트 표시가 필요할 때가 있어서
//       public 접근제한자로 변경함

/**
 * 주어진 조건에 따라 텍스트를 표시합니다.
 * 꽥꽥에서 텍스트를 표시하는데 사용되는 최하위 컴포넌트 입니다.
 *
 * **애니메이션 가능한 모든 요소들에는 자동으로 애니메이션이 적용됩니다.**
 * 현재 애니메이션이 적용되는 요소들은 다음과 같습니다.
 *
 * [text], [style]
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param text 표시할 텍스트
 * @param style 텍스트를 그릴 [TextStyle] 의 QuackQuack 버전
 * @param singleLine 텍스트를 한 줄로 표시할지 여부
 * @param overflow 최대 표시 가능 범위를 넘었을 때 텍스트를 처리할 정책
 */
@Composable
public fun QuackText(
    modifier: Modifier = Modifier,
    text: Any,
    style: QuackTextStyle,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    val animatedQuackTextStyle = animatedQuackTextStyleAsState(
        targetValue = style,
    )

    QuackAnimatedContent(
        modifier = modifier,
        targetState = text.toString(),
    ) { animatedText ->
        BasicText(
            text = animatedText,
            style = animatedQuackTextStyle.asComposeStyle(),
            maxLines = when (singleLine) {
                true -> 1
                else -> Int.MAX_VALUE
            },
            overflow = overflow,
        )
    }
}

/**
 * 주어진 조건에 따라 텍스트를 표시합니다.
 * 꽥꽥에서 텍스트를 표시하는데 사용되는 최하위 컴포넌트 입니다.
 *
 * **애니메이션 가능한 모든 요소들에는 자동으로 애니메이션이 적용됩니다.**
 * 현재 애니메이션이 적용되는 요소들은 다음과 같습니다.
 *
 * [annotatedText], [style]
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param annotatedText 표시할 텍스트
 * @param style 텍스트를 그릴 [TextStyle] 의 QuackQuack 버전
 * @param singleLine 텍스트를 한 줄로 표시할지 여부
 * @param overflow 최대 표시 가능 범위를 넘었을 때 텍스트를 처리할 정책
 */
@Composable
public fun QuackText(
    modifier: Modifier = Modifier,
    annotatedText: AnnotatedString,
    style: QuackTextStyle,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    val styleAnimationState = animatedQuackTextStyleAsState(
        targetValue = style,
    )

    QuackAnimatedContent(
        modifier = modifier,
        targetState = annotatedText,
    ) { animatedText ->
        BasicText(
            text = animatedText,
            style = styleAnimationState.asComposeStyle(),
            maxLines = when (singleLine) {
                true -> 1
                else -> Int.MAX_VALUE
            },
            overflow = overflow,
        )
    }
}

/**
 * 클릭 가능한 텍스트를 표시합니다.
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param text 표시할 텍스트 내용
 * @param clickEventTextInfo 클릭 이벤트 처리용 텍스트 목록
 * @param defaultOnClick [clickEventTextInfo] 목록 내 모든 range 에 속하지 않을 때 실행할 클릭 이벤트
 * @param style 텍스트를 그릴 [TextStyle] 의 QuackQuack 버전
 * @param singleLine 텍스트를 한 줄로 표시할 지 여부
 * @param overflow 최대 표시 가능 범위를 넘었을 때 텍스트를 처리할 정책
 */
@Composable
public fun QuackClickableText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    clickEventTextInfo: ImmutableList<QuackHighlightTextInfo>,
    defaultOnClick: (() -> Unit)? = null,
    style: QuackTextStyle,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    ClickableText(
        modifier = modifier,
        text = text,
        style = style.asComposeStyle(),
        onClick = { offset ->
            clickEventTextInfo.fastForEach { (value, _, _, onClick) ->
                text.getStringAnnotations(
                    tag = value,
                    start = offset,
                    end = offset,
                ).firstOrNull()?.let {
                    onClick?.invoke()
                    return@ClickableText
                }
            }

            defaultOnClick?.invoke()
        },
        maxLines = when (singleLine) {
            true -> 1
            else -> Int.MAX_VALUE
        },
        overflow = overflow,
    )
}
