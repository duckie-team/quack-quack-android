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

public val OutlinedGroup.Area: ImageVector
  get() {
    if (_area != null) {
      return _area!!
    }
    _area = Builder(
      name = "Area", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(9.62f, 4.82f)
        horizontalLineTo(5.72f)
        curveTo(5.1677f, 4.82f, 4.72f, 5.2677f, 4.72f, 5.82f)
        verticalLineTo(9.72f)
        curveTo(4.72f, 10.2723f, 5.1677f, 10.72f, 5.72f, 10.72f)
        horizontalLineTo(9.62f)
        curveTo(10.1723f, 10.72f, 10.62f, 10.2723f, 10.62f, 9.72f)
        verticalLineTo(5.82f)
        curveTo(10.62f, 5.2677f, 10.1723f, 4.82f, 9.62f, 4.82f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(18.2721f, 4.82f)
        horizontalLineTo(14.3721f)
        curveTo(13.8198f, 4.82f, 13.3721f, 5.2677f, 13.3721f, 5.82f)
        verticalLineTo(9.72f)
        curveTo(13.3721f, 10.2723f, 13.8198f, 10.72f, 14.3721f, 10.72f)
        horizontalLineTo(18.2721f)
        curveTo(18.8244f, 10.72f, 19.2721f, 10.2723f, 19.2721f, 9.72f)
        verticalLineTo(5.82f)
        curveTo(19.2721f, 5.2677f, 18.8244f, 4.82f, 18.2721f, 4.82f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(9.62f, 13.279f)
        horizontalLineTo(5.72f)
        curveTo(5.1677f, 13.279f, 4.72f, 13.7267f, 4.72f, 14.279f)
        verticalLineTo(18.179f)
        curveTo(4.72f, 18.7313f, 5.1677f, 19.179f, 5.72f, 19.179f)
        horizontalLineTo(9.62f)
        curveTo(10.1723f, 19.179f, 10.62f, 18.7313f, 10.62f, 18.179f)
        verticalLineTo(14.279f)
        curveTo(10.62f, 13.7267f, 10.1723f, 13.279f, 9.62f, 13.279f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(16.3221f, 19.179f)
        curveTo(15.7386f, 19.179f, 15.1683f, 19.006f, 14.6831f, 18.6818f)
        curveTo(14.198f, 18.3577f, 13.8199f, 17.897f, 13.5966f, 17.3579f)
        curveTo(13.3733f, 16.8189f, 13.3149f, 16.2257f, 13.4288f, 15.6535f)
        curveTo(13.5426f, 15.0812f, 13.8235f, 14.5556f, 14.2361f, 14.143f)
        curveTo(14.6487f, 13.7305f, 15.1743f, 13.4495f, 15.7466f, 13.3357f)
        curveTo(16.3188f, 13.2219f, 16.9119f, 13.2803f, 17.451f, 13.5036f)
        curveTo(17.99f, 13.7268f, 18.4508f, 14.1049f, 18.7749f, 14.5901f)
        curveTo(19.0991f, 15.0752f, 19.2721f, 15.6455f, 19.2721f, 16.229f)
        curveTo(19.2721f, 16.6164f, 19.1958f, 17.0f, 19.0475f, 17.3579f)
        curveTo(18.8993f, 17.7158f, 18.682f, 18.041f, 18.408f, 18.315f)
        curveTo(18.1341f, 18.5889f, 17.8089f, 18.8062f, 17.451f, 18.9544f)
        curveTo(17.0931f, 19.1027f, 16.7095f, 19.179f, 16.3221f, 19.179f)
        verticalLineTo(19.179f)
        close()
      }
    }
      .build()
    return _area!!
  }

private var _area: ImageVector? = null
