/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon.quackicon.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.quackicon.FilledGroup

public val FilledGroup.Message: ImageVector
  get() {
    if (_message != null) {
      return _message!!
    }
    _message = Builder(
      name = "Message", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = EvenOdd
      ) {
        moveTo(18.8426f, 2.3236f)
        curveTo(19.2381f, 2.1686f, 19.6702f, 2.1322f, 20.0861f, 2.2188f)
        curveTo(20.5022f, 2.3055f, 20.884f, 2.5116f, 21.1847f, 2.8119f)
        curveTo(21.4855f, 3.1121f, 21.6922f, 3.4936f, 21.7796f, 3.9095f)
        curveTo(21.8669f, 4.3254f, 21.8311f, 4.7578f, 21.6766f, 5.1537f)
        lineTo(21.6764f, 5.1542f)
        lineTo(15.6966f, 20.4418f)
        lineTo(15.6964f, 20.4422f)
        curveTo(15.5425f, 20.8363f, 15.2775f, 21.1772f, 14.9336f, 21.4236f)
        curveTo(14.5895f, 21.6701f, 14.1813f, 21.8115f, 13.7585f, 21.8305f)
        curveTo(13.3357f, 21.8495f, 12.9165f, 21.7454f, 12.5517f, 21.5308f)
        curveTo(12.187f, 21.3161f, 11.8923f, 21.0002f, 11.7036f, 20.6214f)
        lineTo(9.2852f, 15.7754f)
        lineTo(14.5303f, 10.5303f)
        curveTo(14.8232f, 10.2374f, 14.8232f, 9.7626f, 14.5303f, 9.4697f)
        curveTo(14.2374f, 9.1768f, 13.7625f, 9.1768f, 13.4696f, 9.4697f)
        lineTo(8.2251f, 14.7142f)
        lineTo(3.3803f, 12.2982f)
        curveTo(3.0015f, 12.1093f, 2.6857f, 11.8146f, 2.4712f, 11.4497f)
        curveTo(2.2567f, 11.0849f, 2.1527f, 10.6656f, 2.1719f, 10.2428f)
        curveTo(2.1911f, 9.82f, 2.3326f, 9.4118f, 2.5793f, 9.0679f)
        curveTo(2.8258f, 8.7241f, 3.1668f, 8.4592f, 3.5609f, 8.3054f)
        lineTo(3.5615f, 8.3052f)
        lineTo(18.8421f, 2.3238f)
        lineTo(18.8426f, 2.3236f)
        close()
      }
    }
      .build()
    return _message!!
  }

private var _message: ImageVector? = null
