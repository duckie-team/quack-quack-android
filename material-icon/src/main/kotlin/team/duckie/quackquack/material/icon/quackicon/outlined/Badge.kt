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

public val OutlinedGroup.Badge: ImageVector
  get() {
    if (_badge != null) {
      return _badge!!
    }
    _badge = Builder(
      name = "Badge", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(20.189f, 11.999f)
        curveTo(20.1892f, 13.6187f, 19.7091f, 15.2021f, 18.8094f, 16.5489f)
        curveTo(17.9096f, 17.8958f, 16.6307f, 18.9455f, 15.1343f, 19.5655f)
        curveTo(13.6379f, 20.1854f, 11.9913f, 20.3476f, 10.4027f, 20.0317f)
        curveTo(8.8141f, 19.7158f, 7.3548f, 18.9358f, 6.2095f, 17.7905f)
        curveTo(5.0642f, 16.6452f, 4.2843f, 15.186f, 3.9683f, 13.5974f)
        curveTo(3.6524f, 12.0087f, 3.8147f, 10.3621f, 4.4346f, 8.8657f)
        curveTo(5.0545f, 7.3694f, 6.1043f, 6.0904f, 7.4511f, 5.1907f)
        curveTo(8.7979f, 4.2909f, 10.3813f, 3.8108f, 12.001f, 3.811f)
        curveTo(14.1725f, 3.8113f, 16.255f, 4.674f, 17.7905f, 6.2095f)
        curveTo(19.326f, 7.745f, 20.1888f, 9.8275f, 20.189f, 11.999f)
        verticalLineTo(11.999f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(11.9999f, 14.613f)
        lineTo(9.4199f, 15.97f)
        lineTo(9.9139f, 13.093f)
        lineTo(7.8259f, 11.064f)
        lineTo(10.7099f, 10.644f)
        lineTo(11.9999f, 8.032f)
        lineTo(13.2889f, 10.644f)
        lineTo(16.1729f, 11.064f)
        lineTo(14.0859f, 13.093f)
        lineTo(14.5789f, 15.965f)
        lineTo(11.9999f, 14.613f)
        close()
      }
    }
      .build()
    return _badge!!
  }

private var _badge: ImageVector? = null
