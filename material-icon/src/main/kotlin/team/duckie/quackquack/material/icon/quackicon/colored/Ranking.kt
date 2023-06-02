/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon.quackicon.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.quackicon.ColoredGroup

public val ColoredGroup.Ranking: ImageVector
  get() {
    if (_ranking != null) {
      return _ranking!!
    }
    _ranking = Builder(
      name = "Ranking", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = Round,
        strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(3.0f, 15.0f)
        curveTo(3.0f, 14.4477f, 3.4477f, 14.0f, 4.0f, 14.0f)
        horizontalLineTo(6.0f)
        curveTo(6.5523f, 14.0f, 7.0f, 14.4477f, 7.0f, 15.0f)
        verticalLineTo(21.0f)
        horizontalLineTo(3.0f)
        verticalLineTo(15.0f)
        close()
      }
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = Round,
        strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(17.0f, 15.0f)
        curveTo(17.0f, 14.4477f, 17.4477f, 14.0f, 18.0f, 14.0f)
        horizontalLineTo(20.0f)
        curveTo(20.5523f, 14.0f, 21.0f, 14.4477f, 21.0f, 15.0f)
        verticalLineTo(21.0f)
        horizontalLineTo(17.0f)
        verticalLineTo(15.0f)
        close()
      }
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = Round,
        strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(9.0f, 11.5f)
        curveTo(9.0f, 10.6716f, 9.6716f, 10.0f, 10.5f, 10.0f)
        horizontalLineTo(13.5f)
        curveTo(14.3284f, 10.0f, 15.0f, 10.6716f, 15.0f, 11.5f)
        verticalLineTo(21.0f)
        horizontalLineTo(9.0f)
        verticalLineTo(11.5f)
        close()
      }
      path(
        fill = SolidColor(Color(0xFFFF8300)), stroke = SolidColor(Color(0xFFFF8300)),
        strokeLineWidth = 1.4f, strokeLineCap = Butt, strokeLineJoin = Round,
        strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(12.0f, 2.0f)
        lineTo(13.058f, 3.5438f)
        lineTo(14.8532f, 4.0729f)
        lineTo(13.7119f, 5.5562f)
        lineTo(13.7634f, 7.4271f)
        lineTo(12.0f, 6.8f)
        lineTo(10.2366f, 7.4271f)
        lineTo(10.2881f, 5.5562f)
        lineTo(9.1468f, 4.0729f)
        lineTo(10.942f, 3.5438f)
        lineTo(12.0f, 2.0f)
        close()
      }
    }
      .build()
    return _ranking!!
  }

private var _ranking: ImageVector? = null
