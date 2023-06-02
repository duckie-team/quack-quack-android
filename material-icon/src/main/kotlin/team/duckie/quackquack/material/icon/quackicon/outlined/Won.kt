/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon.quackicon.outlined

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

public val OutlinedGroup.Won: ImageVector
  get() {
    if (_won != null) {
      return _won!!
    }
    _won = Builder(
      name = "Won", defaultWidth = 16.0.dp, defaultHeight = 16.0.dp, viewportWidth
      = 16.0f, viewportHeight = 16.0f
    ).apply {
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = NonZero
      ) {
        moveTo(14.5521f, 7.313f)
        horizontalLineTo(12.8221f)
        lineTo(14.0221f, 2.946f)
        horizontalLineTo(13.0001f)
        lineTo(11.8361f, 7.313f)
        horizontalLineTo(9.7111f)
        lineTo(8.5201f, 2.959f)
        horizontalLineTo(7.5001f)
        lineTo(6.3001f, 7.313f)
        horizontalLineTo(4.1801f)
        lineTo(3.0161f, 2.946f)
        horizontalLineTo(1.9801f)
        lineTo(3.1801f, 7.313f)
        horizontalLineTo(1.4441f)
        verticalLineTo(8.322f)
        horizontalLineTo(3.4581f)
        lineTo(4.7581f, 13.054f)
        horizontalLineTo(5.6961f)
        lineTo(6.9811f, 8.322f)
        horizontalLineTo(9.0091f)
        lineTo(10.3021f, 13.054f)
        horizontalLineTo(11.2401f)
        lineTo(12.5401f, 8.322f)
        horizontalLineTo(14.5461f)
        lineTo(14.5521f, 7.313f)
        close()
        moveTo(5.2281f, 11.247f)
        lineTo(4.4481f, 8.322f)
        horizontalLineTo(6.0291f)
        lineTo(5.2281f, 11.247f)
        close()
        moveTo(7.2631f, 7.313f)
        lineTo(8.0001f, 4.597f)
        lineTo(8.7421f, 7.313f)
        horizontalLineTo(7.2631f)
        close()
        moveTo(10.7861f, 11.247f)
        lineTo(9.9861f, 8.322f)
        horizontalLineTo(11.5661f)
        lineTo(10.7861f, 11.247f)
        close()
      }
    }
      .build()
    return _won!!
  }

private var _won: ImageVector? = null
