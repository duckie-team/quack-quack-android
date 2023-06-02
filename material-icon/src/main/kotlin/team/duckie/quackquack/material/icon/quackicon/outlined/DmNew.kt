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

public val OutlinedGroup.DmNew: ImageVector
  get() {
    if (_dmNew != null) {
      return _dmNew!!
    }
    _dmNew = Builder(
      name = "DmNew", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(17.647f, 8.957f)
        lineTo(19.239f, 4.885f)
        curveTo(19.334f, 4.6417f, 19.3559f, 4.3759f, 19.3022f, 4.1203f)
        curveTo(19.2486f, 3.8647f, 19.1215f, 3.6302f, 18.9367f, 3.4456f)
        curveTo(18.7519f, 3.261f, 18.5173f, 3.1343f, 18.2617f, 3.0809f)
        curveTo(18.006f, 3.0275f, 17.7403f, 3.0498f, 17.497f, 3.145f)
        lineTo(3.192f, 8.744f)
        curveTo(2.9499f, 8.8392f, 2.7406f, 9.0026f, 2.5896f, 9.2145f)
        curveTo(2.4385f, 9.4263f, 2.3522f, 9.6774f, 2.3411f, 9.9374f)
        curveTo(2.33f, 10.1973f, 2.3945f, 10.4549f, 2.5269f, 10.6788f)
        curveTo(2.6593f, 10.9028f, 2.8539f, 11.0835f, 3.087f, 11.199f)
        lineTo(8.087f, 13.693f)
        curveTo(8.3464f, 13.8233f, 8.5568f, 14.0337f, 8.687f, 14.293f)
        lineTo(11.192f, 19.299f)
        curveTo(11.3081f, 19.5316f, 11.4892f, 19.7256f, 11.7134f, 19.8574f)
        curveTo(11.9375f, 19.9892f, 12.195f, 20.0531f, 12.4548f, 20.0415f)
        curveTo(12.7145f, 20.0299f, 12.9653f, 19.9431f, 13.1768f, 19.7918f)
        curveTo(13.3882f, 19.6405f, 13.5512f, 19.4311f, 13.646f, 19.189f)
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(8.49f, 13.893f)
        lineTo(12.403f, 9.98f)
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(18.1011f, 12.492f)
        verticalLineTo(18.716f)
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(21.213f, 15.604f)
        horizontalLineTo(14.989f)
      }
    }
      .build()
    return _dmNew!!
  }

private var _dmNew: ImageVector? = null
