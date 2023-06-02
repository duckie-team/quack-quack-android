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

public val OutlinedGroup.Message: ImageVector
  get() {
    if (_message != null) {
      return _message!!
    }
    _message = Builder(
      name = "Message", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(19.1159f, 3.022f)
        lineTo(3.834f, 9.004f)
        curveTo(3.5749f, 9.105f, 3.3508f, 9.279f, 3.1888f, 9.505f)
        curveTo(3.0267f, 9.7309f, 2.9337f, 9.999f, 2.9211f, 10.2768f)
        curveTo(2.9086f, 10.5545f, 2.9769f, 10.83f, 3.1178f, 11.0696f)
        curveTo(3.2587f, 11.3093f, 3.4661f, 11.5029f, 3.715f, 11.627f)
        lineTo(9.061f, 14.293f)
        curveTo(9.3396f, 14.4325f, 9.5655f, 14.6584f, 9.705f, 14.937f)
        lineTo(12.3749f, 20.287f)
        curveTo(12.4989f, 20.5359f, 12.6925f, 20.7434f, 12.9321f, 20.8844f)
        curveTo(13.1717f, 21.0254f, 13.4471f, 21.0938f, 13.7248f, 21.0813f)
        curveTo(14.0026f, 21.0688f, 14.2707f, 20.9759f, 14.4967f, 20.814f)
        curveTo(14.7227f, 20.652f, 14.8968f, 20.428f, 14.9979f, 20.169f)
        lineTo(20.9779f, 4.881f)
        curveTo(21.0795f, 4.6209f, 21.103f, 4.3369f, 21.0456f, 4.0637f)
        curveTo(20.9882f, 3.7905f, 20.8524f, 3.5399f, 20.6548f, 3.3426f)
        curveTo(20.4573f, 3.1454f, 20.2065f, 3.01f, 19.9332f, 2.9531f)
        curveTo(19.6598f, 2.8961f, 19.3759f, 2.9201f, 19.1159f, 3.022f)
        verticalLineTo(3.022f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(9.4919f, 14.507f)
        lineTo(13.6729f, 10.326f)
      }
    }
      .build()
    return _message!!
  }

private var _message: ImageVector? = null
