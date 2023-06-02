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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.quackicon.FilledGroup

public val FilledGroup.Search: ImageVector
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
        strokeLineWidth = 2.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(10.7269f, 17.753f)
        curveTo(14.6073f, 17.753f, 17.7529f, 14.6074f, 17.7529f, 10.727f)
        curveTo(17.7529f, 6.8467f, 14.6073f, 3.701f, 10.7269f, 3.701f)
        curveTo(6.8466f, 3.701f, 3.7009f, 6.8467f, 3.7009f, 10.727f)
        curveTo(3.7009f, 14.6074f, 6.8466f, 17.753f, 10.7269f, 17.753f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 2.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(15.696f, 15.696f)
        lineTo(20.299f, 20.299f)
      }
    }
      .build()
    return _search!!
  }

private var _search: ImageVector? = null
