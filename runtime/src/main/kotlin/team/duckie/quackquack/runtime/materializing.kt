/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.runtime

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composer
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import team.duckie.quackquack.util.MustBeTested

// TODO(1): @NoCopy 구현 및 data class 보일러플레이트 제거
// Ref: https://github.com/AhmedMourad0/no-copy
// copy 생성을 막아야 하기에 data class 미사용
/**
 * [Composer.quackMaterializeOf]의 결과를 나타냅니다.
 *
 * @param composeModifier `androidx.compose.ui.Modifier`으로만 구성된 [Modifier]
 * @param quackDataModels [QuackDataModifierModel]으로만 구성된 리스트
 *
 * @see Composer.quackMaterializeOf
 */
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

@VisibleForTesting
internal object QuackMaterializingErrors {
    const val QuackDataModelProducerButNot =
        "The result of the Modifier factory was expected to be a QuackDataModifierModel " +
                "because isQuackDataModelProducer was provided as true, but it is not. " +
                "Please provide isQuackDataModelProducer as false for correct information. "
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
                if (element.quackDataModelProducer) {
                    @Suppress("UNCHECKED_CAST")
                    val factory = element.factory as Modifier.(Composer, Int) -> Modifier
                    val composed = factory.invoke(Modifier, this, 0) as? QuackDataModifierModel ?: error(
                        message = QuackMaterializingErrors.QuackDataModelProducerButNot,
                    )
                    quackDataModels += composed
                    acc
                } else {
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
