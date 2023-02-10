/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.runIf
import team.duckie.quackquack.ui.util.runtimeCheck

private val QuackDialogShape = RoundedCornerShape(8.dp)

/**
 * 덕키에 맞는 다이얼로그를 구현합니다.
 * [QuackDialog] 는 다음과 같은 특징을 갖습니다.
 *
 * - 항상 Dialog 의 MATCH_PARENT 로 width 가 지정됩니다.
 *
 * - [fullWidthButtonText] 값이 지정되면 [fullWidthButtonOnClick] 값도 지정돼야 합니다.
 * - [fullWidthButtonText] 값이 지정되지 않으면 [fullWidthButtonOnClick] 값은 무시됩니다.
 * - [fullWidthButtonText] 값이 지정되면 [leftButtonText] 와 [rightButtonText] 값은 무시됩니다.
 *
 * - [leftButtonText] 값이 지정되면 [leftButtonOnClick] 값도 지정돼야 합니다.
 * - [leftButtonText] 값이 지정되지 않으면 [leftButtonOnClick] 값은 무시됩니다.
 * - [leftButtonText] 값이 지정되기 위해선 [rightButtonText] 값이 지정돼야 합니다.
 *
 * - [rightButtonText] 값이 지정되면 [rightButtonOnClick] 값도 지정돼야 합니다.
 * - [rightButtonText] 값이 지정되지 않으면 [rightButtonOnClick] 값은 무시됩니다.
 *
 * @param modifier 다이얼로그에 적용할 [Modifier]
 * @param title 다이얼로그의 제목 (필수)
 * @param message 다이얼로그의 본문 메시지
 * @param fullWidthButtonText full-width 를 갖는 버튼의 텍스트
 * @param fullWidthButtonOnClick full-width 를 갖는 버튼의 클릭 이벤트
 * @param leftButtonText left-side 에 위치한 버튼의 텍스트
 * @param leftButtonOnClick left-side 에 위치한 버튼의 클릭 이벤트
 * @param rightButtonText right-side 에 위치한 버튼의 텍스트
 * @param rightButtonOnClick right-side 에 위치한 버튼의 클릭 이벤트
 * @param visible 현재 다이얼로그가 보이고 있는지 여부
 * @param onDismissRequest Executes when the user tries to dismiss the dialog.
 * @param properties [DialogProperties] for further customization of this dialog's behavior.
 */
@Composable
public fun QuackDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String? = null,
    fullWidthButtonText: String? = null,
    fullWidthButtonOnClick: (() -> Unit)? = null,
    leftButtonText: String? = null,
    leftButtonOnClick: (() -> Unit)? = null,
    rightButtonText: String? = null,
    rightButtonOnClick: (() -> Unit)? = null,
    visible: Boolean,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
) {
    quackDialogAssertion(
        fullWidthButtonText = fullWidthButtonText,
        fullWidthButtonOnClick = fullWidthButtonOnClick,
        leftButtonText = leftButtonText,
        leftButtonOnClick = leftButtonOnClick,
        rightButtonText = rightButtonText,
        rightButtonOnClick = rightButtonOnClick,
    )

    if (visible) {
        Dialog(
            properties = properties,
            onDismissRequest = onDismissRequest,
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(QuackDialogShape)
                    .background(
                        color = QuackColor.White.composeColor,
                        shape = QuackDialogShape,
                    ),
            ) {
                QuackHeadLine2(
                    modifier = Modifier
                        .padding(
                            top = 28.dp,
                            start = 28.dp,
                            end = 28.dp,
                        ).runIf(message == null) {
                            padding(bottom = 28.dp)
                        },
                    text = title,
                )
                if (message != null) {
                    QuackBody2(
                        modifier = Modifier.padding(
                            top = 12.dp,
                            bottom = 28.dp,
                            start = 28.dp,
                            end = 28.dp,
                        ),
                        text = message,
                        color = QuackColor.Gray1,
                    )
                }
                if (fullWidthButtonText != null) {
                    QuackSurface(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = QuackColor.DuckieOrange,
                        onClick = fullWidthButtonOnClick,
                    ) {
                        QuackSubtitle(
                            modifier = Modifier.padding(vertical = 13.dp),
                            text = fullWidthButtonText,
                            color = QuackColor.White,
                        )
                    }
                }
                if (rightButtonText != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 9.dp,
                                horizontal = 16.dp,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.End,
                        )
                    ) {
                        if (leftButtonText != null) {
                            QuackSubtitle(
                                modifier = Modifier
                                    .quackClickable(
                                        rippleEnabled = false,
                                        onClick = leftButtonOnClick,
                                    )
                                    .padding(
                                        vertical = 4.dp,
                                        horizontal = 16.dp,
                                    ),
                                text = leftButtonText,
                                color = QuackColor.DuckieOrange,
                            )
                        }
                        QuackSubtitle(
                            modifier = Modifier
                                .quackClickable(
                                    rippleEnabled = false,
                                    onClick = rightButtonOnClick,
                                )
                                .padding(
                                    vertical = 4.dp,
                                    horizontal = 16.dp,
                                ),
                            text = rightButtonText,
                            color = QuackColor.DuckieOrange,
                        )
                    }
                }
            }
        }
    }
}

private fun quackDialogAssertion(
    fullWidthButtonText: String? = null,
    fullWidthButtonOnClick: (() -> Unit)? = null,
    leftButtonText: String? = null,
    leftButtonOnClick: (() -> Unit)? = null,
    rightButtonText: String? = null,
    rightButtonOnClick: (() -> Unit)? = null,
) {
    if (fullWidthButtonText != null) {
        runtimeCheck(fullWidthButtonOnClick != null) {
            "fullWidthButtonOnClick must be specified when fullWidthButtonText is specified."
        }
    }
    if (fullWidthButtonOnClick != null) {
        runtimeCheck(fullWidthButtonText != null) {
            "fullWidthButtonText must be specified when fullWidthButtonOnClick is specified."
        }
    }
    if (leftButtonText != null) {
        runtimeCheck(leftButtonOnClick != null) {
            "leftButtonOnClick must be specified when leftButtonText is specified."
        }
    }
    if (leftButtonOnClick != null) {
        runtimeCheck(leftButtonText != null) {
            "leftButtonText must be specified when leftButtonOnClick is specified."
        }
    }
    if (leftButtonText != null) {
        runtimeCheck(rightButtonText != null) {
            "rightButtonText must be specified when leftButtonText is specified."
        }
    }
    if (rightButtonText != null) {
        runtimeCheck(rightButtonOnClick != null) {
            "rightButtonOnClick must be specified when rightButtonText is specified."
        }
    }
    if (rightButtonOnClick != null) {
        runtimeCheck(rightButtonText != null) {
            "rightButtonText must be specified when rightButtonOnClick is specified."
        }
    }
    if (fullWidthButtonText != null) {
        runtimeCheck(leftButtonText == null) {
            "leftButtonText must be null when fullWidthButtonText is specified."
        }
        runtimeCheck(rightButtonText == null) {
            "rightButtonText must be null when fullWidthButtonText is specified."
        }
    }
}
