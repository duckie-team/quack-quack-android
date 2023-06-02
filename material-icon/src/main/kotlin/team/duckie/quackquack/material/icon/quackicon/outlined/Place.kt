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

public val OutlinedGroup.Place: ImageVector
  get() {
    if (_place != null) {
      return _place!!
    }
    _place = Builder(
      name = "Place", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(11.489f, 4.858f)
        curveTo(15.941f, 4.419f, 18.918f, 9.426f, 16.732f, 13.329f)
        lineTo(12.22f, 19.102f)
        curveTo(12.2082f, 19.123f, 12.1909f, 19.1405f, 12.1701f, 19.1527f)
        curveTo(12.1493f, 19.1649f, 12.1256f, 19.1713f, 12.1015f, 19.1713f)
        curveTo(12.0774f, 19.1713f, 12.0537f, 19.1649f, 12.0329f, 19.1527f)
        curveTo(12.012f, 19.1405f, 11.9948f, 19.123f, 11.983f, 19.102f)
        lineTo(7.304f, 13.202f)
        curveTo(6.5539f, 11.9432f, 6.3281f, 10.4401f, 6.6752f, 9.0164f)
        curveTo(7.0224f, 7.5928f, 7.9146f, 6.3623f, 9.16f, 5.59f)
        curveTo(9.6846f, 5.2882f, 10.2546f, 5.0734f, 10.848f, 4.954f)
        curveTo(11.0599f, 4.9113f, 11.2738f, 4.8793f, 11.489f, 4.858f)
        verticalLineTo(4.858f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(13.7831f, 10.352f)
        curveTo(13.7833f, 10.7049f, 13.6788f, 11.0499f, 13.4829f, 11.3434f)
        curveTo(13.287f, 11.6369f, 13.0085f, 11.8657f, 12.6825f, 12.0009f)
        curveTo(12.3565f, 12.1361f, 11.9978f, 12.1716f, 11.6517f, 12.1028f)
        curveTo(11.3055f, 12.0341f, 10.9876f, 11.8643f, 10.738f, 11.6148f)
        curveTo(10.4884f, 11.3654f, 10.3184f, 11.0475f, 10.2494f, 10.7014f)
        curveTo(10.1805f, 10.3553f, 10.2158f, 9.9966f, 10.3508f, 9.6705f)
        curveTo(10.4858f, 9.3445f, 10.7144f, 9.0658f, 11.0078f, 8.8697f)
        curveTo(11.3012f, 8.6737f, 11.6462f, 8.569f, 11.9991f, 8.569f)
        curveTo(12.4721f, 8.569f, 12.9257f, 8.7568f, 13.2602f, 9.0912f)
        curveTo(13.5947f, 9.4255f, 13.7828f, 9.879f, 13.7831f, 10.352f)
        verticalLineTo(10.352f)
        close()
      }
    }
      .build()
    return _place!!
  }

private var _place: ImageVector? = null
