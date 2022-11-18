/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsState
import team.duckie.quackquack.ui.component.internal.QuackSurface
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.runIf

/**
 * QuackSelectableImage 를 그리는데 필요한 리소스를 구성합니다.
 */
private object QuackImageDefaults {
    object BadgeableImage {
        val Margin = PaddingValues(
            all = 8.dp,
        )
    }

    object SelectableImage {
        val Margin = PaddingValues(
            all = 7.dp,
        )

        /**
         * 주어진 조건에 맞는 테두리를 계산합니다.
         *
         * @param isSelected 현재 선택된 상태인지 여부
         *
         * @return [isSelected] 여부에 따라 사용할 [QuackBorder]
         */
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

    // TODO: 가장 작은 DelectionImage 가 커스텀 레이아웃을 필요로 함
    @Suppress("unused")
    object Deletion {
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
 * @param badge 리소스와 함께 표시할 배지
 * @param badgeSize 배지의 크기
 * @param badgeAlign 배지의 위치
 * @param contentDescription 이미지의 설명
 */
@Composable
public fun QuackImage(
    modifier: Modifier = Modifier,
    src: Any?,
    size: DpSize? = null,
    tint: QuackColor? = null,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    shape: Shape = RectangleShape,
    badge: (@Composable () -> Unit)? = null,
    badgeSize: DpSize? = null,
    badgeAlign: Alignment = Alignment.TopEnd,
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
    badge = badge,
    badgeSize = badgeSize,
    badgeAlign = badgeAlign,
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
 * @param badge 리소스와 함께 표시할 배지
 * @param badgeSize 배지의 크기
 * @param badgeAlign 배지의 위치
 * @param contentDescription 이미지의 설명
 */
// TODO: 로딩 및 로드 실패 인터렉션 (#301)
// FIXME: WrapContent 가 안됨 (#373)
@Composable
private fun QuackImageInternal(
    modifier: Modifier,
    src: Any?,
    size: DpSize?,
    tint: QuackColor?,
    contentScale: ContentScale,
    badge: (@Composable () -> Unit)?,
    badgeSize: DpSize?,
    badgeAlign: Alignment,
    contentDescription: String?,
) = with(
    receiver = QuackImageDefaults.BadgeableImage,
) {
    if (src == null) return
    val density = LocalDensity.current
    val animatedTint by animateQuackColorAsState(
        targetValue = tint ?: QuackColor.Transparent,
    )
    val imageModifier = remember(
        key1 = size,
        key2 = density,
    ) {
        when (size) {
            null -> Modifier.wrapContentSize()
            else -> Modifier.size(
                size = size * density.fontScale,
            )
        }.zIndex(
            zIndex = 1f,
        )
    }
    val badgeModifier = remember(
        key1 = badgeSize,
        key2 = density,
    ) {
        Modifier
            .padding(
                paddingValues = Margin,
            )
            .run {
                when (badgeSize) {
                    null -> wrapContentSize()
                    else -> size(
                        size = badgeSize * density.fontScale,
                    )
                }
            }
            .zIndex(
                zIndex = 2f,
            )
    }
    if (src is QuackIcon) {
        QuackAnimatedContent(
            targetState = src,
            modifier = imageModifier,
        ) { imageModel ->
            Box(
                modifier = imageModifier.paint(
                    painter = painterResource(
                        id = imageModel.drawableId,
                    ),
                    colorFilter = animatedTint.toColorFilter(),
                    contentScale = contentScale,
                ),
                contentAlignment = badgeAlign,
            ) {
                badge?.let {
                    QuackAnimatedContent(
                        modifier = badgeModifier,
                        targetState = badge,
                    ) { animatedBadge ->
                        animatedBadge()
                    }
                }
            }
        }
        return
    }
    QuackAnimatedContent(
        targetState = src,
        modifier = imageModifier,
    ) { imageModel ->
        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = badgeAlign,
        ) {
            AsyncImage(
                modifier = imageModifier,
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
                filterQuality = FilterQuality.High,
            )
            badge?.let {
                QuackAnimatedContent(
                    modifier = badgeModifier,
                    targetState = badge,
                ) { animatedBadge ->
                    animatedBadge()
                }
            }
        }
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
    receiver = QuackImageDefaults.SelectableImage,
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
