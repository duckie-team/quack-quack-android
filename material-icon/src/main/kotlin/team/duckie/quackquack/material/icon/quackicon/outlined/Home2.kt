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
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup

public val OutlinedGroup.Home2: ImageVector
  get() {
    if (_home2 != null) {
      return _home2!!
    }
    _home2 = Builder(
      name = "Home2", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(13.8304f, 2.605f)
        lineTo(19.7813f, 7.6057f)
        curveTo(20.1584f, 7.8893f, 20.4653f, 8.2607f, 20.677f, 8.6897f)
        curveTo(20.8888f, 9.1186f, 20.9994f, 9.5931f, 21.0f, 10.0744f)
        verticalLineTo(17.9299f)
        curveTo(20.9948f, 18.7493f, 20.6744f, 19.533f, 20.1091f, 20.1087f)
        curveTo(19.5438f, 20.6845f, 18.7799f, 21.005f, 17.9855f, 20.9999f)
        horizontalLineTo(16.374f)
        horizontalLineTo(7.6241f)
        horizontalLineTo(6.0145f)
        curveTo(5.6211f, 21.0026f, 5.231f, 20.9253f, 4.8666f, 20.7724f)
        curveTo(4.5022f, 20.6195f, 4.1706f, 20.3941f, 3.8906f, 20.109f)
        curveTo(3.6107f, 19.8239f, 3.388f, 19.4848f, 3.2351f, 19.1109f)
        curveTo(3.0823f, 18.737f, 3.0024f, 18.3357f, 3.0f, 17.9299f)
        verticalLineTo(10.1629f)
        curveTo(3.001f, 9.683f, 3.1112f, 9.21f, 3.3219f, 8.7821f)
        curveTo(3.5325f, 8.3541f, 3.8377f, 7.9832f, 4.2128f, 7.6992f)
        lineTo(10.2378f, 2.605f)
        curveTo(10.7592f, 2.2119f, 11.3883f, 2.0f, 12.0341f, 2.0f)
        curveTo(12.68f, 2.0f, 13.309f, 2.2119f, 13.8304f, 2.605f)
        close()
      }
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = NonZero
      ) {
        moveTo(9.0f, 17.0f)
        lineTo(15.0f, 17.0f)
        arcTo(1.0f, 1.0f, 0.0f, false, true, 16.0f, 18.0f)
        lineTo(16.0f, 18.0f)
        arcTo(1.0f, 1.0f, 0.0f, false, true, 15.0f, 19.0f)
        lineTo(9.0f, 19.0f)
        arcTo(1.0f, 1.0f, 0.0f, false, true, 8.0f, 18.0f)
        lineTo(8.0f, 18.0f)
        arcTo(1.0f, 1.0f, 0.0f, false, true, 9.0f, 17.0f)
        close()
      }
    }
      .build()
    return _home2!!
  }

private var _home2: ImageVector? = null
