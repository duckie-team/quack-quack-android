/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin.image

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import coil.ImageLoader
import team.duckie.quackquack.ui.plugin.QuackPlugin
import team.duckie.quackquack.ui.plugin.QuackPluginLocal

/** `QuackImage`에 적용되는 플러그인 모음 */
@Immutable
public sealed interface QuackImagePlugin : QuackPlugin {
  /**
   * `QuackImage`에서 사용하는 [Coil](https://coil-kt.github.io/coil/) 요청에 사용할
   * [ImageLoader.Builder]를 생성합니다.
   */
  @Immutable
  public fun interface CoilImageLoader : QuackImagePlugin {
    /**
     * [ImageLoader.Builder] 생성자에 이어서 적용할 [ImageLoader.Builder]를 반환합니다.
     *
     * @param context [ImageLoader]를 생성할 때 사용된 [Context]
     * @param src `QuackImage`의 `src` 인자로 제공된 값
     * @param contentDescription `QuackImage`의 `contentDescription` 인자로 제공된 값
     * @param quackPluginLocal `Modifier.quackPluginLocal`로 제공된 값
     */
    @Stable
    public fun ImageLoader.Builder.builder(
      context: Context,
      src: Any?,
      contentDescription: String?,
      quackPluginLocal: QuackPluginLocal?,
    ): ImageLoader.Builder
  }
}
