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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.QuackColor.Companion.Black80
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackBorderOrNull

private val ImagePadding = PaddingValues(top = 4.dp, end = 4.dp)
private val DeletableImageSmallSize = 46.dp
private val DeletableImageLargeSize = 72.dp
private val QuackDeletableImageShape = RoundedCornerShape(12.dp)
private val QuackLargeDeletablePadding = PaddingValues(top = 6.dp, end = 6.dp)
private val QuackSmallDeletablePadding = PaddingValues(top = 2.dp, end = 2.dp)

/**
 * TODO 왜 영역보다 더 Clickable 이 많이 되는거 같지?
 *
 * QuackSelectableImage 를 구현합니다.
 *
 * @param size 이미지의 길이는 화면에 따라 가변적이므로 넘겨받습니다
 * @param isSelected 해당 이미지가 선택되었는지에 대한 상태값
 * @param image 이미지 resource
 * @param onClick 이미지가 선택되었을 때 발생하는 클릭 이벤트 -> 이미지 자체가 아니라 아이콘의 클릭 이벤트
 *
 * 클릭될 때마다 SelectedFilterBox 가 나타나게 됨
 *
 */
@Composable
fun QuackSelectableImage(
    size: Dp,
    isSelected: Boolean,
    image: Any,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(
                size = size
            )
            .quackBorderOrNull(
                color = QuackColor.DuckieOrange,
                isSelected = isSelected,
            ),
    ) {
        QuackImage(
            image = image
        )
        if (isSelected) SelectedFilterBox()
        QuackSelectedIcon(
            modifier = Modifier.align(
                alignment = Alignment.TopEnd
            ),
            isSelected = isSelected,
            onClick = onClick,
        )
    }
}

/**
 * QuackSelectedIcon 은 QuackSelectableImage 에서 사용되는 우상단 아이콘입니다.
 *
 * @param modifier QuackSelectedIcon 의 alignment 설정을 위해 넘겨받습니다.
 * @param isSelected 아이콘이 선택되었는지에 대한 상태값, 어떤 아이콘을 보여줄지 결정합니다
 * @param onClick 아이콘 클릭시 발생하는 클릭 이벤트
 */
@Composable
private fun QuackSelectedIcon(
    modifier: Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val selectableIcon = when (isSelected) {
        true -> QuackIcon.Checked
        else -> QuackIcon.UnChecked
    }

    Box(
        modifier = modifier
            .padding(
                paddingValues = ImagePadding
            ),
    ) {
        QuackImage(
            icon = selectableIcon,
            rippleEnabled = false,
            onClick = onClick,
        )
    }
}

/**
 * 이미지가 선택되었을 때 나타나는 Deem Filter
 *
 * @author doro
 */
@Composable
private fun SelectedFilterBox() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Black80.value
            )
    )
}

/**
 * QuackDeletableImage 의 Large Component 를 구현합니다
 *
 * @param image 이미지 Resource
 * @param onClick deleteIcon 을 클릭헀을 때 발생하는 클릭 이벤트
 *
 */
@Composable
fun QuackLargeDeletableImage(
    image: Any,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.size(
            size = DeletableImageLargeSize
        ),
    ) {
        QuackDeleteImage(
            modifier = Modifier.matchParentSize(),
            image = image,
        )
        QuackDeleteIcon(
            modifier = Modifier
                .align(
                    alignment = Alignment.TopEnd
                )
                .padding(
                    paddingValues = QuackLargeDeletablePadding
                ),
            onClick = onClick,
        )
    }
}

/**
 * QuackDeletableImage 의 Small Component 를 구현합니다
 * @param image 이미지 Resource
 * @param onClick deleteIcon 을 클릭헀을 때 발생하는 클릭 이벤트
 */
@Composable
fun QuackSmallDeletableImage(
    image: Any,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.size(
            size = DeletableImageSmallSize
        ),
    ) {
        QuackDeleteImage(
            modifier = Modifier.matchParentSize(),
            image = image,
        )
        QuackDeleteIcon(
            modifier = Modifier.align(
                alignment = Alignment.TopEnd
            ),
            onClick = onClick,
        )
    }
}

/**
 * Quack*DeletableImage 에서 공통적으로 사용되는 Background 이미지
 * @param modifier matchParentSize() 값을 전달받기 위한 Modifier
 * @param image 이미지 resource
 */
@Composable
private fun QuackDeleteImage(
    modifier: Modifier,
    image: Any,) {
    Box(
        modifier = modifier
            .padding(
                paddingValues = QuackSmallDeletablePadding
            )
            .clip(
                shape = QuackDeletableImageShape
            ),
    ) {
        QuackImage(
            image = image
        )
    }
}

/**
 * Quack*DeletableImage 에서 공통적으로 사용되는 Delete 아이콘
 *
 * @param modifier topEnd 로 align 하기 위한 Modifier
 * @param onClick 아이콘을 클릭했을 떄 발생한느 클릭 이벤트
 */

@Composable
private fun QuackDeleteIcon(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        QuackImage(
            icon = QuackIcon.DeleteBg,
            onClick = onClick,
        )
    }
}
