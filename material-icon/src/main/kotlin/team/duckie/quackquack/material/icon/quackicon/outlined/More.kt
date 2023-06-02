/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon.quackicon.outlined

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

public val OutlinedGroup.More: ImageVector
  get() {
    if (_more != null) {
      return _more!!
    }
    _more = Builder(
      name = "More", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = NonZero
      ) {
        moveTo(13.221f, 6.015f)
        curveTo(13.221f, 6.2563f, 13.1495f, 6.4921f, 13.0155f, 6.6927f)
        curveTo(12.8815f, 6.8933f, 12.691f, 7.0497f, 12.4681f, 7.1421f)
        curveTo(12.2452f, 7.2344f, 12.0f, 7.2587f, 11.7634f, 7.2117f)
        curveTo(11.5267f, 7.1647f, 11.3093f, 7.0486f, 11.1387f, 6.8781f)
        curveTo(10.968f, 6.7076f, 10.8518f, 6.4903f, 10.8046f, 6.2537f)
        curveTo(10.7574f, 6.0171f, 10.7814f, 5.7718f, 10.8736f, 5.5489f)
        curveTo(10.9658f, 5.3259f, 11.122f, 5.1353f, 11.3225f, 5.0011f)
        curveTo(11.523f, 4.867f, 11.7588f, 4.7952f, 12.0f, 4.795f)
        curveTo(12.3235f, 4.7953f, 12.6336f, 4.9239f, 12.8624f, 5.1527f)
        curveTo(13.0911f, 5.3814f, 13.2197f, 5.6916f, 13.22f, 6.015f)
      }
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = NonZero
      ) {
        moveTo(13.221f, 12.0f)
        curveTo(13.221f, 12.2413f, 13.1495f, 12.4772f, 13.0154f, 12.6778f)
        curveTo(12.8813f, 12.8785f, 12.6908f, 13.0348f, 12.4679f, 13.1272f)
        curveTo(12.245f, 13.2195f, 11.9997f, 13.2437f, 11.763f, 13.1966f)
        curveTo(11.5263f, 13.1495f, 11.309f, 13.0333f, 11.1383f, 12.8627f)
        curveTo(10.9677f, 12.6921f, 10.8515f, 12.4747f, 10.8044f, 12.2381f)
        curveTo(10.7574f, 12.0014f, 10.7815f, 11.7561f, 10.8739f, 11.5332f)
        curveTo(10.9662f, 11.3102f, 11.1226f, 11.1197f, 11.3232f, 10.9857f)
        curveTo(11.5238f, 10.8516f, 11.7597f, 10.78f, 12.001f, 10.78f)
        curveTo(12.3245f, 10.7803f, 12.6346f, 10.9089f, 12.8634f, 11.1377f)
        curveTo(13.0921f, 11.3664f, 13.2207f, 11.6766f, 13.221f, 12.0f)
        close()
      }
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = NonZero
      ) {
        moveTo(13.221f, 17.985f)
        curveTo(13.221f, 18.2263f, 13.1495f, 18.4622f, 13.0154f, 18.6628f)
        curveTo(12.8813f, 18.8635f, 12.6908f, 19.0198f, 12.4679f, 19.1122f)
        curveTo(12.245f, 19.2045f, 11.9997f, 19.2287f, 11.763f, 19.1816f)
        curveTo(11.5263f, 19.1345f, 11.309f, 19.0183f, 11.1383f, 18.8477f)
        curveTo(10.9677f, 18.6771f, 10.8515f, 18.4597f, 10.8044f, 18.2231f)
        curveTo(10.7574f, 17.9864f, 10.7815f, 17.7411f, 10.8739f, 17.5182f)
        curveTo(10.9662f, 17.2952f, 11.1226f, 17.1047f, 11.3232f, 16.9707f)
        curveTo(11.5238f, 16.8366f, 11.7597f, 16.765f, 12.001f, 16.765f)
        curveTo(12.3245f, 16.7653f, 12.6346f, 16.8939f, 12.8634f, 17.1227f)
        curveTo(13.0921f, 17.3514f, 13.2207f, 17.6616f, 13.221f, 17.985f)
        close()
      }
    }
      .build()
    return _more!!
  }

private var _more: ImageVector? = null
