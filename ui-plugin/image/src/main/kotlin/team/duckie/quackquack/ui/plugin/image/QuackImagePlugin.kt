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

@Immutable
public sealed interface QuackImagePlugin : QuackPlugin {
  @Immutable
  public fun interface CoilImageLoader : QuackImagePlugin {
    @Stable
    public fun ImageLoader.Builder.builder(
      context: Context,
      src: Any?,
      contentDescription: String?,
      quackPluginLocal: QuackPluginLocal?,
    ): ImageLoader.Builder
  }
}
