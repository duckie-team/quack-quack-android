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

public val OutlinedGroup.Share: ImageVector
  get() {
    if (_share != null) {
      return _share!!
    }
    _share = Builder(
      name = "Share", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(15.2961f, 8.123f)
        curveTo(16.256f, 8.123f, 17.0341f, 7.3449f, 17.0341f, 6.385f)
        curveTo(17.0341f, 5.4251f, 16.256f, 4.647f, 15.2961f, 4.647f)
        curveTo(14.3362f, 4.647f, 13.5581f, 5.4251f, 13.5581f, 6.385f)
        curveTo(13.5581f, 7.3449f, 14.3362f, 8.123f, 15.2961f, 8.123f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(15.2961f, 19.352f)
        curveTo(16.256f, 19.352f, 17.0341f, 18.5739f, 17.0341f, 17.614f)
        curveTo(17.0341f, 16.6541f, 16.256f, 15.876f, 15.2961f, 15.876f)
        curveTo(14.3362f, 15.876f, 13.5581f, 16.6541f, 13.5581f, 17.614f)
        curveTo(13.5581f, 18.5739f, 14.3362f, 19.352f, 15.2961f, 19.352f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(7.615f, 13.758f)
        curveTo(8.5748f, 13.758f, 9.353f, 12.9799f, 9.353f, 12.02f)
        curveTo(9.353f, 11.0601f, 8.5748f, 10.282f, 7.615f, 10.282f)
        curveTo(6.6551f, 10.282f, 5.8769f, 11.0601f, 5.8769f, 12.02f)
        curveTo(5.8769f, 12.9799f, 6.6551f, 13.758f, 7.615f, 13.758f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(9.01f, 10.985f)
        lineTo(13.856f, 7.36f)
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(13.852f, 16.647f)
        lineTo(9.01f, 13.061f)
      }
    }
      .build()
    return _share!!
  }

private var _share: ImageVector? = null
