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
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.withNotNull
import team.duckie.quackquack.core.material.QuackColor
import team.duckie.quackquack.core.material.QuackTypography
import team.duckie.quackquack.core.util.Empty
import team.duckie.quackquack.core.util.setQuackContent

/**
 * [QuackTextScope]를 테스트합니다.
 *
 * - `Modifier.span`과 `Modifier.highlight`는 같이 사용할 수 없음
 * - `Modifier.span`으로 특정 텍스트에 [SpanStyle]을 적용할 수 있음
 * - `Modifier.highlight`로 특정 텍스트에 [SpanStyle] 및 onClick 이벤트를 설정할 수 있음
 * - `Modifier.highlight`로 특정 텍스트에 [SpanStyle] 및 광역 onClick 이벤트를 설정할 수 있음
 */
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

    // - `Modifier.span`으로 특정 텍스트에 [SpanStyle]을 적용할 수 있음
    @Test
    fun Set_SpanStyle_At_Some_Texts_With_ModifierSpan() {
        composeTestRule.setQuackContent {
            QuackText(
                modifier = Modifier
                    .testTag("text")
                    .span(
                        texts = listOf("@성빈", "코루틴"),
                        style = SpanStyle(
                            color = QuackColor.DuckieOrange.value,
                            fontWeight = FontWeight.SemiBold,
                        ),
                    ),
                text = "@성빈 코루틴 잘 쓰고 싶다.",
                typography = QuackTypography.Body1,
            )
        }

        composeTestRule.onNodeWithTag("text").fetchSemanticsNode()
    }

    // - `Modifier.highlight`로 특정 텍스트에 [SpanStyle] 및 onClick 이벤트를 설정할 수 있음
    @Test
    fun Set_SpanStyle_And_OnClickEvent_With_ModifierHighlight() {

    }

    // - `Modifier.highlight`로 특정 텍스트에 [SpanStyle] 및 광역 onClick 이벤트를 설정할 수 있음
    @Test
    fun Set_SpanStyle_And_GlobalOnClickEvent_With_ModifierHighlight() {

    }
}
