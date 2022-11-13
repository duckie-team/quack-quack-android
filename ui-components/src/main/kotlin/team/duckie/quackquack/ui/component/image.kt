/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import coil.compose.AsyncImage
import coil.request.ImageRequest
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsState
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.runIf

/**
 * 이미지 혹은 [QuackIcon] 을 표시합니다.
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param src 표시할 리소스. 만약 null 이 들어온다면 리소스를 그리지 않습니다.
 * @param size 리소스의 크기를 지정합니다. null 이 들어오면 기본 크기로 표시합니다.
 * @param tint 적용할 틴트 값
 * @param rippleEnabled 클릭됐을 때 ripple 발생 여부
 * @param onClick 클릭됐을 때 실행할 람다식
 * @param contentScale 적용할 content scale 정책
 * @param shape 리소스가 표시될 모양
 * @param contentDescription 이미지의 설명
 */
@Composable
@NonRestartableComposable
public fun QuackImage(
    modifier: Modifier = Modifier,
    src: Any?,
    size: DpSize? = null,
    tint: QuackColor? = null,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    shape: Shape = RectangleShape,
    contentDescription: String? = null,
): Unit = QuackImageImpl(
    modifier = modifier
        .clip(
            shape = shape,
        )
        .quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
    src = src,
    size = size,
    tint = tint,
    contentScale = contentScale,
    contentDescription = contentDescription,
)

/**
 * [QuackImage] 를 실제로 그립니다
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param src 표시할 이미지의 리소스.
 * 만약 null 이 들어온다면 이미지를 그리지 않습니다.
 * @param size 리소스의 크기를 지정합니다. null 이 들어오면 기본 크기로 표시합니다.
 * @param tint 적용할 틴트 값
 * @param contentScale 적용할 content scale 정책
 * @param contentDescription 이미지의 설명
 */
// TODO: 로딩 및 로드 실패 인터렉션 (#301)
@Composable
private fun QuackImageImpl(
    modifier: Modifier = Modifier,
    src: Any?,
    size: DpSize? = null,
    tint: QuackColor? = null,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
) {
    if (src == null) return
    val density = LocalDensity.current
    val animatedTint by animateQuackColorAsState(
        targetValue = tint ?: QuackColor.Transparent,
    )
    val sizedModifier = remember(
        key1 = size,
        key2 = density,
    ) {
        modifier.runIf(
            condition = size != null,
        ) {
            this.size(
                size = size!! * density.fontScale,
            )
        }
    }
    if (src is QuackIcon) {
        QuackAnimatedContent(
            targetState = src,
            modifier = sizedModifier,
        ) { imageModel ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painter = painterResource(
                            id = imageModel.drawableId,
                        ),
                        colorFilter = animatedTint.toColorFilter(),
                        contentScale = contentScale,
                    )
            )
        }
        return
    }
    QuackAnimatedContent(
        targetState = src,
        modifier = sizedModifier,
    ) { imageModel ->
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest
                .Builder(
                    context = LocalContext.current,
                )
                .data(
                    data = imageModel,
                )
                .runIf(
                    condition = size != null,
                ) {
                    with(
                        receiver = density,
                    ) {
                        val densitiedSize = size!! * density.fontScale
                        size(
                            width = densitiedSize.width.roundToPx(),
                            height = densitiedSize.height.roundToPx(),
                        )
                    }
                }
                .build(),
            colorFilter = animatedTint.toColorFilter(),
            contentDescription = contentDescription,
            contentScale = contentScale,
        )
    }
}

/**
 * [QuackColor] 를 [ColorFilter] 로 변환합니다.
 *
 * @receiver 변환할 [QuackColor]
 *
 * @return 변환된 [ColorFilter].
 * 만약 [QuackColor] 가 [QuackColor.Transparent] 이라면 null 을 반환합니다.
 */
private fun QuackColor.toColorFilter(): ColorFilter? {
    return if (this == QuackColor.Transparent || this == QuackColor.Unspecified) {
        null
    } else {
        ColorFilter.tint(
            color = composeColor,
        )
    }
}
