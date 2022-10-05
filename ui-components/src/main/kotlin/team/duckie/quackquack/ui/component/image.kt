/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
private val QuackRoundImageShape = RoundedCornerShape(
    size = 24.dp,
)
private val QuackRoundImageSize = 72.dp

/**
 * 이미지 하나만 표시하는 컴포넌트의 아이콘 버전
 *
 * @param icon 표시할 아이콘의 drawable 아이디.
 * 만약 null 이 들어온다면 아이콘을 그리지 않습니다.
 * @param tint 아이콘에 적용할 틴트 값
 * @param rippleEnabled 이미지 클릭시 ripple 발생 여부
 * @param onClick 아이콘이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
fun QuackImage(
    icon: QuackIcon?,
    tint: QuackColor? = null,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    InternalQuackImage(
        icon = icon,
        tint = tint,
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    )
}

/**
 * 이미지 하나만 표시하는 컴포넌트의 이미지 버전
 *
 * @param image 표시할 이미지의 값.
 * 만약 null 이 들어온다면 이미지를 그리지 않습니다.
 * @param tint 아이콘에 적용할 틴트 값
 * @param rippleEnabled 이미지 클릭시 ripple 발생 여부
 * @param onClick 아이콘이 클릭됐을 때 실행할 람다식
 */
// TODO: 로딩 effect
@Composable
@NonRestartableComposable
fun QuackImage(
    image: Any?,
    tint: QuackColor? = null,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    InternalQuackImage(
        image = image,
        tint = tint,
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    )
}

/**
 * [QuackRoundImage] 를 구현합니다.
 *
 * 내부적으로 [InternalQuackImage] 를 사용하고
 * [Box]로 감싸 Shape만 관여합니다.
 *
 * @param image 표시할 이비지의 값
 * @param size 이미지의 사이즈 값
 */
// TODO: 로딩 effect
@Composable
fun QuackRoundImage(
    image: Any?,
    size: Dp = QuackRoundImageSize,
){
    Box(
        modifier = Modifier.size(
                size = size,
            ).clip(
                shape = QuackRoundImageShape,
        ),
    ){
        InternalQuackImage(
            image = image,
        )
    }
}

/**
 * 이미지 하나만 표시하는 컴포넌트의 아이콘 버전
 *
 * 모듈 내에서 [Modifier] 및 [tint] 변경하여 사용 가능
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param icon 표시할 아이콘의 drawable 아이디.
 * 만약 null 이 들어온다면 아이콘을 그리지 않습니다.
 * @param rippleEnabled 이미지 클릭시 ripple 발생 여부
 * @param tint 아이콘에 적용할 틴트 값
 * @param onClick 아이콘이 클릭됐을 때 실행할 람다식
 */
@Composable
@NonRestartableComposable
internal fun InternalQuackImage(
    modifier: Modifier = Modifier,
    icon: QuackIcon?,
    tint: QuackColor? = null,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    if (icon == null) return
    Image(
        modifier = modifier.quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        painter = painterResource(
            id = icon.drawableId,
        ),
        contentDescription = null,
        colorFilter = tint.toColorFilter(),
    )
}

/**
 * 이미지 하나만 표시하는 컴포넌트의 이미지 버전
 *
 * 모듈 내에서 [Modifier] 및 [tint] 변경하여 사용 가능
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param image 표시할 이미지의 값.
 * 만약 null 이 들어온다면 이미지를 그리지 않습니다.
 * @param rippleEnabled 이미지 클릭시 ripple 발생 여부
 * @param tint 아이콘에 적용할 틴트 값
 * @param onClick 아이콘이 클릭됐을 때 실행할 람다식
 */
// TODO: 로딩 effect
@Composable
@NonRestartableComposable
fun InternalQuackImage(
    modifier: Modifier = Modifier,
    image: Any?,
    rippleEnabled: Boolean = true,
    tint: QuackColor? = null,
    onClick: (() -> Unit)? = null,
) {
    if (image == null) return
    GlideImage(
        modifier = modifier.quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        imageModel = image,
        colorFilter = tint.toColorFilter(),
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
        color = value,
    )
}
