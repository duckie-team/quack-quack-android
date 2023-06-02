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

public val OutlinedGroup.Notice: ImageVector
  get() {
    if (_notice != null) {
      return _notice!!
    }
    _notice = Builder(
      name = "Notice", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(12.0005f, 21.0f)
        curveTo(12.7955f, 20.9993f, 13.5599f, 20.7061f, 14.1361f, 20.1809f)
        curveTo(14.7122f, 19.6556f, 15.0563f, 18.9383f, 15.0972f, 18.1769f)
        horizontalLineTo(8.9009f)
        curveTo(8.9423f, 18.9386f, 9.2868f, 19.6561f, 9.8635f, 20.1813f)
        curveTo(10.4402f, 20.7066f, 11.2051f, 20.9996f, 12.0005f, 21.0f)
        verticalLineTo(21.0f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(12.0001f, 3.0f)
        curveTo(11.0045f, 3.0021f, 10.1614f, 3.1925f, 9.2426f, 3.5603f)
        curveTo(8.3238f, 3.9281f, 7.4896f, 4.4661f, 6.788f, 5.1434f)
        curveTo(6.0863f, 5.8208f, 5.5308f, 6.6243f, 5.1534f, 7.5078f)
        curveTo(4.776f, 8.3914f, 4.584f, 9.3376f, 4.5884f, 10.2924f)
        verticalLineTo(16.899f)
        curveTo(4.5892f, 17.237f, 4.7298f, 17.5608f, 4.9792f, 17.7995f)
        curveTo(5.2287f, 18.0382f, 5.5667f, 18.1723f, 5.9191f, 18.1723f)
        horizontalLineTo(18.0813f)
        curveTo(18.2561f, 18.1724f, 18.4292f, 18.1395f, 18.5907f, 18.0754f)
        curveTo(18.7521f, 18.0113f, 18.8989f, 17.9173f, 19.0225f, 17.7988f)
        curveTo(19.146f, 17.6803f, 19.2441f, 17.5396f, 19.3109f, 17.3847f)
        curveTo(19.3777f, 17.2298f, 19.412f, 17.0639f, 19.4119f, 16.8963f)
        verticalLineTo(10.1059f)
        curveTo(19.4115f, 8.2446f, 18.6499f, 6.4577f, 17.2906f, 5.129f)
        curveTo(15.9314f, 3.8004f, 14.1178f, 3.0f, 12.0001f, 3.0f)
        close()
      }
    }
      .build()
    return _notice!!
  }

private var _notice: ImageVector? = null
