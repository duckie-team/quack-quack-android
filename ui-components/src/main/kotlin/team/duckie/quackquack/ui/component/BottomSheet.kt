/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor

// Caveat: BottomSheet 는 개인이 구현하기엔 꽤 복잡하고, edge case 가 많을 것으로 예상되어
//         Material 를 참조합니다.

/**
 * QuackBottmSheet 를 그리기 위해 필요한 리소스들을 정의합니다.
 */
private object QuackBottomSheetDefaults {
    val BackgroundColor = QuackColor.White
    val Shape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
    )
}
