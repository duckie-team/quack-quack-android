/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon.quackicon.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup

public val OutlinedGroup.Image: ImageVector
  get() {
    if (_image != null) {
      return _image!!
    }
    _image = Builder(
      name = "Image", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = EvenOdd
      ) {
        moveTo(3.8621f, 4.611f)
        curveTo(3.8621f, 4.1968f, 4.1979f, 3.861f, 4.6121f, 3.861f)
        horizontalLineTo(19.3881f)
        curveTo(19.8023f, 3.861f, 20.1381f, 4.1968f, 20.1381f, 4.611f)
        verticalLineTo(19.387f)
        curveTo(20.1381f, 19.8012f, 19.8023f, 20.137f, 19.3881f, 20.137f)
        horizontalLineTo(4.6121f)
        curveTo(4.1979f, 20.137f, 3.8621f, 19.8012f, 3.8621f, 19.387f)
        verticalLineTo(16.5871f)
        curveTo(3.8621f, 16.587f, 3.8621f, 16.587f, 3.8621f, 16.5869f)
        verticalLineTo(4.611f)
        close()
        moveTo(5.3621f, 16.8978f)
        verticalLineTo(18.637f)
        horizontalLineTo(18.6381f)
        verticalLineTo(16.8301f)
        lineTo(16.8519f, 14.2307f)
        lineTo(14.5964f, 15.9478f)
        curveTo(14.2787f, 16.1896f, 13.8274f, 16.1402f, 13.5695f, 15.8355f)
        lineTo(10.2959f, 11.9665f)
        lineTo(5.3621f, 16.8978f)
        close()
        moveTo(18.6381f, 14.1815f)
        verticalLineTo(5.361f)
        horizontalLineTo(5.3621f)
        verticalLineTo(14.777f)
        lineTo(9.8119f, 10.3295f)
        curveTo(9.9602f, 10.1813f, 10.1638f, 10.1019f, 10.3733f, 10.1107f)
        curveTo(10.5829f, 10.1194f, 10.7791f, 10.2155f, 10.9146f, 10.3755f)
        lineTo(14.2545f, 14.3228f)
        lineTo(16.5698f, 12.5602f)
        curveTo(16.7331f, 12.4359f, 16.9402f, 12.384f, 17.1428f, 12.4165f)
        curveTo(17.3455f, 12.449f, 17.526f, 12.5631f, 17.6422f, 12.7322f)
        lineTo(18.6381f, 14.1815f)
        close()
      }
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = NonZero
      ) {
        moveTo(16.3349f, 8.506f)
        curveTo(16.3351f, 8.7769f, 16.2549f, 9.0417f, 16.1046f, 9.2671f)
        curveTo(15.9542f, 9.4924f, 15.7403f, 9.668f, 15.49f, 9.7717f)
        curveTo(15.2398f, 9.8754f, 14.9644f, 9.9025f, 14.6987f, 9.8497f)
        curveTo(14.433f, 9.7968f, 14.189f, 9.6663f, 13.9975f, 9.4747f)
        curveTo(13.8061f, 9.2831f, 13.6757f, 9.0389f, 13.6231f, 8.7732f)
        curveTo(13.5704f, 8.5075f, 13.5977f, 8.2321f, 13.7016f, 7.9819f)
        curveTo(13.8055f, 7.7318f, 13.9813f, 7.5181f, 14.2067f, 7.3678f)
        curveTo(14.4321f, 7.2176f, 14.697f, 7.1376f, 14.9679f, 7.138f)
        curveTo(15.3307f, 7.138f, 15.6787f, 7.2821f, 15.9352f, 7.5387f)
        curveTo(16.1918f, 7.7952f, 16.3359f, 8.1432f, 16.3359f, 8.506f)
      }
    }
      .build()
    return _image!!
  }

private var _image: ImageVector? = null
