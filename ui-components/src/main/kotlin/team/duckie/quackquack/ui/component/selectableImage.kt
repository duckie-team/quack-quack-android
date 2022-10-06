/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [selectableImage.kt] created by doro on 22. 9. 3. 오후 11:45
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.QuackColor.Companion.Black80
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackBorderOrNull
import team.duckie.quackquack.ui.modifier.quackClickable

private val ImagePadding = PaddingValues(top = 4.dp, end = 4.dp)
private val SmallDeletableImagePadding = 4.dp
private val SmallDeletableImageSize = 44.dp
private val DeletableImageLargeSize = 72.dp
private val QuackDeletableImageShape = RoundedCornerShape(12.dp)
private val LargeDeletableImagePadding = PaddingValues(
    all = 6.dp,
)
private val SelectableImageDefaultSize = 118.dp
private val SelectedIconSize = DpSize(
    width = 28.dp,
    height = 28.dp,
)
private val DeletableIconSize = DpSize(
    width = 16.dp,
    height = 16.dp,
)

/**
 *
 *
 * QuackSelectableImage 를 구현합니다.
 *
 * @param overrideSize 이미지의 길이는 화면에 따라 가변적이므로 넘겨받습니다
 * @param isSelected 해당 이미지가 선택되었는지에 대한 상태값
 * @param image 이미지 resource
 * @param onClick 이미지가 선택되었을 때 발생하는 클릭 이벤트 -> 이미지 자체가 아니라 아이콘의 클릭 이벤트
 *
 * 클릭될 때마다 SelectedFilterBox 가 나타나게 됨
 *
 */
@Composable
public fun QuackSelectableImage(
    overrideSize: Dp = SelectableImageDefaultSize,
    isSelected: Boolean,
    image: Any,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .quackBorderOrNull(
                color = QuackColor.DuckieOrange,
                enabled = isSelected,
            ),
        contentAlignment = Alignment.TopEnd,
    ) {
        QuackImage(
            src = image,
            overrideSize = DpSize(
                width = overrideSize,
                height = overrideSize,
            ),
        )
        if (isSelected) SelectedFilterBox()
        QuackSelectedIcon(
            isSelected = isSelected,
            onClick = onClick,
        )
    }
}

/**
 * QuackSelectedIcon 은 QuackSelectableImage 에서 사용되는 우상단 아이콘입니다.
 *
 * @param isSelected 아이콘이 선택되었는지에 대한 상태값, 어떤 아이콘을 보여줄지 결정합니다
 * @param onClick 아이콘 클릭시 발생하는 클릭 이벤트
 */
@Composable
private fun QuackSelectedIcon(
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val selectableIcon = when (isSelected) {
        true -> QuackIcon.Checked
        else -> QuackIcon.UnChecked
    }

    QuackImageInternal(
        modifier = Modifier
            .padding(
                paddingValues = ImagePadding,
            )
            .quackClickable(
                rippleEnabled = false,
                onClick = onClick,
            ),
        src = selectableIcon,
        overrideSize = SelectedIconSize,
    )
}

/**
 * [SelectedFilterBox] 를 구현합니다.
 * 이미지가 선택되었을 때 나타나는 Deem Filter
 *
 */
@Composable
private fun SelectedFilterBox() {
    Box {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    color = Black80.composeColor,
                )
        )
    }
}

/**
 * QuackDeletableImage 의 Large Component 를 구현합니다
 *
 * @param image 이미지 Resource
 * @param overrideSize 이미지의 크기
 * @param onClick deleteIcon 을 클릭헀을 때 발생하는 클릭 이벤트
 *
 */
@Composable
public fun QuackLargeDeletableImage(
    image: Any,
    overrideSize: Dp = DeletableImageLargeSize,
    onClick: () -> Unit,

    ) {
    Box(
        modifier = Modifier.size(
            size = overrideSize,
        ),
    ) {
        QuackDeleteImage(
            image = image,
            overrideSize = overrideSize,
        )
        QuackDeleteIcon(
            modifier = Modifier.align(
                alignment = Alignment.TopEnd,
            ),
            onClick = onClick,
            paddingValues = LargeDeletableImagePadding,
        )
    }
}

/**
 * QuackDeletableImage 의 Small Component 를 구현합니다
 * @param image 이미지 Resource
 * @param overrideSize 이미지 size
 * @param onClick deleteIcon 을 클릭헀을 때 발생하는 클릭 이벤트
 */
@Composable
public fun QuackSmallDeletableImage(
    image: Any,
    overrideSize: Dp = SmallDeletableImageSize,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.size(
            size = overrideSize + SmallDeletableImagePadding
        ),
    ) {
        QuackDeleteImage(
            modifier = Modifier
                .align(
                    alignment = Alignment.Center,
                ),
            image = image,
            overrideSize = overrideSize,
        )
        QuackDeleteIcon(
            modifier = Modifier.align(
                alignment = Alignment.TopEnd,
            ),
            onClick = onClick,
        )
    }
}

/**
 * Quack*DeletableImage 에서 공통적으로 사용되는 Background 이미지
 * @param modifier matchParentSize() 값을 전달받기 위한 Modifier
 * @param image 이미지 resource
 * @param overrideSize 이미지 size
 */
@Composable
private fun QuackDeleteImage(
    modifier: Modifier = Modifier,
    image: Any,
    overrideSize: Dp,
) {
    Box(
        modifier = modifier
            .clip(
                shape = QuackDeletableImageShape,
            ),
    ) {
        QuackImageInternal(
            src = image,
            overrideSize = DpSize(
                width = overrideSize,
                height = overrideSize,
            ),
        )
    }
}

/**
 * Quack*DeletableImage 에서 공통적으로 사용되는 Delete 아이콘
 *
 * @param modifier 상위 Composable로부터 넘겨받는 Modifier
 * @param paddingValues 아이콘에 들어가는 Padding 값
 * @param onClick 아이콘을 클릭했을 떄 발생한느 클릭 이벤트
 */

@Composable
private fun QuackDeleteIcon(
    modifier: Modifier,
    onClick: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(
        all = 0.dp,
    ),
) {
    Box(
        modifier = modifier
            .quackClickable(
                rippleEnabled = true,
                onClick = onClick,
            )
            .padding(
                paddingValues = paddingValues,
            ),
    ) {
        QuackImageInternal(
            src = QuackIcon.DeleteBg,
            overrideSize = DeletableIconSize,
        )
    }
}
