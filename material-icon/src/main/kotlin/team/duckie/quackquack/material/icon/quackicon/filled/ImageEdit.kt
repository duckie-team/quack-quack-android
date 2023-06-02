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

public val FilledGroup.ImageEdit: ImageVector
  get() {
    if (_imageEdit != null) {
      return _imageEdit!!
    }
    _imageEdit = Builder(
      name = "ImageEdit", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
      viewportWidth = 20.0f, viewportHeight = 20.0f
    ).apply {
      group {
        path(
          fill = SolidColor(Color(0xFF222222)), stroke = null, fillAlpha = 0.5f,
          strokeAlpha = 0.5f, strokeLineWidth = 0.0f, strokeLineCap = Butt,
          strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(20.0f, 10.0f)
          curveTo(20.0f, 4.4771f, 15.5228f, 0.0f, 10.0f, 0.0f)
          curveTo(4.4771f, 0.0f, 0.0f, 4.4771f, 0.0f, 10.0f)
          curveTo(0.0f, 15.5228f, 4.4771f, 20.0f, 10.0f, 20.0f)
          curveTo(15.5228f, 20.0f, 20.0f, 15.5228f, 20.0f, 10.0f)
          close()
        }
        path(
          fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
          strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
          strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(9.095f, 13.573f)
          verticalLineTo(11.927f)
        }
        path(
          fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
          strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
          strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(9.095f, 4.618f)
          verticalLineTo(6.264f)
        }
        path(
          fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
          strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
          strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(7.0929f, 11.097f)
          lineTo(5.9299f, 12.26f)
        }
        path(
          fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
          strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
          strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(12.2609f, 5.929f)
          lineTo(11.0979f, 7.092f)
        }
        path(
          fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
          strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
          strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(13.572f, 9.095f)
          horizontalLineTo(11.926f)
        }
        path(
          fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
          strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
          strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(6.2639f, 9.095f)
          horizontalLineTo(4.6179f)
        }
        path(
          fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
          strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
          strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(5.929f, 5.93f)
          lineTo(7.092f, 7.093f)
        }
        path(
          fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
          strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
          strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
          moveTo(11.0979f, 11.098f)
          lineTo(14.4999f, 14.501f)
        }
      }
    }
      .build()
    return _imageEdit!!
  }

private var _imageEdit: ImageVector? = null
