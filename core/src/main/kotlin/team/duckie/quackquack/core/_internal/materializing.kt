/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.core._internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import team.duckie.quackquack.core.util.MustBeTested

@JvmInline
private value class QuackMaterializableComposedModifier(
    val factory: @Composable Modifier.() -> Modifier,
) : Modifier.Element

/**
 * [quackMaterializerOf]에서 `ComposedModifier`를 처리하기 위한 클래스입니다.
 *
 * `composed`의 결과가 [QuackDataModifierModel]일 경우 `acc`를 유지하며 folding하기 위해
 * `ComposedModifier`의 별도 대응이 필요합니다.
 */
@Stable
public fun Modifier.quackComposed(factory: @Composable Modifier.() -> Modifier): Modifier {
    return then(QuackMaterializableComposedModifier(factory))
}

/**
 * 컴포즈의 [Modifier]와 꽥꽥 컴포넌트의 데이터를 나타내는 [QuackDataModifierModel]를 구분하여 반환합니다.
 *
 * @return 컴포즈 자체의 [Modifier]와 [QuackDataModifierModel] 리스트의 [Pair]
 */
@MustBeTested
public fun Composer.quackMaterializerOf(modifier: Modifier): Pair<Modifier, List<QuackDataModifierModel>> {
    val needsOpenGroup = modifier.any { it is QuackMaterializableComposedModifier }

    // Random number for fake group key. Chosen by fair die roll.
    // `Integer.toHexString(Random.nextInt())`
    if (needsOpenGroup) startReplaceableGroup(0x7cf25b00)

    val quackDataModels = mutableListOf<QuackDataModifierModel>()
    val composeModifier = modifier.foldIn<Modifier>(Modifier) { acc, element ->
        when (element) {
            is QuackDataModifierModel -> {
                quackDataModels += element
                acc
            }
            is QuackMaterializableComposedModifier -> {
                @Suppress("UNCHECKED_CAST")
                val factory = element.factory as Modifier.(Composer, Int) -> Modifier
                val composed = factory(Modifier, this, 0)
                if (composed is QuackDataModifierModel) {
                    quackDataModels += composed
                    acc
                } else {
                    // FIXME(sungbin): stdlib-materializerOf으로 들어가면 re-invoke됨 (동일 프레임에서)
                    acc.then(element)
                }
            }
            else -> {
                acc.then(element)
            }
        }
    }

    if (needsOpenGroup) endReplaceableGroup()

    return composeModifier to quackDataModels
}
