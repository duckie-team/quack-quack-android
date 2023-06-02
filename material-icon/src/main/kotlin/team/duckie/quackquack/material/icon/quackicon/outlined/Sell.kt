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

public val OutlinedGroup.Sell: ImageVector
  get() {
    if (_sell != null) {
      return _sell!!
    }
    _sell = Builder(
      name = "Sell", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(18.032f, 19.83f)
        verticalLineTo(5.351f)
        curveTo(18.032f, 4.9876f, 17.8877f, 4.639f, 17.6308f, 4.3819f)
        curveTo(17.3739f, 4.1248f, 17.0255f, 3.9803f, 16.662f, 3.98f)
        horizontalLineTo(7.339f)
        curveTo(7.1591f, 3.9796f, 6.9809f, 4.0147f, 6.8146f, 4.0833f)
        curveTo(6.6483f, 4.1518f, 6.4971f, 4.2525f, 6.3698f, 4.3796f)
        curveTo(6.2424f, 4.5067f, 6.1414f, 4.6576f, 6.0724f, 4.8238f)
        curveTo(6.0035f, 4.99f, 5.968f, 5.1681f, 5.968f, 5.348f)
        curveTo(5.968f, 9.195f, 5.968f, 19.827f, 5.968f, 19.827f)
        lineTo(7.477f, 19.098f)
        lineTo(8.985f, 19.827f)
        lineTo(10.493f, 19.098f)
        lineTo(12.002f, 19.827f)
        lineTo(13.51f, 19.098f)
        lineTo(15.017f, 19.83f)
        lineTo(16.526f, 19.101f)
        lineTo(18.032f, 19.83f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(8.6509f, 8.059f)
        horizontalLineTo(15.3459f)
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(8.6509f, 10.884f)
        horizontalLineTo(15.3459f)
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(8.6509f, 13.709f)
        horizontalLineTo(11.9989f)
      }
    }
      .build()
    return _sell!!
  }

private var _sell: ImageVector? = null
