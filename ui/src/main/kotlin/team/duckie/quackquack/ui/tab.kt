/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("ModifierParameter")

package team.duckie.quackquack.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collection.MutableVector
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor

@Immutable
public class QuackTabColors private constructor(
  public val background: QuackColor = QuackColor.White,
  public val underline: QuackColor = QuackColor.Gray4,
  public val indicate: QuackColor = QuackColor.DuckieOrange,
  public val contentColor: QuackColor = QuackColor.Black,
  public val disableContentColor: QuackColor = QuackColor.Gray1,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is QuackTabColors) return false

    if (background != other.background) return false
    if (underline != other.underline) return false
    if (indicate != other.indicate) return false
    if (contentColor != other.contentColor) return false
    return disableContentColor == other.disableContentColor
  }

  override fun hashCode(): Int {
    var result = background.hashCode()
    result = 31 * result + underline.hashCode()
    result = 31 * result + indicate.hashCode()
    result = 31 * result + contentColor.hashCode()
    result = 31 * result + disableContentColor.hashCode()
    return result
  }

  @Stable
  public fun copy(
    background: QuackColor = this.background,
    underline: QuackColor = this.underline,
    indicate: QuackColor = this.indicate,
    contentColor: QuackColor = this.contentColor,
    disableContentColor: QuackColor = this.disableContentColor,
  ): QuackTabColors =
    QuackTabColors(
      background = background,
      underline = underline,
      indicate = indicate,
      contentColor = contentColor,
      disableContentColor = disableContentColor,
    )

  public companion object {
    @Stable
    public fun defaultTabColors(): QuackTabColors = QuackTabColors()
  }
}

@Immutable
public interface QuackTabScope {
  public fun tab(label: String, onClick: (index: Int) -> Unit)
}

private typealias TabLabelAndOnClickPair = Pair<String, (Int) -> Unit>

@Immutable
private class QuackTabScopeScope(
  private val tabs: MutableVector<TabLabelAndOnClickPair>,
) : QuackTabScope {
  override fun tab(label: String, onClick: (index: Int) -> Unit) {
    tabs += label to onClick
  }
}

private const val TabLayoutLayoutId = "TabLayout"
private const val TabLabelLayoutId = "TabLabel"

private const val TabAnimationMillis = 300
private val tabAnimationSpec = tween<Any>(TabAnimationMillis)

@Composable
public fun QuackTab(
  index: Int,
  colors: QuackTabColors = QuackTabColors.defaultTabColors(),
  modifier: Modifier = Modifier,
  content: QuackTabScope.() -> Unit,
) {
  val tabs = remember<List<TabLabelAndOnClickPair>>(content) {
    MutableVector<TabLabelAndOnClickPair>().also { tabs ->
      QuackTabScopeScope(tabs).content()
    }.asMutableList()
  }

  val density = LocalDensity.current
  val coroutineScope = rememberCoroutineScope()
  val horizontalPadding = with(density) { 16.dp.roundToPx() }

  val indicateStartOffset = remember {
    Animatable(
      initialValue = horizontalPadding,
      typeConverter = Int.VectorConverter,
      label = "QuackTabIndicateStartOffset",
    )
  }
  val indicateEndOffset = remember {
    Animatable(
      initialValue = Int.MIN_VALUE,
      typeConverter = Int.VectorConverter,
      label = "QuackTabIndicateEndOffset",
    )
  }

  Layout(
    modifier = modifier,
    content = {

    },
  ) { measurables, constraints ->

  }
}
