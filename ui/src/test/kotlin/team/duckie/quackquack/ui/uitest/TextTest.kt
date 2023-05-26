/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.uitest

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.matchers.shouldBe
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.QuackTextErrors
import team.duckie.quackquack.ui.commonutil.setQuackContent
import team.duckie.quackquack.ui.highlight
import team.duckie.quackquack.ui.span
import team.duckie.quackquack.ui.uitest.util.markTest
import team.duckie.quackquack.ui.uitest.util.onTest
import team.duckie.quackquack.util.Empty

@RunWith(AndroidJUnit4::class)
class TextTest {
  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  // `Modifier.span`과 `Modifier.highlight`는 같이 사용할 수 없음
  @Test
  fun cannot_use_ModifierSpan_and_ModifierHeight_same_time() {
    shouldThrowWithMessage<IllegalStateException>(QuackTextErrors.CannotUseSpanAndHighlightAtSameTime) {
      compose.setQuackContent {
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
  }

  // `Modifier.highlight`로 특정 텍스트에 `SpanStyle` 및 글로벌 onClick 이벤트를 설정할 수 있음
  @Ignore("click 시멘틱 제공")
  @Test
  fun set_SpanStyle_and_global_OnClickEvent_with_ModifierHighlight() {
    var clicked = 0
    val text = "text"

    val span = SpanStyle(
      color = QuackColor.DuckieOrange.value,
      fontWeight = FontWeight.SemiBold,
    )
    val typography = QuackTypography.Body1

    compose.setQuackContent {
      QuackText(
        modifier = Modifier
          .markTest()
          .highlight(
            texts = listOf(text),
            globalOnClick = { clicked++ },
            span = span,
          ),
        text = text,
        typography = typography,
      )
    }

    compose.onTest().performClick()
    compose.runOnIdle { clicked shouldBe 1 }
  }

  // `Modifier.highlight`로 특정 텍스트에 `SpanStyle` 및 onClick 이벤트를 설정할 수 있음
  // Compose Testing API의 한계로 ClickableText의 클릭된 Offset까지는 테스트가 불가능함 (아마)
  // https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/foundation/foundation/src/androidAndroidTest/kotlin/androidx/compose/foundation/text/ClickableTextTest.kt
  @Ignore("click 시멘틱 제공")
  @Test
  fun set_SpanStyle_and_OnClickEvent_with_ModifierHighlight() {
    var clicked = 0
    val text = "text"

    val span = SpanStyle(
      color = QuackColor.DuckieOrange.value,
      fontWeight = FontWeight.SemiBold,
    )
    val typography = QuackTypography.Body1

    compose.setQuackContent {
      Column {
        QuackText(
          modifier = Modifier
            .markTest()
            .highlight(
              highlights = listOf(text to { clicked++ }),
              span = span,
            ),
          text = text,
          typography = typography,
        )
      }
    }

    compose.onTest().performClick()
    compose.runOnIdle { clicked shouldBe 1 }
  }
}
