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

public val OutlinedGroup.Comment: ImageVector
  get() {
    if (_comment != null) {
      return _comment!!
    }
    _comment = Builder(
      name = "Comment", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(17.4511f, 17.303f)
        curveTo(18.6682f, 16.0114f, 19.4026f, 14.3395f, 19.5304f, 12.5694f)
        curveTo(19.6583f, 10.7993f, 19.1717f, 9.0393f, 18.1527f, 7.5862f)
        curveTo(17.1338f, 6.1332f, 15.6449f, 5.076f, 13.9371f, 4.5931f)
        curveTo(12.2294f, 4.1101f, 10.4074f, 4.2309f, 8.7783f, 4.9351f)
        curveTo(7.1493f, 5.6393f, 5.813f, 6.8839f, 4.9949f, 8.4587f)
        curveTo(4.1767f, 10.0336f, 3.9268f, 11.8425f, 4.2872f, 13.5802f)
        curveTo(4.6476f, 15.3179f, 5.5963f, 16.8782f, 6.9734f, 17.9978f)
        curveTo(8.3504f, 19.1174f, 10.0714f, 19.7278f, 11.8461f, 19.726f)
        horizontalLineTo(19.8761f)
        lineTo(17.4511f, 17.303f)
        close()
      }
    }
      .build()
    return _comment!!
  }

private var _comment: ImageVector? = null
