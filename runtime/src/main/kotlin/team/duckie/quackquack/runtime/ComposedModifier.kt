/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.NoInspectorInfo

// TODO: 전체 문서화

internal open class QuackComposedModifier(
    inspectorInfo: InspectorInfo.() -> Unit,
    val quackDataModelProducer: Boolean = true,
    val factory: @Composable Modifier.() -> Modifier,
) : Modifier.Element, InspectorValueInfo(inspectorInfo)

@Stable
private class KeyedQuackComposedModifier1(
    quackDataModelProducer: Boolean = true,
    val fqName: String,
    val key1: Any?,
    inspectorInfo: InspectorInfo.() -> Unit,
    factory: @Composable Modifier.() -> Modifier,
) : QuackComposedModifier(
    inspectorInfo = inspectorInfo,
    quackDataModelProducer = quackDataModelProducer,
    factory = factory,
) {
    override fun equals(other: Any?): Boolean {
        return other is KeyedQuackComposedModifier1 &&
                fqName == other.fqName &&
                key1 == other.key1
    }

    override fun hashCode(): Int {
        return 31 * fqName.hashCode() + key1.hashCode()
    }
}

@Stable
private class KeyedQuackComposedModifier2(
    quackDataModelProducer: Boolean = true,
    val fqName: String,
    val key1: Any?,
    val key2: Any?,
    inspectorInfo: InspectorInfo.() -> Unit,
    factory: @Composable Modifier.() -> Modifier,
) : QuackComposedModifier(
    inspectorInfo = inspectorInfo,
    quackDataModelProducer = quackDataModelProducer,
    factory = factory,
) {
    override fun equals(other: Any?): Boolean {
        return other is KeyedQuackComposedModifier2 &&
                fqName == other.fqName &&
                key1 == other.key1 &&
                key2 == other.key2
    }

    override fun hashCode(): Int {
        var result = fqName.hashCode()
        result = 31 * result + key1.hashCode()
        result = 31 * result + key2.hashCode()
        return result
    }
}

@Stable
private class KeyedQuackComposedModifier3(
    quackDataModelProducer: Boolean = true,
    val fqName: String,
    val key1: Any?,
    val key2: Any?,
    val key3: Any?,
    inspectorInfo: InspectorInfo.() -> Unit,
    factory: @Composable Modifier.() -> Modifier,
) : QuackComposedModifier(
    inspectorInfo = inspectorInfo,
    quackDataModelProducer = quackDataModelProducer,
    factory = factory,
) {
    override fun equals(other: Any?): Boolean {
        return other is KeyedQuackComposedModifier3 &&
                fqName == other.fqName &&
                key1 == other.key1 &&
                key2 == other.key2 &&
                key3 == other.key3
    }

    override fun hashCode(): Int {
        var result = fqName.hashCode()
        result = 31 * result + key1.hashCode()
        result = 31 * result + key2.hashCode()
        result = 31 * result + key3.hashCode()
        return result
    }
}

@Stable
private class KeyedQuackComposedModifierN(
    quackDataModelProducer: Boolean = true,
    val fqName: String,
    val keys: Array<out Any?>,
    inspectorInfo: InspectorInfo.() -> Unit,
    factory: @Composable Modifier.() -> Modifier,
) : QuackComposedModifier(
    inspectorInfo = inspectorInfo,
    quackDataModelProducer = quackDataModelProducer,
    factory = factory,
) {
    override fun equals(other: Any?): Boolean {
        return other is KeyedQuackComposedModifierN &&
                fqName == other.fqName &&
                keys.contentEquals(other.keys)
    }

    override fun hashCode(): Int {
        return 31 * fqName.hashCode() + keys.contentHashCode()
    }
}

/**
 * [quackMaterializeOf]에서 `ComposedModifier`를 처리하기 위한 클래스입니다.
 *
 * `composed`의 결과가 [QuackDataModifierModel]일 경우 `acc`를 유지하며
 * folding하기 위해 `ComposedModifier`의 별도 대응이 필요합니다.
 */
@Stable
public fun Modifier.quackComposed(
    quackDataModelProducer: Boolean = true,
    inspectorInfo: InspectorInfo.() -> Unit = NoInspectorInfo,
    factory: @Composable Modifier.() -> Modifier,
): Modifier {
    return then(
        QuackComposedModifier(
            quackDataModelProducer = quackDataModelProducer,
            inspectorInfo = inspectorInfo,
            factory = factory,
        ),
    )
}

@Stable
public fun Modifier.quackComposed(
    quackDataModelProducer: Boolean = true,
    fullyQualifiedName: String,
    key1: Any?,
    inspectorInfo: InspectorInfo.() -> Unit = NoInspectorInfo,
    factory: @Composable Modifier.() -> Modifier,
): Modifier {
    return then(
        KeyedQuackComposedModifier1(
            quackDataModelProducer = quackDataModelProducer,
            fqName = fullyQualifiedName,
            key1 = key1,
            inspectorInfo = inspectorInfo,
            factory = factory,
        ),
    )
}

@Stable
public fun Modifier.quackComposed(
    quackDataModelProducer: Boolean = true,
    fullyQualifiedName: String,
    key1: Any?,
    key2: Any?,
    inspectorInfo: InspectorInfo.() -> Unit = NoInspectorInfo,
    factory: @Composable Modifier.() -> Modifier,
): Modifier {
    return then(
        KeyedQuackComposedModifier2(
            quackDataModelProducer = quackDataModelProducer,
            fqName = fullyQualifiedName,
            key1 = key1,
            key2 = key2,
            inspectorInfo = inspectorInfo,
            factory = factory,
        ),
    )
}

@Stable
public fun Modifier.quackComposed(
    quackDataModelProducer: Boolean = true,
    fullyQualifiedName: String,
    key1: Any?,
    key2: Any?,
    key3: Any?,
    inspectorInfo: InspectorInfo.() -> Unit = NoInspectorInfo,
    factory: @Composable Modifier.() -> Modifier,
): Modifier {
    return then(
        KeyedQuackComposedModifier3(
            quackDataModelProducer = quackDataModelProducer,
            fqName = fullyQualifiedName,
            key1 = key1,
            key2 = key2,
            key3 = key3,
            inspectorInfo = inspectorInfo,
            factory = factory,
        ),
    )
}

@Stable
public fun Modifier.quackComposed(
    quackDataModelProducer: Boolean = true,
    fullyQualifiedName: String,
    vararg keys: Any?,
    inspectorInfo: InspectorInfo.() -> Unit = NoInspectorInfo,
    factory: @Composable Modifier.() -> Modifier,
): Modifier {
    return then(
        KeyedQuackComposedModifierN(
            quackDataModelProducer = quackDataModelProducer,
            fqName = fullyQualifiedName,
            keys = keys,
            inspectorInfo = inspectorInfo,
            factory = factory,
        ),
    )
}
