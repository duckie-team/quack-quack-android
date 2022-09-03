/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [label.kt] created by doro n on 22. 9. 3. 오후 9:40
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor

private val QuackLabelShape = RoundedCornerShape(11.dp)
private val SkyBlueColor = Color(0xFF6DBBFF)
private val QuackLabelPadding = PaddingValues(
    horizontal = 6.dp,
    vertical = 4.dp,
)


/**
 * 덕키의 QuackSimpleLabel 을 구현합니다.
 * QuackLabelStatus 의 값에 따라서 값이 달리집니다.
 *
 * @param text 라벨에 표시될 텍스트
 * @param active 버튼 활성화 여부. 배경 색상에 영향을 미칩니다.
 *
 */


@Composable
fun QuackSimpleLabel(
    text: String,
    active: Boolean
) {
    val backgroundColor = when (active) {
        true -> SkyBlueColor
        else -> QuackColor.Gray3.value
    }
    val textColor = when (active) {
        true -> QuackColor.White
        else -> QuackColor.Gray1
    }

    Box(
        modifier = Modifier
            .clip(QuackLabelShape)
            .background(backgroundColor)
            .padding(QuackLabelPadding)
    ) {
        QuackBody3(text = text, color = textColor)
    }
}

@Preview
@Composable
private fun QuackSimpleLabelPreview() {

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

        QuackSimpleLabel(text = "거래완료", active = false)
        QuackSimpleLabel(text = "예약중", active = true)

    }
}
