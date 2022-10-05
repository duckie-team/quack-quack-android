/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.uxwriting.overlay

import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt
import team.duckie.quackquack.uxwriting.model.QuackWrting
import team.duckie.quackquack.uxwriting.rule.BasicWritingRule

/**
 * QuackText 로 표시되고 있는 텍스트가 QuackQuack 의 ux writing
 * 규칙에 맞게 표시되고 있는지 검사합니다.
 *
 * 만약 규칙에 어긋나게 표시되는 텍스트가 있다면 해당 텍스트 밑에
 * 올바른 writing 과 잘못된 이유를 표시합니다.
 *
 * @param quackTexts QuackText 로 표시되고 있는 텍스트들
 */
@Composable
fun QuackUxWritingOverlay(
    quackTexts: SnapshotStateList<QuackWrting>,
) {
    quackTexts.fastForEach { quackText ->
        BasicWritingRule().fastForEach { rule ->
            val result = quackText.check(
                rule = rule,
            )
            if (!result) {
                val (wrong, correct, because) = rule
                Text(
                    modifier = Modifier.offset {
                        quackText.offset.run {
                            IntOffset(
                                x = x.roundToInt(),
                                y = y.roundToInt() + 20,
                            )
                        }
                    },
                    text = "$wrong -> $correct\n$because",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
