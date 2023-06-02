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
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup

public val OutlinedGroup.WriteFeed: ImageVector
  get() {
    if (_writeFeed != null) {
      return _writeFeed!!
    }
    _writeFeed = Builder(
      name = "WriteFeed", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.7f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(9.582f, 7.595f)
        horizontalLineTo(17.829f)
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.7f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(9.582f, 12.0f)
        horizontalLineTo(17.829f)
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.7f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(9.582f, 16.405f)
        horizontalLineTo(17.829f)
      }
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = NonZero
      ) {
        moveTo(7.147f, 7.595f)
        curveTo(7.1464f, 7.7752f, 7.0924f, 7.9512f, 6.9919f, 8.1008f)
        curveTo(6.8914f, 8.2503f, 6.7488f, 8.3667f, 6.5822f, 8.4353f)
        curveTo(6.4155f, 8.5039f, 6.2323f, 8.5216f, 6.0556f, 8.4862f)
        curveTo(5.8789f, 8.4507f, 5.7167f, 8.3637f, 5.5894f, 8.2362f)
        curveTo(5.4621f, 8.1086f, 5.3755f, 7.9462f, 5.3404f, 7.7694f)
        curveTo(5.3054f, 7.5927f, 5.3235f, 7.4095f, 5.3924f, 7.243f)
        curveTo(5.4614f, 7.0765f, 5.5781f, 6.9342f, 5.7279f, 6.834f)
        curveTo(5.8777f, 6.7338f, 6.0538f, 6.6802f, 6.234f, 6.68f)
        curveTo(6.4758f, 6.6803f, 6.7076f, 6.7764f, 6.8786f, 6.9474f)
        curveTo(7.0496f, 7.1184f, 7.1457f, 7.3502f, 7.146f, 7.592f)
      }
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = NonZero
      ) {
        moveTo(7.147f, 12.0f)
        curveTo(7.1472f, 12.1804f, 7.0939f, 12.3568f, 6.9939f, 12.5069f)
        curveTo(6.8938f, 12.6569f, 6.7515f, 12.774f, 6.5849f, 12.8432f)
        curveTo(6.4184f, 12.9124f, 6.235f, 12.9307f, 6.0581f, 12.8957f)
        curveTo(5.8811f, 12.8607f, 5.7185f, 12.774f, 5.5908f, 12.6466f)
        curveTo(5.4631f, 12.5192f, 5.3761f, 12.3568f, 5.3407f, 12.1799f)
        curveTo(5.3053f, 12.003f, 5.3232f, 11.8196f, 5.392f, 11.6529f)
        curveTo(5.4609f, 11.4862f, 5.5776f, 11.3436f, 5.7275f, 11.2433f)
        curveTo(5.8773f, 11.1429f, 6.0536f, 11.0892f, 6.234f, 11.089f)
        curveTo(6.4758f, 11.0893f, 6.7076f, 11.1854f, 6.8786f, 11.3564f)
        curveTo(7.0496f, 11.5274f, 7.1457f, 11.7592f, 7.146f, 12.001f)
      }
      path(
        fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = NonZero
      ) {
        moveTo(7.147f, 16.405f)
        curveTo(7.1478f, 16.5855f, 7.095f, 16.7622f, 6.9953f, 16.9127f)
        curveTo(6.8956f, 17.0631f, 6.7535f, 17.1807f, 6.587f, 17.2503f)
        curveTo(6.4205f, 17.32f, 6.2371f, 17.3387f, 6.0599f, 17.304f)
        curveTo(5.8828f, 17.2694f, 5.7199f, 17.1829f, 5.5919f, 17.0557f)
        curveTo(5.4639f, 16.9284f, 5.3766f, 16.766f, 5.3409f, 16.589f)
        curveTo(5.3053f, 16.4121f, 5.323f, 16.2285f, 5.3917f, 16.0616f)
        curveTo(5.4605f, 15.8947f, 5.5772f, 15.752f, 5.7272f, 15.6515f)
        curveTo(5.8771f, 15.551f, 6.0535f, 15.4972f, 6.234f, 15.497f)
        curveTo(6.4758f, 15.4973f, 6.7076f, 15.5934f, 6.8786f, 15.7644f)
        curveTo(7.0496f, 15.9354f, 7.1457f, 16.1672f, 7.146f, 16.409f)
      }
    }
      .build()
    return _writeFeed!!
  }

private var _writeFeed: ImageVector? = null
