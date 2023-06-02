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

public val OutlinedGroup.Profile: ImageVector
  get() {
    if (_profile != null) {
      return _profile!!
    }
    _profile = Builder(
      name = "Profile", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(15.818f, 7.307f)
        curveTo(15.8178f, 8.0603f, 15.5942f, 8.7965f, 15.1756f, 9.4228f)
        curveTo(14.757f, 10.049f, 14.1621f, 10.5371f, 13.4661f, 10.8252f)
        curveTo(12.7702f, 11.1134f, 12.0044f, 11.1888f, 11.2656f, 11.0418f)
        curveTo(10.5268f, 10.8948f, 9.8482f, 10.532f, 9.3156f, 9.9994f)
        curveTo(8.783f, 9.4667f, 8.4202f, 8.7881f, 8.2732f, 8.0493f)
        curveTo(8.1262f, 7.3106f, 8.2015f, 6.5448f, 8.4897f, 5.8488f)
        curveTo(8.7779f, 5.1529f, 9.2659f, 4.558f, 9.8922f, 4.1393f)
        curveTo(10.5184f, 3.7207f, 11.2547f, 3.4972f, 12.008f, 3.497f)
        curveTo(13.0184f, 3.497f, 13.9875f, 3.8984f, 14.702f, 4.6129f)
        curveTo(15.4165f, 5.3274f, 15.818f, 6.2965f, 15.818f, 7.307f)
        verticalLineTo(7.307f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(19.1589f, 18.344f)
        curveTo(19.1589f, 15.529f, 19.0009f, 13.702f, 17.8589f, 12.6f)
        curveTo(17.5165f, 12.2932f, 17.0745f, 12.1211f, 16.6148f, 12.1154f)
        curveTo(16.1551f, 12.1097f, 15.7089f, 12.2708f, 15.3589f, 12.569f)
        curveTo(14.3845f, 13.2764f, 13.2131f, 13.6611f, 12.0089f, 13.669f)
        curveTo(10.8076f, 13.6607f, 9.6392f, 13.276f, 8.6679f, 12.569f)
        curveTo(8.3151f, 12.2704f, 7.866f, 12.1097f, 7.4038f, 12.1167f)
        curveTo(6.9416f, 12.1236f, 6.4976f, 12.2978f, 6.1539f, 12.607f)
        curveTo(4.8829f, 13.819f, 4.7619f, 15.913f, 4.8719f, 19.232f)
        curveTo(4.884f, 19.5702f, 5.0257f, 19.8907f, 5.2678f, 20.1272f)
        curveTo(5.5098f, 20.3636f, 5.8336f, 20.4978f, 6.1719f, 20.502f)
        horizontalLineTo(17.8579f)
        curveTo(18.2027f, 20.502f, 18.5334f, 20.365f, 18.7772f, 20.1212f)
        curveTo(19.021f, 19.8774f, 19.1579f, 19.5468f, 19.1579f, 19.202f)
        lineTo(19.1589f, 18.344f)
        close()
      }
    }
      .build()
    return _profile!!
  }

private var _profile: ImageVector? = null
