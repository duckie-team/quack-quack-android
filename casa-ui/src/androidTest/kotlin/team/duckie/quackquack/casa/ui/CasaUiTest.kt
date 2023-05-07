/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.casa.ui

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.casa.ui.theme.CasaTheme

class CasaUiTest {
  @get:Rule
  val rule = createComposeRule()

  // 검색
  @Test
  fun searchable() {
    rule.setCasaScreen()

    val searchField = rule.onNodeWithTag("searchField")
    val searchIcon = rule.onNodeWithContentDescription("Search")
    val clearIcon = rule.onNodeWithContentDescription("Clear")
    val casaComponents = rule.onNodeWithTag("casaComponents")

    searchIcon.performClick()
    searchField.assertIsDisplayed()
    // TODO: assertIsDisplayed() vs. assertExists()
    // TODO: assert keyboard is open (how?)

    searchField.performTextInput("kdoc")
    casaComponents.assertSingleTextIsDisplayedOnChildren(
      "QuackText KDOC",
      "QuackButton KDOC",
      "QuackTag KDOC",
    )

    searchField.performTextReplacement("aWeSoMe")
    casaComponents.assertSingleTextIsDisplayedOnChildren(
      "AwEsOmEQuackText",
      "AwesomeQuackButton",
    )
    casaComponents.assertTextIsNotDisplayedOnChildren("QuackTag")

    searchField.performTextReplacement("button")
    casaComponents.assertSingleTextIsDisplayedOnChildren("QuackButton KDOC")
    casaComponents.assertTextIsNotDisplayedOnChildren("QuackText KDOC", "QuackTag KDOC")

    clearIcon.performClick()
    searchField.assertTextEquals("")
    casaComponents.assertSingleTextIsDisplayedOnChildren(
      "AwEsOmEQuackText",
      "AwesomeQuackButton",
      "QuackTag",
    )
  }

  // 도메인 네비게이션
  @Test
  fun navigation() {
    rule.setCasaScreen()

    val navigator = rule.onNodeWithTag("domainNavigator")
    val casaComponents = rule.onNodeWithTag("casaComponents")

    navigator.onChildren().filterToOne(hasTextExactly("text")).performClick()
    casaComponents.assertSingleTextIsDisplayedOnChildren("QuackText KDOC")
    casaComponents.assertTextIsNotDisplayedOnChildren(
      "QuackButton KDOC",
      "QuackTag KDOC",
    )

    navigator.onChildren().filterToOne(hasTextExactly("button")).performClick()
    casaComponents.assertSingleTextIsDisplayedOnChildren(
      "QuackText KDOC",
      "QuackButton KDOC",
    )
    casaComponents.assertTextIsNotDisplayedOnChildren("QuackTag KDOC")

    navigator.onChildren().run {
      filterToOne(hasTextExactly("text")).performClick() // disable
      filterToOne(hasTextExactly("button")).performClick() // disable
      filterToOne(hasTextExactly("tag")).performClick() // enable
    }
    casaComponents.assertSingleTextIsDisplayedOnChildren("QuackTag KDOC")
    casaComponents.assertTextIsNotDisplayedOnChildren(
      "QuackText KDOC",
      "QuackButton KDOC",
    )

    navigator.onChildren().filterToOne(hasTextExactly("tag")).performClick() // disable
    casaComponents.assertSingleTextIsDisplayedOnChildren(
      "QuackText KDOC",
      "QuackButton KDOC",
      "QuackTag KDOC",
    )
  }
}

private fun ComposeContentTestRule.setCasaScreen() {
  setContent {
    CasaTheme {
      CasaScreen(models = casaModels)
    }
  }
}

private fun SemanticsNodeInteraction.assertSingleTextIsDisplayedOnChildren(vararg text: String) {
  if (text.isEmpty()) return

  val children = onChildren()
  text.forEach { currentText ->
    children.filterToOne(hasText(currentText)).assertTextContains(currentText)
  }
}

private fun SemanticsNodeInteraction.assertTextIsNotDisplayedOnChildren(vararg text: String) {
  if (text.isEmpty()) return

  val matcher = text.drop(1).fold(!hasText(text.first())) { acc, element ->
    acc and !hasText(element)
  }
  onChildren().assertAll(matcher)
}
