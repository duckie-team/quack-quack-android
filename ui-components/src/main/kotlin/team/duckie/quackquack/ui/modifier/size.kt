/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.modifier

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth

/**
 * 컴포넌트의 추상화를 위해 [Modifier] 를 그대로 노출하는게 아닌,
 * Modifier 을 통한 조정이 필요할 것이라고 예상되는 옵션들만
 * 인자로 받을 수 있게 따로 클래스를 만들어서 노출시킵니다.
 *
 * width 와 height 와 같은 경우에는 조정이 필요한 옵션이라고
 * 예상되어 [QuackWidth] 와 [QuackHeight] 를 통해 조정할 수 있습니다.
 *
 * 이 Modifier 는 인자로 받은 [QuackWidth] 와 [QuackHeight] 를
 * 적용해 줍니다.
 *
 * @param width 컴포넌트의 width 조정을 위한 옵션
 * @param height 컴포넌트의 height 조정을 위한 옵션
 *
 * @return 사이즈가 적용된 [Modifier]
 */
@Stable
internal fun Modifier.applyQuackSize(
    width: QuackWidth,
    height: QuackHeight,
) = this
    .run {
        when (width) {
            QuackWidth.Fill -> fillMaxWidth()
            QuackWidth.Wrap -> wrapContentWidth()
            is QuackWidth.Custom -> width(width.width)
        }
    }
    .run {
        when (height) {
            QuackHeight.Fill -> fillMaxHeight()
            QuackHeight.Wrap -> wrapContentHeight()
            is QuackHeight.Custom -> height(height.height)
        }
    }
