/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.border.QuackBorder
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
): Unit = QuackImageInternal(
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
private fun QuackImageInternal(
    modifier: Modifier,
    src: Any?,
    size: DpSize?,
    tint: QuackColor?,
    contentScale: ContentScale,
    contentDescription: String?,
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

// TODO: 가장 작은 DelectionImage 가 커스텀 레이아웃 해야 함...
/**
 * QuackSelectableImage 를 그리는데 필요한 리소스를 구성합니다.
 */
private object QuackSelectableImageDefaults {
    object SelectableImage {
        val Margin = PaddingValues(
            all = 7.dp,
        )

        @Stable
        fun borderFor(
            isSelected: Boolean,
        ) = QuackBorder(
            color = QuackColor.Gray3,
        ).takeIf {
            isSelected
        }

        val Shape = RectangleShape
    }

    object Deletion {
        // TODO: margin

        val Icon = QuackIcon.Delete
        val IconSize = team.duckie.quackquack.ui.util.DpSize(
            all = 10.dp,
        )

        val IconContainerShape = CircleShape
        val IconContainerBackgroundColor = QuackColor.Black.change(
            alpha = 0.5f
        )
        val IconContainerSize = team.duckie.quackquack.ui.util.DpSize(
            all = 16.dp,
        )
    }
}

/**
 * 오른쪽 상단에 체크박스와 함께 이미지 혹은 [QuackIcon] 을 표시합니다.
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param isSelected 현재 이미지가 선택됐는지 여부
 * @param src 표시할 리소스. 만약 null 이 들어온다면 리소스를 그리지 않습니다.
 * @param size 리소스의 크기를 지정합니다. null 이 들어오면 기본 크기로 표시합니다.
 * @param tint 적용할 틴트 값
 * @param rippleEnabled 클릭됐을 때 ripple 발생 여부
 * @param onClick 클릭됐을 때 실행할 람다식
 * @param contentScale 적용할 content scale 정책
 * @param contentDescription 이미지의 설명
 */
@Composable
public fun QuackSelectableImage(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    src: Any?,
    size: DpSize? = null,
    tint: QuackColor? = null,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    contentDescription: String? = null,
): Unit = with(
    receiver = QuackSelectableImageDefaults.SelectableImage,
) {
    QuackSurface(
        modifier = modifier.wrapContentSize(),
        shape = Shape,
        border = borderFor(
            isSelected = isSelected,
        ),
        contentAlignment = Alignment.TopEnd,
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ) {
        QuackRoundCheckBox(
            modifier = Modifier.padding(
                paddingValues = Margin,
            ),
            checked = isSelected,
        )
        QuackImage(
            src = src,
            size = size,
            tint = tint,
            contentScale = contentScale,
            contentDescription = contentDescription,
        )
    }
}
