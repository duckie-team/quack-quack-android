/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon.quackicon.outlined

import androidx.compose.ui.graphics.StrokeCap.Companion.Round as strokeCapRound
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round as strokeJoinRound
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup

public val OutlinedGroup.Bookmark: ImageVector
  get() {
    if (_bookmark != null) {
      return _bookmark!!
    }
    _bookmark = Builder(
      name = "Bookmark", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(7.5151f, 4.945f)
        curveTo(7.1485f, 4.945f, 6.797f, 5.0906f, 6.5378f, 5.3498f)
        curveTo(6.2787f, 5.609f, 6.1331f, 5.9605f, 6.1331f, 6.327f)
        verticalLineTo(17.673f)
        curveTo(6.1323f, 17.9158f, 6.1956f, 18.1546f, 6.3165f, 18.3651f)
        curveTo(6.4375f, 18.5757f, 6.6118f, 18.7507f, 6.8219f, 18.8724f)
        curveTo(7.0321f, 18.9941f, 7.2706f, 19.0582f, 7.5134f, 19.0583f)
        curveTo(7.7562f, 19.0584f, 7.9948f, 18.9945f, 8.2051f, 18.873f)
        lineTo(9.0661f, 18.373f)
        lineTo(12.0001f, 16.679f)
        lineTo(14.9331f, 18.372f)
        lineTo(15.7941f, 18.872f)
        curveTo(16.0043f, 18.9935f, 16.2429f, 19.0574f, 16.4857f, 19.0573f)
        curveTo(16.7285f, 19.0572f, 16.967f, 18.9931f, 17.1772f, 18.8714f)
        curveTo(17.3873f, 18.7497f, 17.5616f, 18.5747f, 17.6826f, 18.3641f)
        curveTo(17.8035f, 18.1536f, 17.8668f, 17.9148f, 17.8661f, 17.672f)
        verticalLineTo(6.327f)
        curveTo(17.8662f, 6.1454f, 17.8305f, 5.9655f, 17.761f, 5.7977f)
        curveTo(17.6916f, 5.63f, 17.5897f, 5.4775f, 17.4612f, 5.3491f)
        curveTo(17.3328f, 5.2208f, 17.1802f, 5.119f, 17.0124f, 5.0496f)
        curveTo(16.8445f, 4.9803f, 16.6647f, 4.9447f, 16.4831f, 4.945f)
        horizontalLineTo(7.5151f)
        close()
      }
    }
      .build()
    return _bookmark!!
  }

private var _bookmark: ImageVector? = null
