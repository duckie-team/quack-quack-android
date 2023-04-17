/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.runtime

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

/**
 * [Modifier]로 제공될 꽥꽥 컴포넌트의 데이터임을 나타냅니다.
 * 다음과 같이 사용될 수 있습니다.
 *
 * ```
 * @JvmInline
 * value class StringData(value: String): QuackDataModifierModel
 *
 * @Stable
 * fun Modifier.appendStringData(data: String): Modifier {
 *     return then(StringData(data))
 * }
 * ```
 *
 * 이렇게 제공된 [Modifier]와 컴포즈의 표준 [Modifier]를 구분하려면
 * [quackMaterializeOf]를 사용하세요.
 *
 * @see quackMaterializeOf
 */
@Stable
public interface QuackDataModifierModel : Modifier.Element
