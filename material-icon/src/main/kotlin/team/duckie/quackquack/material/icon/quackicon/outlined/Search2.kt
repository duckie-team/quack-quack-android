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

public val OutlinedGroup.Search2: ImageVector
  get() {
    if (_search2 != null) {
      return _search2!!
    }
    _search2 = Builder(
      name = "Search2", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(16.0f, 3.0f)
        horizontalLineTo(4.0f)
        verticalLineTo(21.0f)
        horizontalLineTo(16.0f)
        verticalLineTo(18.0f)
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.6f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(14.5f, 15.0f)
        curveTo(16.9853f, 15.0f, 19.0f, 12.9853f, 19.0f, 10.5f)
        curveTo(19.0f, 8.0147f, 16.9853f, 6.0f, 14.5f, 6.0f)
        curveTo(12.0147f, 6.0f, 10.0f, 8.0147f, 10.0f, 10.5f)
        curveTo(10.0f, 12.9853f, 12.0147f, 15.0f, 14.5f, 15.0f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.6f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(18.0f, 14.0f)
        lineTo(21.0f, 17.0f)
      }
    }
      .build()
    return _search2!!
  }

private var _search2: ImageVector? = null
