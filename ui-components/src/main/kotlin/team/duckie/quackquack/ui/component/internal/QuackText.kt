/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component.internal

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import team.duckie.quackquack.ui.animation.AnimatedContentTransform
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.textstyle.animatedQuackTextStyleAsState

// public val quackTexts: SnapshotStateList<QuackWrting> = mutableStateListOf()

/**
 * 주어진 조건에 따라 텍스트를 표시합니다. 꽥꽥에서 텍스트를 표시하는데
 * 사용되는 최하위 컴포넌트 입니다.
 *
 * **[QuackTextStyle] 의 모든 요소들에 자동으로 애니메이션이 적용됩니다.**
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param text 표시할 텍스트 내용
 * @param style 텍스트를 그릴 [TextStyle] 의 QuackQuack 버전
 * @param singleLine 텍스트를 한 줄로 표시할지 여부
 */
@Composable
internal fun QuackText(
    modifier: Modifier = Modifier,
    text: String,
    style: QuackTextStyle,
    singleLine: Boolean = false,
) {
    val styleAnimationState = animatedQuackTextStyleAsState(
        targetValue = style,
    )

    AnimatedContentTransform(
        modifier = modifier,
        /*.lifecycle(
                    key = text,
                    onAppear = { offset ->
                        quackTexts.add(
                            QuackWrting(
                                text = text,
                                offset = offset,
                            )
                        )
                        println("A! $offset")
                    },
                    onDisappear = {
                        quackTexts.remove(
                            quackTexts.first { quackText ->
                                quackText.text == text
                            }
                        )
                        println("B! $quackTexts")
                    },
                )*/
        targetState = text,
    ) { animatedText ->
        BasicText(
            text = animatedText,
            style = styleAnimationState.asComposeStyle(),
            maxLines = when (singleLine) {
                true -> 1
                else -> Int.MAX_VALUE
            },
        )
    }
}

/**
 * 주어진 조건에 따라 텍스트를 표시합니다. 꽥꽥에서 텍스트를 표시하는데
 * 사용되는 최하위 컴포넌트 입니다.
 *
 * **애니메이션 가능한 모든 요소들에는 자동으로 애니메이션이 적용됩니다.**
 * 현재 애니메이션이 적용되는 요소들은 다음과 같습니다.
 *
 * [text], [style]
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param text 표시할 텍스트 내용
 * @param style 텍스트를 그릴 [TextStyle] 의 QuackQuack 버전
 * @param singleLine 텍스트를 한 줄로 표시할지 여부
 */
@Composable
internal fun QuackText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    style: QuackTextStyle,
    singleLine: Boolean = false,
) {
    val styleAnimationState = animatedQuackTextStyleAsState(
        targetValue = style,
    )

    AnimatedContentTransform(
        modifier = modifier,
        /*.lifecycle(
                    key = text,
                    onAppear = { offset ->
                        quackTexts.add(
                            QuackWrting(
                                text = text,
                                offset = offset,
                            )
                        )
                        println("A! $offset")
                    },
                    onDisappear = {
                        quackTexts.remove(
                            quackTexts.first { quackText ->
                                quackText.text == text
                            }
                        )
                        println("B! $quackTexts")
                    },
                )*/
        targetState = text,
    ) { animatedText ->
        BasicText(
            text = animatedText,
            style = styleAnimationState.asComposeStyle(),
            maxLines = when (singleLine) {
                true -> 1
                else -> Int.MAX_VALUE
            },
        )
    }
}
