/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon.quackicon.colored

import androidx.compose.ui.graphics.StrokeCap.Companion.Round as strokeCapRound
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round as strokeJoinRound
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.quackicon.ColoredGroup

public val ColoredGroup.Notice: ImageVector
  get() {
    if (_notice != null) {
      return _notice!!
    }
    _notice = Builder(
      name = "Notice", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0xFFFF8300)), stroke = SolidColor(Color(0xFFFF8300)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(12.0003f, 21.0f)
        curveTo(12.8395f, 20.9993f, 13.6463f, 20.6898f, 14.2545f, 20.1354f)
        curveTo(14.8627f, 19.5809f, 15.2259f, 18.8238f, 15.269f, 18.0201f)
        horizontalLineTo(8.7285f)
        curveTo(8.7722f, 18.8241f, 9.1359f, 19.5814f, 9.7447f, 20.1358f)
        curveTo(10.3534f, 20.6903f, 11.1608f, 20.9996f, 12.0003f, 21.0f)
        verticalLineTo(21.0f)
        close()
      }
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(12.0f, 2.0f)
        curveTo(10.9491f, 2.0022f, 10.0591f, 2.2031f, 9.0893f, 2.5914f)
        curveTo(8.1194f, 2.9796f, 7.239f, 3.5475f, 6.4983f, 4.2625f)
        curveTo(5.7576f, 4.9775f, 5.1713f, 5.8256f, 4.7729f, 6.7583f)
        curveTo(4.3745f, 7.6909f, 4.1719f, 8.6897f, 4.1766f, 9.6976f)
        verticalLineTo(16.6712f)
        curveTo(4.1774f, 17.0279f, 4.3257f, 17.3698f, 4.589f, 17.6217f)
        curveTo(4.8524f, 17.8737f, 5.2091f, 18.0152f, 5.5811f, 18.0152f)
        horizontalLineTo(18.419f)
        curveTo(18.6035f, 18.0153f, 18.7862f, 17.9806f, 18.9567f, 17.9129f)
        curveTo(19.1272f, 17.8453f, 19.282f, 17.7461f, 19.4125f, 17.621f)
        curveTo(19.5429f, 17.4959f, 19.6464f, 17.3473f, 19.7169f, 17.1839f)
        curveTo(19.7875f, 17.0204f, 19.8237f, 16.8452f, 19.8236f, 16.6683f)
        verticalLineTo(9.5007f)
        curveTo(19.8232f, 7.536f, 19.0192f, 5.6498f, 17.5845f, 4.2473f)
        curveTo(16.1497f, 2.8448f, 14.2353f, 2.0f, 12.0f, 2.0f)
        close()
      }
    }
      .build()
    return _notice!!
  }

private var _notice: ImageVector? = null
