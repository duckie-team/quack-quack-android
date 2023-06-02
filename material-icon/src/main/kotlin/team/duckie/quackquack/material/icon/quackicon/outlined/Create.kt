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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup

public val OutlinedGroup.Create: ImageVector
  get() {
    if (_create != null) {
      return _create!!
    }
    _create = Builder(
      name = "Create", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = strokeJoinRound,
        strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(20.0f, 8.0f)
        lineTo(16.0f, 4.0f)
        lineTo(6.0f, 14.0f)
        verticalLineTo(18.0f)
        horizontalLineTo(10.0f)
        lineTo(20.0f, 8.0f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = strokeJoinRound,
        strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(14.0f, 6.0f)
        lineTo(18.0f, 10.0f)
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(5.0f, 21.0f)
        horizontalLineTo(20.0f)
      }
    }
      .build()
    return _create!!
  }

private var _create: ImageVector? = null
