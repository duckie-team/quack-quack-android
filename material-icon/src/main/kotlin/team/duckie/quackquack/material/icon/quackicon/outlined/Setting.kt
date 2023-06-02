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

public val OutlinedGroup.Setting: ImageVector
  get() {
    if (_setting != null) {
      return _setting!!
    }
    _setting = Builder(
      name = "Setting", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
      viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(19.1025f, 16.4428f)
        lineTo(18.4288f, 15.5019f)
        curveTo(18.3373f, 15.3741f, 18.2798f, 15.2251f, 18.2617f, 15.0689f)
        curveTo(18.2437f, 14.9127f, 18.2657f, 14.7545f, 18.3256f, 14.6092f)
        curveTo(18.3856f, 14.4639f, 18.4816f, 14.3362f, 18.6045f, 14.2382f)
        curveTo(18.7275f, 14.1402f, 18.8733f, 14.0751f, 19.0284f, 14.0491f)
        lineTo(20.1691f, 13.8548f)
        curveTo(20.3659f, 13.8219f, 20.5468f, 13.7265f, 20.6851f, 13.5828f)
        curveTo(20.8235f, 13.439f, 20.9118f, 13.2545f, 20.9371f, 13.0566f)
        curveTo(20.9788f, 12.7059f, 20.9998f, 12.3532f, 20.9999f, 12.0001f)
        curveTo(20.9993f, 11.6383f, 20.9772f, 11.2769f, 20.9337f, 10.9177f)
        curveTo(20.9087f, 10.7194f, 20.8201f, 10.5344f, 20.6813f, 10.3906f)
        curveTo(20.5425f, 10.2467f, 20.3608f, 10.1516f, 20.1635f, 10.1195f)
        lineTo(19.0216f, 9.9297f)
        curveTo(18.8666f, 9.9041f, 18.7206f, 9.8395f, 18.5974f, 9.7419f)
        curveTo(18.4742f, 9.6443f, 18.3779f, 9.517f, 18.3175f, 9.3719f)
        curveTo(18.257f, 9.2268f, 18.2345f, 9.0687f, 18.2521f, 8.9125f)
        curveTo(18.2696f, 8.7564f, 18.3265f, 8.6072f, 18.4176f, 8.4791f)
        lineTo(19.0913f, 7.536f)
        curveTo(19.2088f, 7.371f, 19.2694f, 7.1721f, 19.264f, 6.9696f)
        curveTo(19.2585f, 6.767f, 19.1872f, 6.5717f, 19.0609f, 6.4133f)
        curveTo(18.6206f, 5.8596f, 18.1168f, 5.3596f, 17.5598f, 4.9234f)
        curveTo(17.4009f, 4.7978f, 17.2053f, 4.7273f, 17.0028f, 4.7226f)
        curveTo(16.8002f, 4.718f, 16.6017f, 4.7794f, 16.4371f, 4.8976f)
        lineTo(15.4962f, 5.5712f)
        curveTo(15.3684f, 5.6628f, 15.2194f, 5.7203f, 15.0632f, 5.7383f)
        curveTo(14.907f, 5.7564f, 14.7488f, 5.7344f, 14.6035f, 5.6744f)
        curveTo(14.4582f, 5.6144f, 14.3305f, 5.5185f, 14.2325f, 5.3955f)
        curveTo(14.1345f, 5.2726f, 14.0694f, 5.1267f, 14.0434f, 4.9717f)
        lineTo(13.8536f, 3.8298f)
        curveTo(13.8211f, 3.6329f, 13.7258f, 3.4518f, 13.582f, 3.3134f)
        curveTo(13.4381f, 3.175f, 13.2534f, 3.0868f, 13.0554f, 3.0619f)
        curveTo(12.7051f, 3.0201f, 12.3527f, 2.9992f, 12.0f, 2.999f)
        curveTo(11.6367f, 2.9987f, 11.2737f, 3.0208f, 10.9132f, 3.0652f)
        curveTo(10.7147f, 3.0901f, 10.5297f, 3.1786f, 10.3858f, 3.3174f)
        curveTo(10.2419f, 3.4563f, 10.1468f, 3.638f, 10.1149f, 3.8354f)
        lineTo(9.9285f, 4.9773f)
        curveTo(9.9029f, 5.1323f, 9.8383f, 5.2783f, 9.7407f, 5.4015f)
        curveTo(9.6431f, 5.5247f, 9.5158f, 5.6211f, 9.3707f, 5.6815f)
        curveTo(9.2256f, 5.7419f, 9.0675f, 5.7644f, 8.9114f, 5.7469f)
        curveTo(8.7552f, 5.7294f, 8.606f, 5.6724f, 8.4779f, 5.5813f)
        lineTo(7.5337f, 4.9077f)
        curveTo(7.3686f, 4.7901f, 7.1698f, 4.7295f, 6.9673f, 4.735f)
        curveTo(6.7647f, 4.7404f, 6.5694f, 4.8117f, 6.411f, 4.938f)
        curveTo(5.858f, 5.3791f, 5.3583f, 5.8833f, 4.9222f, 6.4402f)
        curveTo(4.7971f, 6.5994f, 4.7269f, 6.7949f, 4.7223f, 6.9973f)
        curveTo(4.7176f, 7.1997f, 4.7787f, 7.3982f, 4.8964f, 7.563f)
        lineTo(5.57f, 8.5038f)
        curveTo(5.6616f, 8.6316f, 5.7191f, 8.7805f, 5.7372f, 8.9366f)
        curveTo(5.7553f, 9.0927f, 5.7334f, 9.2508f, 5.6736f, 9.3961f)
        curveTo(5.6137f, 9.5414f, 5.5179f, 9.6691f, 5.3951f, 9.7672f)
        curveTo(5.2723f, 9.8652f, 5.1265f, 9.9305f, 4.9716f, 9.9567f)
        lineTo(3.8286f, 10.1464f)
        curveTo(3.6318f, 10.1788f, 3.4507f, 10.2741f, 3.3125f, 10.418f)
        curveTo(3.1742f, 10.5619f, 3.0863f, 10.7467f, 3.0618f, 10.9447f)
        curveTo(3.019f, 11.2948f, 2.9976f, 11.6473f, 2.9978f, 12.0001f)
        curveTo(2.9984f, 12.3618f, 3.0205f, 12.7232f, 3.064f, 13.0824f)
        curveTo(3.0891f, 13.2807f, 3.1776f, 13.4657f, 3.3164f, 13.6096f)
        curveTo(3.4553f, 13.7534f, 3.6369f, 13.8485f, 3.8342f, 13.8807f)
        lineTo(4.9772f, 14.0704f)
        curveTo(5.1316f, 14.0966f, 5.2768f, 14.1614f, 5.3993f, 14.2588f)
        curveTo(5.5219f, 14.3562f, 5.6178f, 14.4831f, 5.6781f, 14.6275f)
        curveTo(5.7384f, 14.772f, 5.7611f, 14.9294f, 5.7442f, 15.085f)
        curveTo(5.7273f, 15.2407f, 5.6712f, 15.3895f, 5.5812f, 15.5176f)
        lineTo(4.9076f, 16.4619f)
        curveTo(4.7902f, 16.627f, 4.7298f, 16.8259f, 4.7354f, 17.0285f)
        curveTo(4.7411f, 17.2311f, 4.8126f, 17.4263f, 4.939f, 17.5846f)
        curveTo(5.3785f, 18.1386f, 5.8812f, 18.6393f, 6.4368f, 19.0767f)
        curveTo(6.596f, 19.2018f, 6.7914f, 19.272f, 6.9939f, 19.2766f)
        curveTo(7.1963f, 19.2813f, 7.3948f, 19.2202f, 7.5595f, 19.1026f)
        lineTo(8.5004f, 18.4289f)
        curveTo(8.6281f, 18.3374f, 8.7771f, 18.2798f, 8.9332f, 18.2617f)
        curveTo(9.0892f, 18.2436f, 9.2474f, 18.2655f, 9.3927f, 18.3254f)
        curveTo(9.538f, 18.3852f, 9.6657f, 18.481f, 9.7638f, 18.6038f)
        curveTo(9.8618f, 18.7266f, 9.927f, 18.8724f, 9.9532f, 19.0273f)
        lineTo(10.1475f, 20.1692f)
        curveTo(10.1801f, 20.366f, 10.2754f, 20.547f, 10.4193f, 20.6851f)
        curveTo(10.5631f, 20.8233f, 10.7478f, 20.9113f, 10.9457f, 20.936f)
        curveTo(11.2963f, 20.9788f, 11.6491f, 21.0001f, 12.0022f, 21.0f)
        curveTo(12.364f, 20.9993f, 12.7254f, 20.9772f, 13.0846f, 20.9338f)
        curveTo(13.2827f, 20.9082f, 13.4674f, 20.8195f, 13.6112f, 20.6808f)
        curveTo(13.7549f, 20.542f, 13.8502f, 20.3607f, 13.8828f, 20.1636f)
        lineTo(14.0726f, 19.0206f)
        curveTo(14.0985f, 18.8657f, 14.1633f, 18.7199f, 14.2609f, 18.5969f)
        curveTo(14.3585f, 18.4739f, 14.4858f, 18.3777f, 14.6308f, 18.3173f)
        curveTo(14.7758f, 18.2569f, 14.9337f, 18.2344f, 15.0898f, 18.2517f)
        curveTo(15.2459f, 18.2691f, 15.395f, 18.3258f, 15.5232f, 18.4166f)
        lineTo(16.4663f, 19.0902f)
        curveTo(16.6313f, 19.2078f, 16.8302f, 19.2684f, 17.0327f, 19.2629f)
        curveTo(17.2353f, 19.2574f, 17.4306f, 19.1862f, 17.589f, 19.0599f)
        curveTo(18.1436f, 18.6211f, 18.6445f, 18.1184f, 19.0812f, 17.5621f)
        curveTo(19.2053f, 17.403f, 19.2745f, 17.2078f, 19.2784f, 17.006f)
        curveTo(19.2822f, 16.8042f, 19.2205f, 16.6065f, 19.1025f, 16.4428f)
        verticalLineTo(16.4428f)
        close()
      }
      path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
        strokeLineWidth = 1.5f, strokeLineCap = strokeCapRound, strokeLineJoin =
        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero
      ) {
        moveTo(16.0892f, 12.0001f)
        curveTo(16.089f, 12.8085f, 15.849f, 13.5987f, 15.3998f, 14.2708f)
        curveTo(14.9505f, 14.9429f, 14.312f, 15.4666f, 13.5651f, 15.7759f)
        curveTo(12.8182f, 16.0852f, 11.9963f, 16.166f, 11.2035f, 16.0083f)
        curveTo(10.4106f, 15.8505f, 9.6823f, 15.4612f, 9.1107f, 14.8895f)
        curveTo(8.539f, 14.3179f, 8.1497f, 13.5896f, 7.9919f, 12.7967f)
        curveTo(7.8342f, 12.0039f, 7.915f, 11.182f, 8.2243f, 10.4351f)
        curveTo(8.5336f, 9.6882f, 9.0573f, 9.0497f, 9.7294f, 8.6004f)
        curveTo(10.4015f, 8.1511f, 11.1917f, 7.9112f, 12.0001f, 7.911f)
        curveTo(13.0846f, 7.911f, 14.1247f, 8.3418f, 14.8915f, 9.1087f)
        curveTo(15.6584f, 9.8755f, 16.0892f, 10.9156f, 16.0892f, 12.0001f)
        close()
      }
    }
      .build()
    return _setting!!
  }

private var _setting: ImageVector? = null
