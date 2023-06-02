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

public val OutlinedGroup.Buy: ImageVector
  get() {
    if (_buy != null) {
      return _buy!!
    }
    _buy = Builder(
      name = "Buy", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth
      = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(17.0399f, 19.325f)
        horizontalLineTo(6.9609f)
        curveTo(6.7959f, 19.3244f, 6.6329f, 19.2898f, 6.4819f, 19.2233f)
        curveTo(6.3309f, 19.1568f, 6.1953f, 19.06f, 6.0834f, 18.9387f)
        curveTo(5.9715f, 18.8175f, 5.8859f, 18.6745f, 5.8318f, 18.5186f)
        curveTo(5.7777f, 18.3628f, 5.7563f, 18.1975f, 5.7689f, 18.033f)
        lineTo(6.5149f, 8.693f)
        curveTo(6.5399f, 8.3938f, 6.6762f, 8.1148f, 6.8969f, 7.9111f)
        curveTo(7.1176f, 7.7075f, 7.4066f, 7.594f, 7.7069f, 7.593f)
        horizontalLineTo(16.2939f)
        curveTo(16.5942f, 7.594f, 16.8832f, 7.7075f, 17.1039f, 7.9111f)
        curveTo(17.3246f, 8.1148f, 17.4609f, 8.3938f, 17.4859f, 8.693f)
        lineTo(18.2319f, 18.033f)
        curveTo(18.2446f, 18.1975f, 18.2232f, 18.3628f, 18.1691f, 18.5186f)
        curveTo(18.1149f, 18.6745f, 18.0293f, 18.8175f, 17.9174f, 18.9387f)
        curveTo(17.8056f, 19.06f, 17.6699f, 19.1568f, 17.5189f, 19.2233f)
        curveTo(17.3679f, 19.2898f, 17.2049f, 19.3244f, 17.0399f, 19.325f)
        verticalLineTo(19.325f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(10.1289f, 9.828f)
        verticalLineTo(5.764f)
        curveTo(10.1232f, 5.5151f, 10.1672f, 5.2676f, 10.2585f, 5.036f)
        curveTo(10.3497f, 4.8044f, 10.4864f, 4.5933f, 10.6604f, 4.4153f)
        curveTo(10.8343f, 4.2372f, 11.0422f, 4.0957f, 11.2716f, 3.9991f)
        curveTo(11.501f, 3.9025f, 11.7475f, 3.8528f, 11.9964f, 3.8528f)
        curveTo(12.2454f, 3.8528f, 12.4918f, 3.9025f, 12.7212f, 3.9991f)
        curveTo(12.9507f, 4.0957f, 13.1585f, 4.2372f, 13.3325f, 4.4153f)
        curveTo(13.5065f, 4.5933f, 13.6431f, 4.8044f, 13.7344f, 5.036f)
        curveTo(13.8256f, 5.2676f, 13.8697f, 5.5151f, 13.8639f, 5.764f)
        verticalLineTo(9.828f)
      }
    }
      .build()
    return _buy!!
  }

private var _buy: ImageVector? = null
