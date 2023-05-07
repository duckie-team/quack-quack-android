/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.runtime

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composer
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import land.sungbin.kotlin.dataclass.nocopy.NoCopy
import team.duckie.quackquack.util.MustBeTested

/**
 * [Composer.quackMaterializeOf]의 결과를 나타냅니다.
 *
 * 이 클래스의 경우 값을 임의로 변경하면 안되므로 **자동 생성되는 copy 함수는
 * 꽥꽥 컴파일 단계에서 제거**됩니다.
 *
 * @param composeModifier `androidx.compose.ui.Modifier`으로만 구성된 [Modifier]
 * @param quackDataModels [QuackDataModifierModel]으로만 구성된 리스트
 *
 * @see Composer.quackMaterializeOf
 */
@NoCopy
@Immutable
public data class QuackMaterializeResult internal constructor(
  public val composeModifier: Modifier,
  public val quackDataModels: List<QuackDataModifierModel>,
)

@VisibleForTesting
internal object QuackMaterializingErrors {
  const val MustProducesQuackDataModel =
    "The result of the Modifier factory was expected to be a QuackDataModifierModel " +
        "because `Modifier.quackComposed` was used, but it is not. " +
        "If it returns the standard `Modifier.Element`, use `Modifier.composed` instead."
}

/**
 * 컴포즈의 [Modifier]와 꽥꽥 컴포넌트의 데이터를 나타내는 [QuackDataModifierModel]를
 * 구분하여 반환합니다.
 *
 * [QuackComposedModifier.factory]의 반환값이 [QuackDataModifierModel]를 상속한다면
 * [QuackComposedModifier]를 [QuackDataModifierModel]인 것으로 간주합니다.
 *
 * @param modifier 분석할 [Modifier]
 * @param taversingCallback 주어진 [Modifier]를 foldIn으로 순회하며 방문하는
 * element마다 호출할 선택적 콜백
 *
 * @return 컴포즈 자체의 [Modifier]와 [QuackDataModifierModel] 리스트를 담은 클래스
 */
@MustBeTested(passed = true)
public fun Composer.quackMaterializeOf(
  modifier: Modifier,
  taversingCallback: (modifier: Modifier) -> Unit = {},
): QuackMaterializeResult {
  val needsNewGroup = modifier.any { it is QuackComposedModifier }

  // Random number for fake group key. Chosen by fair die roll.
  // `Integer.toHexString(Random.nextInt())`
  if (needsNewGroup) startReplaceableGroup(0x7cf25b00)

  val quackDataModels = mutableListOf<QuackDataModifierModel>()
  val composeModifier = modifier.foldIn<Modifier>(Modifier) { acc, element ->
    taversingCallback(element)
    when (element) {
      is QuackDataModifierModel -> {
        quackDataModels += element
        acc
      }
      is QuackComposedModifier -> {
        @Suppress("UNCHECKED_CAST")
        val factory = element.factory as Modifier.(Composer, Int) -> Modifier
        val composed = factory.invoke(Modifier, this, 0) as? QuackDataModifierModel ?: error(
          message = QuackMaterializingErrors.MustProducesQuackDataModel,
        )
        quackDataModels += composed
        acc
      }
      else -> {
        acc.then(element)
      }
    }
  }

  if (needsNewGroup) endReplaceableGroup()

  return QuackMaterializeResult(
    composeModifier = composeModifier,
    quackDataModels = quackDataModels,
  )
}
