/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

/**
 * [Modifier.quackPluginLocal]로 제공된 값을 래핑합니다.
 * 이 클래스를 통해 꽥꽥 플러그인 구현체에 특정 값을 전달할 수 있습니다.
 *
 * @param value 꽥꽥 플러그인에서 사용할 데이터
 */
@Immutable
@JvmInline
public value class QuackPluginLocal(public val value: Any?) : Modifier.Element

/**
 * 꽥꽥 플러그인에서 사용할 데이터를 [Modifier]에게 전달합니다.
 * 이렇게 전달된 데이터는 [QuackPluginLocal]로 래핑되어 꽥꽥 플러그인 구현체에서
 * 사용할 수 있습니다.
 *
 * ```
 * interface QuackSamplePlugin : QuackPlugin {
 *   fun plugin(quackPluginLocal: QuackPluginLocal?)
 * }
 * ```
 *
 * @param value 꽥꽥 플러그인에서 사용할 데이터
 */
@Stable
public fun Modifier.quackPluginLocal(value: Any?): Modifier = this then QuackPluginLocal(value)
