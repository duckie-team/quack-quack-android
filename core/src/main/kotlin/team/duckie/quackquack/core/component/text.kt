/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.core.component

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.util.fastFirstOrNull
import androidx.compose.ui.util.fastForEach
import kotlinx.collections.immutable.ImmutableList
import team.duckie.quackquack.core._internal.QuackDataModel
import team.duckie.quackquack.core._internal.materializerOf
import team.duckie.quackquack.core._material.QuackTypography
import team.duckie.quackquack.core._material.animatedQuackTextStyleAsState
import team.duckie.quackquack.core.animation.QuackAnimatedContent
import team.duckie.quackquack.core.sugar.annotation.Sugar

private data class AnnotatedStringData(
    val decorationTexts: ImmutableList<String>,
    val decorationStyle: SpanStyle,
) : QuackDataModel, Modifier.Element

public interface QuackText {
    public fun Modifier.highlight(
        decorationTexts: ImmutableList<String>,
        decorationStyle: SpanStyle,
    ): Modifier
}

internal object QuackTextScope : QuackText {
    override fun Modifier.highlight(
        decorationTexts: ImmutableList<String>,
        decorationStyle: SpanStyle,
    ): Modifier {
        return then(
            AnnotatedStringData(
                decorationTexts = decorationTexts,
                decorationStyle = decorationStyle,
            )
        )
    }
}

@Sugar
@Composable
public fun QuackText.Text(
    modifier: Modifier = Modifier,
    typography: QuackTypography,
    text: String,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    val animatedQuackTextStyle = animatedQuackTextStyleAsState(typography)
    val (composeModifier, quackDataModels) = materializerOf(modifier)
    val annotatedData = quackDataModels.fastFirstOrNull { it is AnnotatedStringData } as? AnnotatedStringData

    QuackAnimatedContent(
        modifier = composeModifier,
        targetState = text,
    ) { animatedText ->
        if (annotatedData == null) {
            BasicText(
                text = animatedText,
                style = animatedQuackTextStyle.asComposeStyle(),
                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                overflow = overflow,
            )
        } else {
            BasicText(
                text = rememberDecorationAnnotatedString(
                    text = animatedText,
                    decorationTexts = annotatedData.decorationTexts,
                    decorationStyle = annotatedData.decorationStyle,
                ),
                style = animatedQuackTextStyle.asComposeStyle(),
                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                overflow = overflow,
            )
        }
    }
}

/**
 * 주어진 옵션에 일치하는 데코레이션을 추가한 [AnnotatedString]을 계산합니다.
 *
 * @param text 원본 텍스트
 * @param decorationTexts 데코레이션 처리할 텍스트들
 * @param decorationStyle 데코레이션 스타일
 */
@Composable
private fun rememberDecorationAnnotatedString(
    text: String,
    decorationTexts: ImmutableList<String>,
    decorationStyle: SpanStyle,
): AnnotatedString {
    return remember(text, decorationTexts, decorationStyle) {
        buildAnnotatedString {
            append(text)
            decorationTexts.fastForEach { annotatedText ->
                val annotatedStartIndex = text.indexOf(annotatedText)
                if (annotatedStartIndex != -1) {
                    addStyle(
                        style = decorationStyle,
                        start = annotatedStartIndex,
                        end = annotatedStartIndex + annotatedText.length,
                    )
                }
            }
        }
    }
}
