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

public val OutlinedGroup.NoticeAdd: ImageVector
  get() {
    if (_noticeAdd != null) {
      return _noticeAdd!!
    }
    _noticeAdd = Builder(
      name = "NoticeAdd", defaultWidth = 16.0.dp, defaultHeight = 16.0.dp,
      viewportWidth = 16.0f, viewportHeight = 16.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(7.6091f, 14.937f)
        curveTo(8.1647f, 14.9367f, 8.6989f, 14.7231f, 9.1016f, 14.3404f)
        curveTo(9.5043f, 13.9576f, 9.7446f, 13.4348f, 9.7731f, 12.88f)
        horizontalLineTo(5.4441f)
        curveTo(5.4728f, 13.4349f, 5.7134f, 13.9577f, 6.1162f, 14.3404f)
        curveTo(6.5191f, 14.7232f, 7.0534f, 14.9367f, 7.6091f, 14.937f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(7.2719f, 1.836f)
        curveTo(5.9478f, 1.9529f, 4.7158f, 2.5624f, 3.8194f, 3.544f)
        curveTo(2.923f, 4.5256f, 2.4275f, 5.8077f, 2.4309f, 7.137f)
        verticalLineTo(11.949f)
        curveTo(2.4309f, 12.1956f, 2.5289f, 12.4322f, 2.7033f, 12.6066f)
        curveTo(2.8777f, 12.781f, 3.1143f, 12.879f, 3.3609f, 12.879f)
        horizontalLineTo(11.8609f)
        curveTo(12.1076f, 12.879f, 12.3441f, 12.781f, 12.5185f, 12.6066f)
        curveTo(12.6929f, 12.4322f, 12.7909f, 12.1956f, 12.7909f, 11.949f)
        verticalLineTo(7.355f)
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(11.4021f, 1.063f)
        verticalLineTo(5.392f)
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(13.567f, 3.228f)
        horizontalLineTo(9.238f)
      }
    }
      .build()
    return _noticeAdd!!
  }

private var _noticeAdd: ImageVector? = null
