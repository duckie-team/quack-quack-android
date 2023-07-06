/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.uitest

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.collection.MutableVector
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.QuackTab
import team.duckie.quackquack.ui.QuackTabScopeScope
import team.duckie.quackquack.ui.commonutil.setQuackContent
import team.duckie.quackquack.ui.layoutIdWithTabIndexPairTextLayoutResult
import team.duckie.quackquack.ui.tabIndex
import team.duckie.quackquack.ui.textLayoutResult
import team.duckie.quackquack.ui.util.rememberLtrTextMeasurer
import team.duckie.quackquack.util.Empty

@RunWith(AndroidJUnit4::class)
class TabTest {
  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun LayoutIdWithTabIndexPairTextLayoutResultElementTest() {
    compose.setQuackContent {
      val textMeasurer = rememberLtrTextMeasurer()

      val layoutId = "AwesomeLayoutId"
      val tabIndex = 999
      val textLayoutResult = textMeasurer.measure(text = String.Empty)

      Box(
        Modifier
          .layoutIdWithTabIndexPairTextLayoutResult(
            layoutId = layoutId,
            tabIndex = tabIndex,
            textLayoutResult = textLayoutResult,
          )
          .layout { measurable, _ ->
            val consumeLayoutId = measurable.layoutId
            val consumeTabIndex = measurable.tabIndex
            val consumeTextLayoutResult = measurable.textLayoutResult

            consumeLayoutId shouldBe layoutId
            consumeTabIndex shouldBe tabIndex
            consumeTextLayoutResult shouldBe textLayoutResult

            layout(0, 0) {}
          },
      )
    }
  }

  @Test
  fun QuackTabScopeIndexIsSequential() {
    val times = 5
    val labelIndices = IntArray(times)

    with(QuackTabScopeScope(MutableVector(times))) {
      repeat(times) { time ->
        tab(
          label = run {
            labelIndices[time] = candidateIndex
            String.Empty
          },
          onClick = {},
        )
      }
    }

    labelIndices.asList() shouldContainExactly List(times) { it }
  }

  @Test
  fun OnClickEvents() {
    val clicked = mutableListOf<Int>()
    compose.setQuackContent {
      QuackTab(0) {
        repeat(3) {
          tab("") { index ->
            clicked += index
          }
        }
      }
    }
    val tabNodes = compose.onAllNodes(hasClickAction())
    val tabList = tabNodes.fetchSemanticsNodes()

    repeat(tabList.size) { index ->
      tabNodes[index].performClick()
    }

    tabList shouldHaveSize 3
    clicked shouldContainExactly List(3) { it }
  }
}
