/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */
@file:Suppress(
    "UnstableApiUsage",
    "TestFunctionName",
    "LongMethod",
)

package team.duckie.quackquack.lint.compose

import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.common.lint.test.LintTestRule
import team.duckie.quackquack.common.lint.test.composableTestFile

/**
 * 테스트 성공 조건
 *
 * 1. 코틀린 함수여야 함
 * 2. animate*AsState 함수를 대상으로 경고해야 함 [AnimateStateOfList]
 * 3. argument animationSpec 에 quackAnimationSpec 을 사용해야 함
 * 4. default argument 를 사용하지 않아야 함
 */
class SpecifyAnimationSpecTest {

    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `NOT Using quackAnimationSpec in animate*AsState function`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                                val defaultFailed = animateDpAsState(
                                    targetValue = 10.dp,
                                )

                                // float
                                val floatFailed = animateFloatAsState(
                                    targetValue = 0f,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                    ),
                                )

                                val floatSuccess = animateFloatAsState(
                                    targetValue = 0f,
                                    animationSpec = quackAnimationSpec(),
                                )


                                // color
                                val colorFailed = animateColorAsState(
                                    targetValue = Color.Black,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                    ),
                                )

                                val colorSuccess = animateColorAsState(
                                    targetValue = Color.Black,
                                    animationSpec = quackAnimationSpec(),
                                )

                                // dp
                                val borderFailed = animateDpAsState(
                                    targetValue = 0.dp,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                    ),
                                )

                                val borderSuccess = animateDpAsState(
                                    targetValue = 0.dp,
                                    animationSpec = quackAnimationSpec(),
                                )

                                // size
                                val sizeFailed = animateSizeAsState(
                                    targetValue = Size.Unspecified,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                    ),
                                )

                                val sizeSuccess = animateSizeAsState(
                                    targetValue = Size.Unspecified,
                                    animationSpec = quackAnimationSpec(),
                                )

                                // offset
                                val offsetFailed = animateOffsetAsState(
                                    targetValue = Offset.Unspecified,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                    ),
                                )

                                val offsetSuccess = animateOffsetAsState(
                                    targetValue = Offset.Unspecified,
                                    animationSpec = quackAnimationSpec(),
                                )

                                // rect
                                val rectFailed = animateRectAsState(
                                    targetValue = Rect.Zero,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                    ),
                                )

                                val rectSuccess = animateRectAsState(
                                    targetValue = Rect.Zero,
                                    animationSpec = quackAnimationSpec(),
                                )

                                // int
                                val intFailed = animateIntAsState(
                                    targetValue = 0,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                    ),
                                )

                                val intSuccess = animateIntAsState(
                                    targetValue = 0,
                                    animationSpec = quackAnimationSpec(),
                                )

                                // intOffset
                                val intOffsetFailed = animateIntOffsetAsState(
                                    targetValue = IntOffset.Zero,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                    ),
                                )

                                val intOffsetSuccess = animateIntOffsetAsState(
                                    targetValue = IntOffset.Zero,
                                    animationSpec = quackAnimationSpec(),
                                )

                                // intSize
                                val intSizeFailed = animateSizeAsState(
                                    targetValue = Size.Unspecified,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                    ),
                                )

                                val intSizeSuccess = animateSizeAsState(
                                    targetValue = Size.Unspecified,
                                    animationSpec = quackAnimationSpec(),
                                )
                        """.trimIndent()
                    ),
                ),
                issues = listOf(
                    SpecifyAnimationSpecIssue,
                ),
                expectedCount = 10,
            )
    }
}
