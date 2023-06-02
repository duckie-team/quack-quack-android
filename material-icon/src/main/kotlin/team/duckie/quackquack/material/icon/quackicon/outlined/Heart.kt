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

public val OutlinedGroup.Heart: ImageVector
  get() {
    if (_heart != null) {
      return _heart!!
    }
    _heart = Builder(
      name = "Heart", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(19.9996f, 9.3436f)
        verticalLineTo(9.3053f)
        curveTo(20.0012f, 8.7329f, 19.8944f, 8.165f, 19.6843f, 7.6295f)
        curveTo(19.4219f, 6.882f, 18.9285f, 6.2296f, 18.2701f, 5.7596f)
        curveTo(17.6117f, 5.2896f, 16.8198f, 5.0246f, 16.0005f, 5.0f)
        curveTo(14.9935f, 5.0468f, 14.0381f, 5.4429f, 13.311f, 6.1151f)
        curveTo(12.5839f, 6.7874f, 12.1342f, 7.6904f, 12.0451f, 8.657f)
        curveTo(12.0435f, 8.6674f, 12.0381f, 8.677f, 12.0298f, 8.6839f)
        curveTo(12.0215f, 8.6907f, 12.0109f, 8.6945f, 12.0f, 8.6945f)
        curveTo(11.9891f, 8.6945f, 11.9785f, 8.6907f, 11.9702f, 8.6839f)
        curveTo(11.9619f, 8.677f, 11.9565f, 8.6674f, 11.9549f, 8.657f)
        curveTo(11.8658f, 7.6905f, 11.4163f, 6.7877f, 10.6893f, 6.1154f)
        curveTo(9.9624f, 5.4432f, 9.0073f, 5.047f, 8.0004f, 5.0f)
        curveTo(7.1805f, 5.024f, 6.3878f, 5.2887f, 5.7287f, 5.7587f)
        curveTo(5.0696f, 6.2287f, 4.5755f, 6.8815f, 4.3128f, 7.6295f)
        curveTo(4.1044f, 8.1653f, 3.9985f, 8.7331f, 4.0003f, 9.3053f)
        verticalLineTo(9.3436f)
        curveTo(3.9968f, 9.5716f, 4.0209f, 9.7992f, 4.0722f, 10.0217f)
        curveTo(4.34f, 11.6611f, 5.5356f, 14.9482f, 11.0155f, 18.6445f)
        lineTo(11.0262f, 18.651f)
        curveTo(11.1534f, 18.7714f, 11.3056f, 18.8644f, 11.4729f, 18.924f)
        curveTo(11.6402f, 18.9836f, 11.8187f, 19.0083f, 11.9966f, 18.9966f)
        curveTo(12.1762f, 19.0095f, 12.3566f, 18.9853f, 12.5257f, 18.9255f)
        curveTo(12.6947f, 18.8658f, 12.8485f, 18.7718f, 12.9767f, 18.6501f)
        lineTo(12.9874f, 18.6435f)
        curveTo(18.4664f, 14.9491f, 19.6658f, 11.6611f, 19.9278f, 10.0255f)
        curveTo(19.9792f, 9.8016f, 20.0033f, 9.5728f, 19.9996f, 9.3436f)
        close()
      }
    }
      .build()
    return _heart!!
  }

private var _heart: ImageVector? = null
