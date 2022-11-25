/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsState
import team.duckie.quackquack.ui.component.QuackSelectableImageType.CheckOverlay
import team.duckie.quackquack.ui.component.QuackSelectableImageType.TopEndCheckBox
import team.duckie.quackquack.ui.component.internal.QuackSurface
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.DpSize
import team.duckie.quackquack.ui.util.runIf
import team.duckie.quackquack.ui.util.runtimeCheck

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
        val IconSize = DpSize(
            all = 10.dp,
        )

        val IconContainerShape = CircleShape
        val IconContainerBackgroundColor = QuackColor.Black.change(
            alpha = 0.5f
        )
        val IconContainerSize = DpSize(
            all = 16.dp,
        )
    }
}

/**
 * 이미지 혹은 [QuackIcon] 을 표시합니다.
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param src 표시할 리소스. 만약 null 이 들어온다면 리소스를 그리지 않습니다.
 * @param size 리소스의 크기를 지정합니다. null 이 들어오면 기본 크기로 표시합니다.
 * @param tint 적용할 틴트 값
 * @param rippleEnabled 클릭됐을 때 ripple 발생 여부
 * @param onClick 클릭됐을 때 실행할 람다식
 * @param onLongClick 길게 클릭됐을 때 실행할 람다식.
 * 이 값이 제공되면 [onClick] 값이 필수로 제공돼야 합니다.
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
    padding: PaddingValues? = null,
    src: Any?,
    size: DpSize? = null,
    tint: QuackColor? = null,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    shape: Shape = RectangleShape,
    badge: (@Composable () -> Unit)? = null,
    badgeSize: DpSize? = null,
    badgeAlign: Alignment = Alignment.TopEnd,
    contentDescription: String? = null,
) {
    if (onLongClick != null) {
        runtimeCheck(
            value = onClick != null,
        ) {
            "onLongClick 값이 제공되면 onClick 값이 필수로 제공돼야 합니다."
        }
    }

    QuackImageInternal(
        modifier = modifier
            .clip(
                shape = shape,
            )
            .quackClickable(
                rippleEnabled = rippleEnabled,
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .runIf(
                condition = padding != null,
            ) {
                padding(
                    paddingValues = padding!!,
                )
            },
        src = src,
        size = size,
        tint = tint,
        contentScale = contentScale,
        badge = badge,
        badgeSize = badgeSize,
        badgeAlign = badgeAlign,
        contentDescription = contentDescription,
    )
}

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
    val sizedModifier = remember(
        key1 = size,
        key2 = density,
    ) {
        when (size) {
            null -> Modifier
            else -> Modifier.size(
                size = size * density.fontScale,
            )
        }.zIndex(
            zIndex = 1f,
        )
    }
    val containerModifier = remember(
        key1 = modifier,
        key2 = sizedModifier,
    ) {
        modifier.then(
            other = sizedModifier,
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
            .runIf(
                condition = badgeSize != null,
            ) {
                size(
                    size = badgeSize!! * density.fontScale,
                )
            }
            .zIndex(
                zIndex = 2f,
            )
    }
    if (src is QuackIcon) {
        QuackAnimatedContent(
            targetState = src,
            modifier = containerModifier,
        ) { imageModel ->
            Box(
                modifier = sizedModifier.paint(
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
        modifier = containerModifier,
    ) { imageModel ->
        Box(
            contentAlignment = badgeAlign,
        ) {
            AsyncImage(
                modifier = sizedModifier,
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
 * [QuackSelectableImage] 에서 selection 이 표시될 방식을 나타냅니다.
 *
 * @property TopEndCheckBox 오른쪽 상단에 [QuackRoundCheckBox] 로 표시
 * @property CheckOverlay 이미지 전체에 [QuackIcon.Check] 로 오버레이 표시 및
 * [QuackColor.Dimmed] 로 dimmed 처리
 *
 * @param size 만약 [CheckOverlay] 방식일 때 [QuackIcon.Check] 의 사이즈
 * @param tint 만약 [CheckOverlay] 방식일 때 [QuackIcon.Check] 의 틴트
 */
public sealed class QuackSelectableImageType(
    internal val size: DpSize? = null,
    internal val tint: QuackColor? = null,
) {
    public object TopEndCheckBox : QuackSelectableImageType()
    public object CheckOverlay : QuackSelectableImageType(
        size = DpSize(all = 28.dp),
        tint = QuackColor.White,
    )
}

/**
 * 오른쪽 상단에 체크박스와 함께 이미지 혹은 [QuackIcon] 을 표시합니다.
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param isSelected 현재 이미지가 선택됐는지 여부
 * @param src 표시할 리소스. 만약 null 이 들어온다면 리소스를 그리지 않습니다.
 * @param size 리소스의 크기를 지정합니다. null 이 들어오면 기본 크기로 표시합니다.
 * @param tint 적용할 틴트 값
 * @param shape 컴포넌트의 모양
 * @param selectableType selection 이 표시될 방식
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
    shape: Shape = QuackImageDefaults.SelectableImage.Shape,
    selectableType: QuackSelectableImageType = TopEndCheckBox,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    contentDescription: String? = null,
): Unit = with(
    receiver = QuackImageDefaults.SelectableImage,
) {
    QuackSurface(
        modifier = modifier,
        shape = shape,
        border = borderFor(isSelected = isSelected)
            .takeIf { selectableType == TopEndCheckBox },
        rippleEnabled = rippleEnabled,
        onClick = onClick,
        contentAlignment = Alignment.TopEnd,
    ) {
        QuackImage(
            modifier = Modifier.zIndex(1f),
            src = src,
            size = size,
            tint = tint,
            contentScale = contentScale,
            contentDescription = contentDescription,
        )
        when (selectableType) {
            TopEndCheckBox -> {
                QuackRoundCheckBox(
                    modifier = Modifier
                        .padding(
                            paddingValues = Margin,
                        )
                        .zIndex(2f),
                    checked = isSelected,
                )
            }
            CheckOverlay -> {
                QuackAnimatedVisibility(
                    modifier = Modifier
                        .matchParentSize()
                        .zIndex(2f),
                    visible = isSelected,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = QuackColor.Dimmed.composeColor),
                        contentAlignment = Alignment.Center,
                    ) {
                        QuackImage(
                            src = QuackIcon.Check,
                            size = selectableType.size!!,
                            tint = selectableType.tint!!,
                        )
                    }
                }
            }
        }
    }
}
