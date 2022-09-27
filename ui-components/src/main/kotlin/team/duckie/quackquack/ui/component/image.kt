/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable

/**
 * 이미지 혹은 [QuackIcon] 을 표시합니다.
 *
 * @param src 표시할 리소스. 만약 null 이 들어온다면 리소스를 그리지 않습니다.
 * [GlideImage] 에서 지원하는 [Any] 에 추가로 [QuackIcon] 이 포함될 수 있습니다.
 * @param tint 아이콘에 적용할 틴트 값
 * @param rippleEnabled 이미지 클릭시 ripple 발생 여부
 * @param onClick 아이콘이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
fun QuackImage(
    src: Any?,
    tint: QuackColor? = null,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) = InternalQuackImage(
    modifier = Modifier.quackClickable(
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ),
    src = src,
    tint = tint,
)

/**
 * [QuackImage] 를 실제로 그립니다. 내부에서 사용되는 컴포넌트이므로
 * [Modifier] 를 추가로 받습니다.
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param src 표시할 이미지의 값.
 * 만약 null 이 들어온다면 이미지를 그리지 않습니다.
 * @param tint 아이콘에 적용할 틴트 값
 */
// TODO: 로딩 effect
@Composable
@NonRestartableComposable
private fun InternalQuackImage(
    modifier: Modifier = Modifier,
    src: Any?,
    tint: QuackColor? = null,
) {
    if (src == null) return
    val imageModel = if (src is QuackIcon) src.drawableId else src
    GlideImage(
        modifier = modifier,
        imageModel = imageModel,
        imageOptions = remember(
            key1 = tint,
        ) {
            ImageOptions(
                colorFilter = tint.toColorFilter(),
            )
        },
    )
}

/**
 * [QuackColor] 를 [ColorFilter] 로 변환합니다.
 *
 * @receiver 변환할 [QuackColor]
 * @return 변환된 [ColorFilter], 만약 receiver 로 null 이 들어왔다면 null 을 그대로 반환합니다.
 */
private fun QuackColor?.toColorFilter() = this?.run {
    ColorFilter.tint(
        color = composeColor,
    )
}
