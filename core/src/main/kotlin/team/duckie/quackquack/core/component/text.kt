/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.core.component

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import team.duckie.quackquack.aide.annotation.TypedModifier
import team.duckie.quackquack.core.animation.QuackAnimatedContent
import team.duckie.quackquack.core.material.QuackColor
import team.duckie.quackquack.core.material.QuackTypography
import team.duckie.quackquack.core.material.animatedQuackTextStyleAsState
import team.duckie.quackquack.core.runtime.QuackDataModifierModel
import team.duckie.quackquack.core.runtime.quackComposed
import team.duckie.quackquack.core.runtime.quackMaterializeOf
import team.duckie.quackquack.core.util.fastFirstIsInstanceOrNull
import team.duckie.quackquack.sugar.material.SugarName
import team.duckie.quackquack.sugar.material.SugarToken

/**
 * `Modifier.highlight`에 들어가는 하이라이트 아이템을 [Pair]로 나타냅니다.
 *
 * 하이라이트 대상 문자열을 나타내는 `String`과,
 * 하이라이트 문자열을 눌렀을 때 실행될 `(text: String) -> Unit` 람다로 구성돼 있습니다.
 * 람다 속 `text` 인자는 클릭된 문자열을 반환합니다.
 */
public typealias HighlightText = Pair<String, ((text: String) -> Unit)?>

@Stable
private data class SpanData(
    val texts: List<String>,
    val style: SpanStyle,
) : QuackDataModifierModel {
    private val textArray = texts.toTypedArray()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SpanData) return false

        if (texts != other.texts) return false
        if (!textArray.contentEquals(other.texts.toTypedArray())) return false
        if (style != other.style) return false

        return true
    }

    override fun hashCode(): Int {
        var result = textArray.contentHashCode()
        result = 31 * result + style.hashCode()
        return result
    }
}

@Stable
private data class HighlightData(
    val highlights: List<HighlightText>,
    val span: SpanStyle,
) : QuackDataModifierModel {
    private val highlightArray = highlights.toTypedArray()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HighlightData) return false

        if (highlights != other.highlights) return false
        if (!highlightArray.contentEquals(other.highlights.toTypedArray())) return false
        if (span != other.span) return false

        return true
    }

    override fun hashCode(): Int {
        var result = highlightArray.contentHashCode()
        result = 31 * result + span.hashCode()
        return result
    }
}

/**
 * 주어진 텍스트에 [SpanStyle]을 적용합니다.
 *
 * @param texts [SpanStyle]을 적용할 텍스트 모음
 * @param style 적용할 [SpanStyle]
 */
@TypedModifier
@Stable
public fun Modifier.span(
    texts: List<String>,
    style: SpanStyle,
): Modifier {
    return then(SpanData(texts = texts, style = style))
}

/**
 * 주어진 텍스트에 클릭 가능한 [SpanStyle]을 입힙니다.
 *
 * @param highlights 클릭 이벤트를 적용할 텍스트 모음
 * @param span 적용할 [SpanStyle]
 */
@TypedModifier
@Stable
public fun Modifier.highlight(
    highlights: List<HighlightText>,
    span: SpanStyle = SpanStyle(
        color = QuackColor.DuckieOrange.value,
        fontWeight = FontWeight.SemiBold,
    ),
): Modifier {
    return then(HighlightData(highlights = highlights, span = span))
}

/**
 * 주어진 텍스트에 클릭 가능한 [SpanStyle]을 입힙니다.
 *
 * @param texts 클릭 이벤트를 적용할 텍스트 모음
 * @param span 적용할 [SpanStyle]
 * @param globalOnClick [texts]에 전역으로 적용할 클릭 이벤트
 */
@TypedModifier
@Stable
public fun Modifier.highlight(
    texts: List<String>,
    span: SpanStyle = SpanStyle(
        color = QuackColor.DuckieOrange.value,
        fontWeight = FontWeight.SemiBold,
    ),
    globalOnClick: (text: String) -> Unit,
): Modifier {
    return quackComposed {
        val highlights = remember(texts, globalOnClick) {
            texts.fastMap { text -> HighlightText(text, globalOnClick) }
        }
        then(HighlightData(highlights = highlights, span = span))
    }
}

@VisibleForTesting
internal object QuackTextErrors {
    const val CannotUseSpanAndHighlightAtSameTime = "Modifier.span과 Modifier.highlight는 같이 사용될 수 없습니다."
}

/**
 * 텍스트를 그리는 기본적인 컴포저블입니다.
 *
 * @param text 그릴 텍스트
 * @param typography 텍스트를 그릴 때 사용할 타이포그래피
 * @param singleLine 텍스트가 한 줄로 그려질 지 여부.
 * 텍스트가 주어진 줄 수를 초과하면 [softWrap] 및 [overflow]에 따라 잘립니다.
 * @param softWrap 텍스트에 softwrap break를 적용할지 여부.
 * `false`이면 텍스트 글리프가 가로 공간이 무제한인 것처럼 배치됩니다.
 * 또한 [overflow] 및 [TextAlign]에 예기치 않은 효과가 발생할 수 있습니다.
 * @param overflow 시각적 overflow를 처리하는 방법
 */
@SugarName(SugarName.PREFIX_NAME + SugarName.TOKEN_NAME)
@Composable
public fun QuackText(
    modifier: Modifier = Modifier,
    text: String,
    @SugarToken typography: QuackTypography,
    singleLine: Boolean = false,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    val (composeModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier)
    val spanData = remember(quackDataModels) {
        quackDataModels.fastFirstIsInstanceOrNull<SpanData>()
    }
    val highlightData = remember(quackDataModels) {
        quackDataModels.fastFirstIsInstanceOrNull<HighlightData>()
    }

    if (highlightData != null && spanData != null) {
        error(QuackTextErrors.CannotUseSpanAndHighlightAtSameTime)
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
                    annotationTexts = emptyList(),
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
    val highlightTexts = highlightData.highlights.fastMap(Pair<String, *>::first)
    val annotatedText = rememberSpanAnnotatedString(
        text = text,
        spanTexts = highlightTexts,
        spanStyle = highlightData.span,
        annotationTexts = highlightTexts,
    )

    ClickableText(
        modifier = modifier,
        text = annotatedText,
        style = style,
        onClick = { offset ->
            highlightData.highlights.fastForEach { (text, onClick) ->
                val annotations = annotatedText.getStringAnnotations(
                    tag = text,
                    start = offset,
                    end = offset,
                )
                if (annotations.isNotEmpty() && onClick != null) {
                    onClick(text)
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
    annotationTexts: List<String>,
): AnnotatedString {
    return remember(text, spanTexts, spanStyle) {
        buildAnnotatedString {
            append(text)
            spanTexts.fastForEach { spanText ->
                val spanStartIndex = text.indexOf(spanText)
                if (spanStartIndex != -1) {
                    addStyle(
                        style = spanStyle,
                        start = spanStartIndex,
                        end = spanStartIndex + spanText.length,
                    )
                }
            }
            annotationTexts.fastForEach { annotationText ->
                val annotationStartIndex = text.indexOf(annotationText)
                if (annotationStartIndex != -1) {
                    addStringAnnotation(
                        tag = annotationText,
                        start = annotationStartIndex,
                        end = annotationStartIndex + annotationText.length,
                        annotation = annotationText,
                    )
                }
            }
        }
    }
}
