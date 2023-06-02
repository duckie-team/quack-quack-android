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

public val OutlinedGroup.Tag: ImageVector
  get() {
    if (_tag != null) {
      return _tag!!
    }
    _tag = Builder(
      name = "Tag", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth
      = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(12.339f, 4.374f)
        lineTo(4.399f, 12.315f)
        curveTo(4.2741f, 12.4397f, 4.175f, 12.5877f, 4.1073f, 12.7507f)
        curveTo(4.0397f, 12.9138f, 4.0049f, 13.0885f, 4.0049f, 13.265f)
        curveTo(4.0049f, 13.4415f, 4.0397f, 13.6162f, 4.1073f, 13.7793f)
        curveTo(4.175f, 13.9423f, 4.2741f, 14.0903f, 4.399f, 14.215f)
        lineTo(9.827f, 19.643f)
        curveTo(9.9515f, 19.7682f, 10.0996f, 19.8675f, 10.2626f, 19.9353f)
        curveTo(10.4256f, 20.0031f, 10.6004f, 20.038f, 10.777f, 20.038f)
        curveTo(10.9536f, 20.038f, 11.1284f, 20.0031f, 11.2914f, 19.9353f)
        curveTo(11.4545f, 19.8675f, 11.6025f, 19.7682f, 11.727f, 19.643f)
        lineTo(19.67f, 11.695f)
        curveTo(19.8019f, 11.5594f, 19.9052f, 11.3987f, 19.9739f, 11.2225f)
        curveTo(20.0426f, 11.0463f, 20.0753f, 10.8581f, 20.07f, 10.669f)
        lineTo(20.045f, 7.654f)
        lineTo(20.025f, 5.341f)
        curveTo(20.0183f, 4.9911f, 19.8762f, 4.6574f, 19.6285f, 4.4101f)
        curveTo(19.3809f, 4.1628f, 19.047f, 4.0212f, 18.697f, 4.015f)
        lineTo(16.385f, 3.991f)
        lineTo(13.375f, 3.965f)
        curveTo(13.1837f, 3.9603f, 12.9935f, 3.9941f, 12.8155f, 4.0643f)
        curveTo(12.6375f, 4.1346f, 12.4755f, 4.2399f, 12.339f, 4.374f)
        verticalLineTo(4.374f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = Miter,
        strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(15.473f, 10.052f)
        curveTo(16.2932f, 10.052f, 16.958f, 9.3871f, 16.958f, 8.567f)
        curveTo(16.958f, 7.7469f, 16.2932f, 7.082f, 15.473f, 7.082f)
        curveTo(14.6529f, 7.082f, 13.988f, 7.7469f, 13.988f, 8.567f)
        curveTo(13.988f, 9.3871f, 14.6529f, 10.052f, 15.473f, 10.052f)
        close()
      }
    }
      .build()
    return _tag!!
  }

private var _tag: ImageVector? = null
