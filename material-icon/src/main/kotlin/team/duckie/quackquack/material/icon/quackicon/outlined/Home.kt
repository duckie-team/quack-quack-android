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

public val OutlinedGroup.Home: ImageVector
  get() {
    if (_home != null) {
      return _home!!
    }
    _home = Builder(
      name = "Home", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(19.9791f, 8.108f)
        lineTo(13.8791f, 3.133f)
        curveTo(13.3437f, 2.7429f, 12.6981f, 2.5333f, 12.0356f, 2.5343f)
        curveTo(11.3732f, 2.5354f, 10.7282f, 2.7472f, 10.1941f, 3.139f)
        lineTo(4.0111f, 8.201f)
        curveTo(3.6263f, 8.4834f, 3.3133f, 8.8523f, 3.0972f, 9.2778f)
        curveTo(2.8812f, 9.7034f, 2.7681f, 10.1737f, 2.7671f, 10.651f)
        verticalLineTo(18.375f)
        curveTo(2.7696f, 18.7785f, 2.8515f, 19.1776f, 3.0083f, 19.5494f)
        curveTo(3.165f, 19.9212f, 3.3935f, 20.2585f, 3.6806f, 20.542f)
        curveTo(3.9678f, 20.8256f, 4.3079f, 21.0497f, 4.6817f, 21.2017f)
        curveTo(5.0555f, 21.3538f, 5.4556f, 21.4306f, 5.8591f, 21.428f)
        horizontalLineTo(7.5101f)
        curveTo(7.9197f, 21.4283f, 8.3126f, 21.2659f, 8.6025f, 20.9765f)
        curveTo(8.8924f, 20.6872f, 9.0556f, 20.2946f, 9.0561f, 19.885f)
        verticalLineTo(16.271f)
        curveTo(9.0561f, 15.774f, 9.2535f, 15.2973f, 9.605f, 14.9459f)
        curveTo(9.9564f, 14.5944f, 10.4331f, 14.397f, 10.9301f, 14.397f)
        horizontalLineTo(13.0711f)
        curveTo(13.3171f, 14.3971f, 13.5606f, 14.4457f, 13.7878f, 14.54f)
        curveTo(14.015f, 14.6342f, 14.2214f, 14.7723f, 14.3952f, 14.9463f)
        curveTo(14.569f, 15.1203f, 14.7069f, 15.3269f, 14.8009f, 15.5542f)
        curveTo(14.8949f, 15.7815f, 14.9432f, 16.025f, 14.9431f, 16.271f)
        verticalLineTo(19.885f)
        curveTo(14.943f, 20.0881f, 14.9829f, 20.2892f, 15.0605f, 20.4768f)
        curveTo(15.1382f, 20.6644f, 15.252f, 20.8349f, 15.3956f, 20.9785f)
        curveTo(15.5392f, 21.1221f, 15.7097f, 21.2359f, 15.8973f, 21.3136f)
        curveTo(16.0849f, 21.3912f, 16.286f, 21.4311f, 16.4891f, 21.431f)
        horizontalLineTo(18.1421f)
        curveTo(18.9569f, 21.4361f, 19.7404f, 21.1173f, 20.3203f, 20.5447f)
        curveTo(20.9001f, 19.9722f, 21.2288f, 19.1928f, 21.2341f, 18.378f)
        verticalLineTo(10.563f)
        curveTo(21.2329f, 10.0839f, 21.1187f, 9.6118f, 20.9006f, 9.1852f)
        curveTo(20.6825f, 8.7586f, 20.3668f, 8.3895f, 19.9791f, 8.108f)
        close()
      }
    }
      .build()
    return _home!!
  }

private var _home: ImageVector? = null
