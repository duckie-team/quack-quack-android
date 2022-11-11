/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
 * @param src 표시할 리소스. 만약 null 이 들어온다면 리소스를 그리지 않습니다.
 * @param size 리소스의 크기를 지정합니다. null 이 들어오면 기본 크기로 표시합니다.
 * @param tint 적용할 틴트 값
 * @param rippleEnabled 클릭됐을 때 ripple 발생 여부
 * @param onClick 클릭됐을 때 실행할 람다식
 * @param contentScale 적용할 content scale 정책
 * @param shape 리소스가 표시될 모양
 */
@Composable
public fun QuackImage(
    src: Any?,
    size: DpSize? = null,
    tint: QuackColor? = null,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    shape: Shape = RectangleShape,
): Unit = QuackImageInternal(
    modifier = Modifier
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
)

/**
 * [QuackImage] 를 실제로 그립니다
 * 내부에서 사용되는 컴포넌트이므로 [Modifier] 를 추가로 받습니다.
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param src 표시할 이미지의 리소스.
 * 만약 null 이 들어온다면 이미지를 그리지 않습니다.
 * @param size 리소스의 크기를 지정합니다. null 이 들어오면 기본 크기로 표시합니다.
 * @param tint 적용할 틴트 값
 * @param contentScale 적용할 content scale 정책
 */
// TODO: 로딩 및 로드 실패 인터렉션 (#301)
@Composable
internal fun QuackImageInternal(
    modifier: Modifier = Modifier,
    src: Any?,
    size: DpSize? = null,
    tint: QuackColor? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    if (src == null) return
    val density = LocalDensity.current
    val animatedTint by animateQuackColorAsState(
        targetValue = tint ?: QuackColor.Transparent,
    )
    if (src is QuackIcon) {
        QuackAnimatedContent(
            targetState = src,
            modifier = modifier,
        ) { imageModel ->
            Box(
                modifier = modifier
                    .runIf(
                        condition = size != null,
                    ) {
                        this.size(
                            size = size!! * density.fontScale,
                        )
                    }
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
        modifier = modifier,
    ) { imageModel ->
        AsyncImage(
            modifier = modifier.runIf(
                condition = size != null,
            ) {
                this.size(
                    size = size!! * density.fontScale,
                )
            },
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
                        size(
                            size = (size!! * density.density).width.roundToPx(),
                        )
                    }
                }
                .build(),
            colorFilter = animatedTint.toColorFilter(),
            contentDescription = null,
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
private fun QuackColor.toColorFilter() =
    if (this == QuackColor.Transparent) null
    else ColorFilter.tint(
        color = composeColor,
    )
