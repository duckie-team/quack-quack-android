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
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup

public val OutlinedGroup.Pager: ImageVector
  get() {
    if (_pager != null) {
      return _pager!!
    }
    _pager = Builder(
      name = "Pager", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
      viewportWidth = 20.0f, viewportHeight = 20.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Round,
        strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(5.2f, 16.0f)
        verticalLineTo(4.0f)
        horizontalLineTo(11.3714f)
        lineTo(14.8f, 7.3333f)
        verticalLineTo(16.0f)
        horizontalLineTo(5.2f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Round,
        strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(10.6857f, 4.0f)
        verticalLineTo(8.0f)
        horizontalLineTo(14.8f)
      }
    }
      .build()
    return _pager!!
  }

private var _pager: ImageVector? = null
