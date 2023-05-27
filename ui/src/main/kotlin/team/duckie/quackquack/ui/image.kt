/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.LocalImageLoader
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackIcon
import team.duckie.quackquack.sugar.material.NoSugar
import team.duckie.quackquack.ui.plugin.EmptyQuackPlugins
import team.duckie.quackquack.ui.plugin.LocalQuackPlugins
import team.duckie.quackquack.ui.plugin.QuackPluginLocal
import team.duckie.quackquack.ui.plugin.image.QuackImagePlugin
import team.duckie.quackquack.ui.plugin.lastByTypeOrNull
import team.duckie.quackquack.util.applyIf
import team.duckie.quackquack.util.modifier.getElementByTypeOrNull

/**
 * 아이콘을 그립니다. 이 API는 꽥꽥 1.x.x 버전과 호환성을 위해 추가되었습니다.
 * 아이콘을 단독으로 그리기 위해선 [QuackIcon] 컴포저블을 대신 사용하세요.
 * 이 컴포저블은 사이즈 UX가 고려되지 않았습니다. (font scale 대응 없음)
 *
 * @param src 그릴 [아이콘][QuackIcon]
 * @param tint 아이콘에 입힐 틴트
 * @param contentScale 아이콘에 적용할 [contentScale][ContentScale]
 */
@Deprecated(
  message = "Displaying QuackIcon as QuackImage is no longer recommended; instead, use QuackIcon Composable.",
  replaceWith = ReplaceWith("QuackIcon"),
)
@NoSugar
@NonRestartableComposable
@Composable
public fun QuackImage(
  src: QuackIcon,
  modifier: Modifier = Modifier,
  tint: QuackColor = QuackColor.Unspecified,
  contentScale: ContentScale = ContentScale.Fit,
) {
  QuackIcon(
    icon = src,
    modifier = modifier,
    tint = tint,
    contentScale = contentScale,
  )
}

/**
 * drawable 리소스를 그립니다.
 *
 * @param src 그릴 drawable 리소스
 * @param tint drawable 리소스에 입힐 틴트
 * @param contentScale drawable 리소스에 적용할 [contentScale][ContentScale]
 * @param contentDescription 접근성 서비스에서 이 이미지가 무엇을 나타내는지 설명할 문구
 */
@NoSugar
@NonRestartableComposable
@Composable
public fun QuackImage(
  @DrawableRes src: Int,
  modifier: Modifier = Modifier,
  tint: QuackColor = QuackColor.Unspecified,
  contentScale: ContentScale = ContentScale.Fit,
  contentDescription: String? = null,
) {
  val currentColorFilter = remember(tint) { tint.toColorFilterOrNull() }

  Box(
    modifier
      .testTag("image")
      .applyIf(contentDescription != null) {
        semantics {
          role = Role.Image
          this.contentDescription = contentDescription!!
        }
      }
      .paint(
        painter = painterResource(src),
        colorFilter = currentColorFilter,
        contentScale = contentScale,
      ),
  )
}

/**
 * 주어진 소스로부터 이미지를 그립니다.
 *
 * 이 컴포저블은 내부적으로 [coil](https://coil-kt.github.io/coil/)을 사용합니다.
 * [ImageLoader] 사용자화는 [CoilImageLoader 플러그인][QuackImagePlugin.CoilImageLoader]으로 가능합니다.
 *
 * @param src 이미지 리소스의 링크
 * @param tint 이미지 리소스에 입힐 틴트
 * @param contentScale 이미지 리소스에 적용할 [contentScale][ContentScale]
 * @param contentDescription 접근성 서비스에서 이 이미지가 무엇을 나타내는지 설명할 문구
 */
@NoSugar
@NonRestartableComposable
@Composable
public fun QuackImage(
  src: Any?,
  modifier: Modifier = Modifier,
  tint: QuackColor = QuackColor.Unspecified,
  contentScale: ContentScale = ContentScale.Fit,
  contentDescription: String? = null,
) {
  val currentColorFilter = remember(tint) { tint.toColorFilterOrNull() }
  val imageLoader =
    LocalQuackPlugins.current.takeIf { it != EmptyQuackPlugins }?.let { plugins ->
      val context = LocalContext.current
      val quackPluginLocal = modifier.getElementByTypeOrNull<QuackPluginLocal>()
      val imageLoaderPlugins = plugins.lastByTypeOrNull<QuackImagePlugin.CoilImageLoader>()

      var builder = ImageLoader.Builder(context)
      if (imageLoaderPlugins != null) {
        with(imageLoaderPlugins) {
          builder = builder.builder(
            context = context,
            src = src,
            contentDescription = contentDescription,
            quackPluginLocal = quackPluginLocal,
          )
        }
      }
      builder.build()
    } ?: @Suppress("DEPRECATION") LocalImageLoader.current

  AsyncImage(
    model = src,
    modifier = modifier,
    imageLoader = imageLoader,
    colorFilter = currentColorFilter,
    contentScale = contentScale,
    contentDescription = contentDescription,
  )
}
