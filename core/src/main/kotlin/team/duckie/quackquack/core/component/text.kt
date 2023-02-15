/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.core.component

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import kotlinx.collections.immutable.ImmutableList
import team.duckie.quackquack.core._internal.QuackDataModifierModel
import team.duckie.quackquack.core._internal.materializerOf
import team.duckie.quackquack.core._material.QuackColor
import team.duckie.quackquack.core._material.QuackTypography
import team.duckie.quackquack.core._material.animatedQuackTextStyleAsState
import team.duckie.quackquack.core.animation.QuackAnimatedContent
import team.duckie.quackquack.core.sugar.annotation.Sugar
import team.duckie.quackquack.core.util.fastFirstInstanceOrNull

public typealias HighlightText = Pair<String, (() -> Unit)?>

private data class SpanData(
    val texts: ImmutableList<String>,
    val style: SpanStyle,
) : QuackDataModifierModel

private data class HighlightData(
    val highlights: ImmutableList<HighlightText>,
    val span: SpanStyle,
) : QuackDataModifierModel

@Immutable
public interface QuackText {
    @Stable
    public fun Modifier.span(
        texts: ImmutableList<String>,
        style: SpanStyle,
    ): Modifier

    @Stable
    public fun Modifier.highlight(
        highlights: ImmutableList<HighlightText>,
        span: SpanStyle = SpanStyle(
            color = QuackColor.DuckieOrange.value,
            fontWeight = FontWeight.SemiBold,
        ),
    ): Modifier
}

internal object QuackTextScope : QuackText {
    override fun Modifier.span(
        texts: ImmutableList<String>,
        style: SpanStyle,
    ): Modifier {
        return then(SpanData(texts = texts, style = style))
    }

    override fun Modifier.highlight(
        highlights: ImmutableList<HighlightText>,
        span: SpanStyle,
    ): Modifier {
        return then(HighlightData(highlights = highlights, span = span))
    }
}

@Sugar
@Composable
public fun QuackText.QuackText(
    modifier: Modifier = Modifier,
    typography: QuackTypography,
    text: String,
    singleLine: Boolean = false,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    val (composeModifier, quackDataModels) = remember(modifier) { materializerOf(modifier) }
    val spanData = remember(quackDataModels) {
        quackDataModels.fastFirstInstanceOrNull<SpanData>()
    }
    val highlightData = remember(quackDataModels) {
        quackDataModels.fastFirstInstanceOrNull<HighlightData>()
    }

    if (highlightData != null && spanData != null) {
        error("Modifier.span과 Modifier.highlight는 같이 사용될 수 없습니다.")
    }

    val style = animatedQuackTextStyleAsState(typography).asComposeStyle()
    val maxLines = if (singleLine) 1 else Int.MAX_VALUE

    QuackAnimatedContent(
        modifier = composeModifier,
        targetState = text,
    ) { animatedText ->
        if (spanData != null) {
            BasicText(
                modifier = composeModifier,
                text = rememberSpanAnnotatedString(
                    text = animatedText,
                    spanTexts = spanData.texts,
                    spanStyle = spanData.style,
                ),
                style = style,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
            )
        } else if (highlightData != null) {
            QuackClickableText(
                modifier = composeModifier,
                text = animatedText,
                highlightData = highlightData,
                style = style,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
            )
        } else {
            BasicText(
                modifier = composeModifier,
                text = animatedText,
                style = style,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
            )
        }
    }
}

@Composable
private fun QuackClickableText(
    modifier: Modifier,
    text: String,
    highlightData: HighlightData,
    style: TextStyle,
    softWrap: Boolean,
    overflow: TextOverflow,
    maxLines: Int,
) {
    val annotatedText = rememberSpanAnnotatedString(
        text = text,
        spanTexts = highlightData.highlights.fastMap(Pair<String, *>::first),
        spanStyle = highlightData.span,
    )

    ClickableText(
        modifier = modifier,
        text = annotatedText,
        style = style,
        onClick = { offset ->
            highlightData.highlights.fastForEach { (text, onClick) ->
                annotatedText
                    .getStringAnnotations(
                        tag = text,
                        start = offset,
                        end = offset,
                    )
                    .firstOrNull()
                    ?.let {
                        onClick?.invoke()
                    }
            }
        },
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
    )
}

/**
 * 주어진 옵션에 일치하는 span을 추가한 [AnnotatedString]을 계산합니다.
 *
 * @param text 원본 텍스트
 * @param spanTexts span 처리할 텍스트들
 * @param spanStyle span 스타일
 */
@Stable
@Composable
private fun rememberSpanAnnotatedString(
    text: String,
    spanTexts: List<String>,
    spanStyle: SpanStyle,
): AnnotatedString {
    return remember(text, spanTexts, spanStyle) {
        buildAnnotatedString {
            append(text)
            spanTexts.fastForEach { annotatedText ->
                val annotatedStartIndex = text.indexOf(annotatedText)
                if (annotatedStartIndex != -1) {
                    addStyle(
                        style = spanStyle,
                        start = annotatedStartIndex,
                        end = annotatedStartIndex + annotatedText.length,
                    )
                }
            }
        }
    }
}

