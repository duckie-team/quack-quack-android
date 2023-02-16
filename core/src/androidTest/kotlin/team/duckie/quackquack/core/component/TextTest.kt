/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.core.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.withNotNull
import team.duckie.quackquack.core.material.QuackColor
import team.duckie.quackquack.core.material.QuackTypography
import team.duckie.quackquack.core.util.Empty
import team.duckie.quackquack.core.util.isScreenshotSame
import team.duckie.quackquack.core.util.markGolden
import team.duckie.quackquack.core.util.markTest
import team.duckie.quackquack.core.util.onGolden
import team.duckie.quackquack.core.util.onTest
import team.duckie.quackquack.core.util.setQuackContent

/**
 * [QuackTextScope]의 로직을 테스트합니다.
 *
 * - `Modifier.span`과 `Modifier.highlight`는 같이 사용할 수 없음
 * - `Modifier#span`과 `buildAnnotatedString`은 같은 텍스트 비쥬얼을 그림
 * - `Modifier.highlight`로 특정 텍스트에 [SpanStyle] 및 onClick 이벤트를 설정할 수 있음
 * - `Modifier.highlight`로 특정 텍스트에 [SpanStyle] 및 글로벌 onClick 이벤트를 설정할 수 있음
 */
class TextTest {
    @get:Rule
    val rule = createComposeRule()

    // - `Modifier.span`과 `Modifier.highlight`는 같이 사용할 수 없음
    @Test
    fun cannot_use_ModifierSpan_and_ModifierHeight_same_time() {
        expectThrows<IllegalStateException> {
            rule.setQuackContent {
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

    // `Modifier#span`과 `buildAnnotatedString`은 같은 텍스트 비쥬얼을 그림
    @Test
    fun ModifierSpan_and_buildAnnotatedString_rendered_same_ui() {
        val style = SpanStyle(
            color = QuackColor.DuckieOrange.value,
            fontWeight = FontWeight.SemiBold,
        )

        rule.setQuackContent {
            Column {
                QuackText(
                    modifier = Modifier
                        .markTest()
                        .span(
                            texts = listOf("@성빈", "코루틴"),
                            style = style,
                        ),
                    text = "@성빈 코루틴 잘 쓰고 싶다.",
                    typography = QuackTypography.Body1,
                )
                BasicText(
                    modifier = Modifier.markGolden(),
                    text = buildAnnotatedString {
                        withStyle(style) { append("@성빈") }
                        append(" ")
                        withStyle(style) { append("코루틴") }
                        append(" 잘 쓰고 싶다.")
                    },
                    style = QuackTypography.Body1.asComposeStyle(),
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        expectThat(rule.onTest()).isScreenshotSame(
            name = "Modifier#span",
            golden = rule.onGolden(),
        )
    }

    // - `Modifier.highlight`로 특정 텍스트에 [SpanStyle] 및 onClick 이벤트를 설정할 수 있음
    // Compose Testing API의 한계로 ClickableText의 클릭된 Offset까지는 테스트가 불가능함
    // https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/foundation/foundation/src/androidAndroidTest/kotlin/androidx/compose/foundation/text/ClickableTextTest.kt
    @Test
    fun set_SpanStyle_and_OnClickEvent_with_ModifierHighlight() {
        val onClick: (String) -> Unit = mock()
        val text = "text"

        val span = SpanStyle(
            color = QuackColor.DuckieOrange.value,
            fontWeight = FontWeight.SemiBold,
        )
        val typography = QuackTypography.Body1

        rule.setQuackContent {
            Column {
                QuackText(
                    modifier = Modifier
                        .markTest()
                        .highlight(
                            highlights = listOf(text to onClick),
                            span = span,
                        ),
                    text = text,
                    typography = typography,
                )
                BasicText(
                    modifier = Modifier.markGolden(),
                    text = buildAnnotatedString {
                        withStyle(span) { append(text) }
                    },
                    style = typography.asComposeStyle(),
                )
            }
        }

        expectThat(rule.onTest()).isScreenshotSame(
            name = "Modifier#highlight-normal",
            golden = rule.onGolden(),
        )
        rule.onTest().performClick()

        rule.runOnIdle {
            verify(onClick, times(1)).invoke(anyString())
        }
    }

    // - `Modifier.highlight`로 특정 텍스트에 [SpanStyle] 및 글로벌 onClick 이벤트를 설정할 수 있음
    @Test
    fun set_SpanStyle_and_global_OnClickEvent_with_ModifierHighlight() {
        val onClick: (String) -> Unit = mock()
        val text = "text"

        val span = SpanStyle(
            color = QuackColor.DuckieOrange.value,
            fontWeight = FontWeight.SemiBold,
        )
        val typography = QuackTypography.Body1

        rule.setQuackContent {
            Column {
                QuackText(
                    modifier = Modifier
                        .markTest()
                        .highlight(
                            texts = listOf(text),
                            globalOnClick = onClick,
                            span = span,
                        ),
                    text = text,
                    typography = typography,
                )
                BasicText(
                    modifier = Modifier.markGolden(),
                    text = buildAnnotatedString {
                        withStyle(span) { append(text) }
                    },
                    style = typography.asComposeStyle(),
                )
            }
        }

        expectThat(rule.onTest()).isScreenshotSame(
            name = "Modifier#highlight-global",
            golden = rule.onGolden(),
        )
        rule.onTest().performClick()

        rule.runOnIdle {
            verify(onClick, times(1)).invoke(anyString())
        }
    }
}

