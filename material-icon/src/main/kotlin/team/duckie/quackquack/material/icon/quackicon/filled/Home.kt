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

public val FilledGroup.Home: ImageVector
  get() {
    if (_home != null) {
      return _home!!
    }
    _home = Builder(
      name = "Home", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(19.9801f, 8.108f)
        lineTo(13.8761f, 3.135f)
        curveTo(13.3413f, 2.744f, 12.696f, 2.5333f, 12.0336f, 2.5333f)
        curveTo(11.3711f, 2.5333f, 10.7259f, 2.744f, 10.1911f, 3.135f)
        lineTo(4.0111f, 8.201f)
        curveTo(3.6263f, 8.4834f, 3.3133f, 8.8523f, 3.0972f, 9.2778f)
        curveTo(2.8812f, 9.7034f, 2.7681f, 10.1737f, 2.7671f, 10.651f)
        verticalLineTo(18.375f)
        curveTo(2.7696f, 18.7785f, 2.8515f, 19.1776f, 3.0083f, 19.5494f)
        curveTo(3.165f, 19.9212f, 3.3935f, 20.2585f, 3.6806f, 20.542f)
        curveTo(3.9678f, 20.8255f, 4.3079f, 21.0497f, 4.6817f, 21.2017f)
        curveTo(5.0555f, 21.3537f, 5.4556f, 21.4306f, 5.8591f, 21.428f)
        horizontalLineTo(7.5101f)
        curveTo(7.7132f, 21.4281f, 7.9142f, 21.3882f, 8.1019f, 21.3106f)
        curveTo(8.2895f, 21.2329f, 8.46f, 21.1191f, 8.6036f, 20.9755f)
        curveTo(8.7472f, 20.8319f, 8.861f, 20.6614f, 8.9387f, 20.4738f)
        curveTo(9.0163f, 20.2861f, 9.0562f, 20.0851f, 9.0561f, 19.882f)
        verticalLineTo(16.271f)
        curveTo(9.0561f, 15.774f, 9.2535f, 15.2973f, 9.605f, 14.9459f)
        curveTo(9.9564f, 14.5944f, 10.4331f, 14.397f, 10.9301f, 14.397f)
        horizontalLineTo(13.0671f)
        curveTo(13.3131f, 14.3971f, 13.5566f, 14.4457f, 13.7838f, 14.5399f)
        curveTo(14.011f, 14.6342f, 14.2174f, 14.7723f, 14.3912f, 14.9463f)
        curveTo(14.565f, 15.1203f, 14.7029f, 15.3269f, 14.7969f, 15.5541f)
        curveTo(14.8909f, 15.7814f, 14.9392f, 16.025f, 14.9391f, 16.271f)
        verticalLineTo(19.882f)
        curveTo(14.9391f, 20.292f, 15.102f, 20.6852f, 15.3919f, 20.9752f)
        curveTo(15.6818f, 21.2651f, 16.0751f, 21.428f, 16.4851f, 21.428f)
        horizontalLineTo(18.1381f)
        curveTo(18.9529f, 21.433f, 19.7364f, 21.1143f, 20.3163f, 20.5417f)
        curveTo(20.8961f, 19.9692f, 21.2248f, 19.1898f, 21.2301f, 18.375f)
        verticalLineTo(10.563f)
        curveTo(21.2295f, 10.0843f, 21.116f, 9.6125f, 20.8988f, 9.1859f)
        curveTo(20.6816f, 8.7594f, 20.3669f, 8.39f, 19.9801f, 8.108f)
        close()
      }
    }
      .build()
    return _home!!
  }

private var _home: ImageVector? = null
