/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.core.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.withNotNull
import team.duckie.quackquack.core.material.QuackColor
import team.duckie.quackquack.core.material.QuackTypography
import team.duckie.quackquack.core.util.Empty
import team.duckie.quackquack.core.util.immediateXMoveTo
import team.duckie.quackquack.core.util.onQuackNodeWithTag
import team.duckie.quackquack.core.util.setQuackContent

/**
 * [QuackTextScope]의 로직을 테스트합니다.
 *
 * - `Modifier.span`과 `Modifier.highlight`는 같이 사용할 수 없음
 * - `Modifier.highlight`로 특정 텍스트에 [SpanStyle] 및 onClick 이벤트를 설정할 수 있음
 * - `Modifier.highlight`로 특정 텍스트에 [SpanStyle] 및 광역 onClick 이벤트를 설정할 수 있음
 */
@RunWith(AndroidJUnit4::class)
class TextTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    // - `Modifier.span`과 `Modifier.highlight`는 같이 사용할 수 없음
    @Test
    fun Cannot_Use_ModifierSpan_And_ModifierHeight_Same_Time() {
        expectThrows<IllegalStateException> {
            composeTestRule.setQuackContent {
                QuackText(
                    modifier = Modifier
                        .span(
                            texts = emptyList(),
                            style = SpanStyle(
                                color = QuackColor.DuckieOrange.value,
                                fontWeight = FontWeight.SemiBold,
                            ),
                        )
                        .highlight(highlights = emptyList()),
                    text = String.Empty,
                    typography = QuackTypography.Body1,
                )
            }
        }
            .get { message }
            .withNotNull {
                isEqualTo(QuackTextErrors.CannotUseSpanAndHighlightAtSameTime)
            }
    }

    // - `Modifier.highlight`로 특정 텍스트에 [SpanStyle] 및 onClick 이벤트를 설정할 수 있음
    // onClick 이벤트 작동을 테스트함
    @Test
    fun Set_SpanStyle_And_OnClickEvent_With_ModifierHighlight() {
        val oneOnClick: (String) -> Unit = mock()
        val twoOnClick: (String) -> Unit = mock()
        val threeOnClick: (String) -> Unit = mock()

        composeTestRule.setQuackContent {
            QuackText(
                modifier = Modifier
                    .testTag("text")
                    .highlight(
                        highlights = listOf(
                            "one" to oneOnClick, // 1~3
                            "two" to twoOnClick, // 5~7
                            "three" to threeOnClick, // 9~13
                        ),
                    ),
                text = "one two three four five",
                typography = QuackTypography.Body1,
            )
        }

        composeTestRule.onQuackNodeWithTag("text").performTouchInput {
            immediateXMoveTo(2f) // one
            immediateXMoveTo(6f) // two
            immediateXMoveTo(11f) // three
        }

        composeTestRule.runOnIdle {
            verify(oneOnClick, times(1)).invoke(any())
            verify(twoOnClick, times(1)).invoke(any())
            verify(threeOnClick, times(1)).invoke(any())
        }
    }

    // - `Modifier.highlight`로 특정 텍스트에 [SpanStyle] 및 광역 onClick 이벤트를 설정할 수 있음
    @Test
    fun Set_SpanStyle_And_GlobalOnClickEvent_With_ModifierHighlight() {
        val globalOnClick: (String) -> Unit = mock()

        composeTestRule.setQuackContent {
            QuackText(
                modifier = Modifier
                    .testTag("text")
                    .highlight(
                        texts = listOf("one", "two", "three"),
                        globalOnClick = globalOnClick,
                    ),
                text = "one two three four five",
                typography = QuackTypography.Body1,
            )
        }

        composeTestRule.onQuackNodeWithTag("text").performClick()

        composeTestRule.runOnIdle {
            verify(globalOnClick, times(1)).invoke(any())
        }
    }
}
