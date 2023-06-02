/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon.quackicon.filled

import androidx.compose.ui.graphics.StrokeCap.Companion.Round as strokeCapRound
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round as strokeJoinRound
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.quackicon.FilledGroup

public val FilledGroup.Delete: ImageVector
  get() {
    if (_delete != null) {
      return _delete!!
    }
    _delete = Builder(
      name = "Delete", defaultWidth = 16.0.dp, defaultHeight = 16.0.dp,
      viewportWidth = 16.0f, viewportHeight = 16.0f
    ).apply {
      group {
        path(
          fill = SolidColor(Color(0xFF222222)), stroke = null, fillAlpha = 0.2f,
          strokeAlpha = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt,
          strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(15.0f, 8.0f)
          curveTo(15.0f, 4.134f, 11.866f, 1.0f, 8.0f, 1.0f)
          curveTo(4.134f, 1.0f, 1.0f, 4.134f, 1.0f, 8.0f)
          curveTo(1.0f, 11.866f, 4.134f, 15.0f, 8.0f, 15.0f)
          curveTo(11.866f, 15.0f, 15.0f, 11.866f, 15.0f, 8.0f)
          close()
        }
        path(
          fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
          strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
          strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(10.0f, 6.0f)
          lineTo(6.0f, 10.0f)
        }
        path(
          fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
          strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
          strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(6.0f, 6.0f)
          lineTo(10.0f, 10.0f)
        }
      }
    }
      .build()
    return _delete!!
  }

private var _delete: ImageVector? = null
