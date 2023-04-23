/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.runtime

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import team.duckie.quackquack.util.MustBeTested

// TODO(1): @NoCopy 구현 및 data class 보일러플레이트 제거
// Ref: https://github.com/AhmedMourad0/no-copy
// copy 생성을 막아야 하기에 data class 미사용
@Immutable
public class QuackMaterializeResult internal constructor(
    public val composeModifier: Modifier,
    public val quackDataModels: List<QuackDataModifierModel>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is QuackMaterializeResult) return false

        if (composeModifier != other.composeModifier) return false
        if (quackDataModels != other.quackDataModels) return false

        return true
    }

    override fun hashCode(): Int {
        var result = composeModifier.hashCode()
        result = 31 * result + quackDataModels.hashCode()
        return result
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    public operator fun component1(): Modifier = composeModifier

    public operator fun component2(): List<QuackDataModifierModel> = quackDataModels
}

@JvmInline
@Immutable
private value class QuackMaterializableComposedModifier(
    val factory: @Composable Modifier.() -> Modifier,
) : Modifier.Element

/**
 * [quackMaterializeOf]에서 `ComposedModifier`를 처리하기 위한 클래스입니다.
 *
 * `composed`의 결과가 [QuackDataModifierModel]일 경우 `acc`를 유지하며 folding하기 위해
 * `ComposedModifier`의 별도 대응이 필요합니다.
 */
@Stable
public fun Modifier.quackComposed(factory: @Composable Modifier.() -> Modifier): Modifier {
    return then(QuackMaterializableComposedModifier(factory))
}

/**
 * 컴포즈의 [Modifier]와 꽥꽥 컴포넌트의 데이터를 나타내는 [QuackDataModifierModel]를
 * 구분하여 반환합니다.
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
    val needsNewGroup = modifier.any { it is QuackMaterializableComposedModifier }

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
            is QuackMaterializableComposedModifier -> {
                @Suppress("UNCHECKED_CAST")
                val factory = element.factory as Modifier.(Composer, Int) -> Modifier
                val composed = factory(Modifier, this, 0)
                if (composed is QuackDataModifierModel) {
                    quackDataModels += composed
                    acc
                } else {
                    // FIXME: 최초 컴포지션시에 stdlib-materializerOf으로 들어가면 re-invoke됨 (동일 프레임에서)
                    @Suppress("UnnecessaryComposedModifier")
                    acc.composed(factory = element.factory)
                }
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
