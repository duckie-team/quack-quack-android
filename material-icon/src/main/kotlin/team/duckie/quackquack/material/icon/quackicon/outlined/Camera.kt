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

public val OutlinedGroup.Camera: ImageVector
  get() {
    if (_camera != null) {
      return _camera!!
    }
    _camera = Builder(
      name = "Camera", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(19.8231f, 6.774f)
        horizontalLineTo(16.0671f)
        verticalLineTo(6.511f)
        curveTo(16.0671f, 6.2524f, 16.0161f, 5.9964f, 15.9172f, 5.7575f)
        curveTo(15.8182f, 5.5186f, 15.6732f, 5.3015f, 15.4904f, 5.1187f)
        curveTo(15.3075f, 4.9359f, 15.0905f, 4.7908f, 14.8516f, 4.6919f)
        curveTo(14.6127f, 4.5929f, 14.3566f, 4.542f, 14.0981f, 4.542f)
        horizontalLineTo(9.8981f)
        curveTo(9.3765f, 4.5431f, 8.8768f, 4.751f, 8.5084f, 5.1201f)
        curveTo(8.14f, 5.4893f, 7.9331f, 5.9895f, 7.9331f, 6.511f)
        verticalLineTo(6.774f)
        horizontalLineTo(4.1771f)
        curveTo(3.9961f, 6.774f, 3.8169f, 6.8096f, 3.6497f, 6.8789f)
        curveTo(3.4825f, 6.9481f, 3.3306f, 7.0497f, 3.2027f, 7.1776f)
        curveTo(3.0747f, 7.3056f, 2.9732f, 7.4575f, 2.904f, 7.6247f)
        curveTo(2.8347f, 7.7919f, 2.7991f, 7.971f, 2.7991f, 8.152f)
        verticalLineTo(18.079f)
        curveTo(2.7989f, 18.26f, 2.8345f, 18.4392f, 2.9037f, 18.6065f)
        curveTo(2.9729f, 18.7737f, 3.0744f, 18.9257f, 3.2024f, 19.0537f)
        curveTo(3.3304f, 19.1817f, 3.4823f, 19.2832f, 3.6496f, 19.3524f)
        curveTo(3.8168f, 19.4216f, 3.9961f, 19.4571f, 4.1771f, 19.457f)
        horizontalLineTo(19.8231f)
        curveTo(20.0041f, 19.4571f, 20.1833f, 19.4216f, 20.3506f, 19.3524f)
        curveTo(20.5178f, 19.2832f, 20.6698f, 19.1817f, 20.7978f, 19.0537f)
        curveTo(20.9257f, 18.9257f, 21.0272f, 18.7737f, 21.0964f, 18.6065f)
        curveTo(21.1657f, 18.4392f, 21.2012f, 18.26f, 21.2011f, 18.079f)
        verticalLineTo(8.152f)
        curveTo(21.2011f, 7.971f, 21.1654f, 7.7919f, 21.0962f, 7.6247f)
        curveTo(21.0269f, 7.4575f, 20.9254f, 7.3056f, 20.7975f, 7.1776f)
        curveTo(20.6695f, 7.0497f, 20.5176f, 6.9481f, 20.3504f, 6.8789f)
        curveTo(20.1832f, 6.8096f, 20.004f, 6.774f, 19.8231f, 6.774f)
        verticalLineTo(6.774f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(12.0009f, 15.653f)
        curveTo(13.6561f, 15.653f, 14.9979f, 14.3112f, 14.9979f, 12.656f)
        curveTo(14.9979f, 11.0008f, 13.6561f, 9.659f, 12.0009f, 9.659f)
        curveTo(10.3457f, 9.659f, 9.0039f, 11.0008f, 9.0039f, 12.656f)
        curveTo(9.0039f, 14.3112f, 10.3457f, 15.653f, 12.0009f, 15.653f)
        close()
      }
    }
      .build()
    return _camera!!
  }

private var _camera: ImageVector? = null
