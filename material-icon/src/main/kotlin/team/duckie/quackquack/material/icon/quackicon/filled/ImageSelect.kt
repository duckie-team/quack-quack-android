/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon.quackicon.filled

import androidx.compose.ui.graphics.StrokeCap.Companion.Round as strokeCapRound
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round as strokeJoinRound
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.quackicon.FilledGroup

public val FilledGroup.ImageSelect: ImageVector
  get() {
    if (_imageSelect != null) {
      return _imageSelect!!
    }
    _imageSelect = Builder(
      name = "ImageSelect", defaultWidth = 24.0.dp, defaultHeight =
      24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      group {
        path(
          fill = SolidColor(Color(0xFF222222)), stroke = null, fillAlpha = 0.5f,
          strokeAlpha = 0.5f, strokeLineWidth = 0.0f, strokeLineCap = Butt,
          strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(24.0f, 12.0f)
          curveTo(24.0f, 5.3726f, 18.6274f, 0.0f, 12.0f, 0.0f)
          curveTo(5.3726f, 0.0f, 0.0f, 5.3726f, 0.0f, 12.0f)
          curveTo(0.0f, 18.6274f, 5.3726f, 24.0f, 12.0f, 24.0f)
          curveTo(18.6274f, 24.0f, 24.0f, 18.6274f, 24.0f, 12.0f)
          close()
        }
        path(
          fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
          strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
          strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(16.6881f, 8.868f)
          horizontalLineTo(14.4351f)
          verticalLineTo(8.71f)
          curveTo(14.4351f, 8.3974f, 14.3111f, 8.0976f, 14.0902f, 7.8763f)
          curveTo(13.8693f, 7.6551f, 13.5697f, 7.5305f, 13.2571f, 7.53f)
          horizontalLineTo(10.7421f)
          curveTo(10.4292f, 7.53f, 10.129f, 7.6543f, 9.9077f, 7.8756f)
          curveTo(9.6864f, 8.0969f, 9.5621f, 8.397f, 9.5621f, 8.71f)
          verticalLineTo(8.868f)
          horizontalLineTo(7.3111f)
          curveTo(7.092f, 8.868f, 6.8819f, 8.955f, 6.727f, 9.1099f)
          curveTo(6.5721f, 9.2648f, 6.4851f, 9.4749f, 6.4851f, 9.694f)
          verticalLineTo(15.644f)
          curveTo(6.4851f, 15.7525f, 6.5065f, 15.8599f, 6.548f, 15.9601f)
          curveTo(6.5895f, 16.0603f, 6.6503f, 16.1514f, 6.727f, 16.2281f)
          curveTo(6.8037f, 16.3048f, 6.8948f, 16.3656f, 6.995f, 16.4071f)
          curveTo(7.0952f, 16.4486f, 7.2026f, 16.47f, 7.3111f, 16.47f)
          horizontalLineTo(16.6881f)
          curveTo(16.7966f, 16.47f, 16.904f, 16.4486f, 17.0042f, 16.4071f)
          curveTo(17.1044f, 16.3656f, 17.1955f, 16.3048f, 17.2722f, 16.2281f)
          curveTo(17.3489f, 16.1514f, 17.4097f, 16.0603f, 17.4512f, 15.9601f)
          curveTo(17.4927f, 15.8599f, 17.5141f, 15.7525f, 17.5141f, 15.644f)
          verticalLineTo(9.694f)
          curveTo(17.5141f, 9.5855f, 17.4927f, 9.4781f, 17.4512f, 9.3779f)
          curveTo(17.4097f, 9.2777f, 17.3489f, 9.1866f, 17.2722f, 9.1099f)
          curveTo(17.1955f, 9.0332f, 17.1044f, 8.9724f, 17.0042f, 8.9309f)
          curveTo(16.904f, 8.8894f, 16.7966f, 8.868f, 16.6881f, 8.868f)
          close()
        }
        path(
          fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
          strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
          strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(12.0001f, 14.189f)
          curveTo(12.992f, 14.189f, 13.7961f, 13.3849f, 13.7961f, 12.393f)
          curveTo(13.7961f, 11.4011f, 12.992f, 10.597f, 12.0001f, 10.597f)
          curveTo(11.0082f, 10.597f, 10.2041f, 11.4011f, 10.2041f, 12.393f)
          curveTo(10.2041f, 13.3849f, 11.0082f, 14.189f, 12.0001f, 14.189f)
          close()
        }
      }
    }
      .build()
    return _imageSelect!!
  }

private var _imageSelect: ImageVector? = null
