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

public val OutlinedGroup.Search: ImageVector
  get() {
    if (_search != null) {
      return _search!!
    }
    _search = Builder(
      name = "Search", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.6f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(11.0f, 18.0f)
        curveTo(14.866f, 18.0f, 18.0f, 14.866f, 18.0f, 11.0f)
        curveTo(18.0f, 7.134f, 14.866f, 4.0f, 11.0f, 4.0f)
        curveTo(7.134f, 4.0f, 4.0f, 7.134f, 4.0f, 11.0f)
        curveTo(4.0f, 14.866f, 7.134f, 18.0f, 11.0f, 18.0f)
        close()
      }
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = NonZero
      ) {
        moveTo(17.5657f, 16.4343f)
        lineTo(17.0f, 15.8686f)
        lineTo(15.8686f, 17.0f)
        lineTo(16.4343f, 17.5657f)
        lineTo(17.5657f, 16.4343f)
        close()
        moveTo(18.4343f, 19.5657f)
        curveTo(18.7467f, 19.8781f, 19.2533f, 19.8781f, 19.5657f, 19.5657f)
        curveTo(19.8781f, 19.2533f, 19.8781f, 18.7467f, 19.5657f, 18.4343f)
        lineTo(18.4343f, 19.5657f)
        close()
        moveTo(16.4343f, 17.5657f)
        lineTo(18.4343f, 19.5657f)
        lineTo(19.5657f, 18.4343f)
        lineTo(17.5657f, 16.4343f)
        lineTo(16.4343f, 17.5657f)
        close()
      }
    }
      .build()
    return _search!!
  }

private var _search: ImageVector? = null
