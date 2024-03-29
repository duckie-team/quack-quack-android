/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import team.duckie.quackquack.casa.annotation.CasaValue
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.runtime.QuackDataModifierModel
import team.duckie.quackquack.runtime.quackComposed
import team.duckie.quackquack.runtime.quackMaterializeOf
import team.duckie.quackquack.sugar.material.SugarName
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.sugar.material.Sugarable
import team.duckie.quackquack.ui.plugin.interceptor.rememberInterceptedStyleSafely
import team.duckie.quackquack.ui.util.asLoose
import team.duckie.quackquack.ui.util.rememberLtrTextMeasurer
import team.duckie.quackquack.util.fastFirstIsInstanceOrNull

/**
 * [Modifier.highlight]에 들어가는 하이라이트 아이템을 [Pair]로 나타냅니다.
 *
 * 하이라이트 대상 문자열을 나타내는 [String]과, 하이라이트 문자열을 눌렀을 때 실행될
 * `(text: String) -> Unit` 람다로 구성돼 있습니다. 람다 속 `text` 인자는 클릭된 문자열을
 * 반환합니다.
 */
public typealias HighlightText = Pair<String, ((text: String) -> Unit)?>

@Stable
private data class TextSpanData(
  val texts: List<String>,
  val style: SpanStyle,
) : QuackDataModifierModel

@Stable
private data class TextHighlightData(
  val highlights: List<HighlightText>,
  val span: SpanStyle,
) : QuackDataModifierModel

/**
 * 주어진 텍스트에 [SpanStyle]을 적용합니다.
 *
 * @param texts [SpanStyle]을 적용할 텍스트 모음
 * @param style 적용할 [SpanStyle]
 */
@Stable
public fun Modifier.span(
  texts: List<String>,
  style: SpanStyle,
): Modifier =
  inspectable(
    inspectorInfo = debugInspectorInfo {
      name = "span"
      properties["texts"] = texts
      properties["style"] = style
    },
  ) {
    TextSpanData(texts = texts, style = style)
  }

/**
 * 주어진 텍스트에 클릭 가능한 [SpanStyle]을 입힙니다.
 *
 * @param highlights 클릭 이벤트를 적용할 텍스트 모음
 * @param span 적용할 [SpanStyle]
 */
@Stable
public fun Modifier.highlight(
  highlights: List<HighlightText>,
  span: SpanStyle = SpanStyle(
    color = QuackColor.DuckieOrange.value,
    fontWeight = FontWeight.SemiBold,
  ),
): Modifier =
  inspectable(
    inspectorInfo = debugInspectorInfo {
      name = "highlight"
      properties["highlights"] = highlights
      properties["span"] = span
    },
  ) {
    TextHighlightData(highlights = highlights, span = span)
  }

/**
 * 주어진 텍스트에 클릭 가능한 [SpanStyle]을 입힙니다.
 *
 * @param texts 클릭 이벤트를 적용할 텍스트 모음
 * @param span 적용할 [SpanStyle]
 * @param globalOnClick [texts]에 전역으로 적용할 클릭 이벤트
 */
@Stable
public fun Modifier.highlight(
  texts: List<String>,
  span: SpanStyle = SpanStyle(
    color = QuackColor.DuckieOrange.value,
    fontWeight = FontWeight.SemiBold,
  ),
  globalOnClick: (text: String) -> Unit,
): Modifier =
  inspectable(
    inspectorInfo = debugInspectorInfo {
      name = "highlight"
      properties["texts"] = texts
      properties["span"] = span
    },
  ) {
    quackComposed {
      val highlights = remember(texts, globalOnClick) {
        texts.fastMap { text -> HighlightText(text, globalOnClick) }
      }
      TextHighlightData(highlights = highlights, span = span)
    }
  }

@VisibleForTesting
internal object QuackTextErrors {
  const val CannotUseSpanAndHighlightAtSameTime = "Modifier.span과 Modifier.highlight는 같이 사용될 수 없습니다."
}

/**
 * 텍스트를 그리는 기본적인 컴포저블입니다.
 *
 * 만약 [typography]로 [QuackTypography.Quote]이 들어온다면 특수한 배치 정책을 가집니다.
 * 이 컴포넌트가 차지할 수 있는 최대 너비로 컴포넌트의 너비가 지정되고, 컴포넌트 가운데로
 * 텍스트가 정렬됩니다. 또한 컴포넌트 너비 양옆에 큰따옴표가 붙습니다.
 *
 * 사용 가능한 데코레이터: [Modifier.span], [Modifier.highlight]
 *
 * @param text 그릴 텍스트
 * @param typography 텍스트를 그릴 때 사용할 타이포그래피
 * @param singleLine 텍스트가 한 줄로 그려질 지 여부. 텍스트가 주어진 줄 수를 초과하면
 * [softWrap] 및 [overflow]에 따라 잘립니다.
 * @param softWrap 텍스트에 softwrap break를 적용할지 여부. `false`이면 텍스트 글리프가
 * 가로 공간이 무제한인 것처럼 배치됩니다. 또한 [overflow] 및 [TextAlign]에 예기치 않은
 * 효과가 발생할 수 있습니다.
 * @param overflow 시각적 overflow를 처리하는 방법
 *
 * @sample team.duckie.quackquack.ui.sample.SampleTest
 */
@Sugarable
@SugarName(SugarName.PREFIX_NAME + SugarName.TOKEN_NAME)
@Composable
public fun QuackText(
  @CasaValue("\"QuackText\"") text: String,
  @SugarToken typography: QuackTypography,
  modifier: Modifier = Modifier,
  singleLine: Boolean = false,
  softWrap: Boolean = true,
  overflow: TextOverflow = TextOverflow.Ellipsis,
) {
  val (composeModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier)
  val spanData = remember(quackDataModels) {
    quackDataModels.fastFirstIsInstanceOrNull<TextSpanData>()
  }
  val highlightData = remember(quackDataModels) {
    quackDataModels.fastFirstIsInstanceOrNull<TextHighlightData>()
  }

  if (highlightData != null && spanData != null) {
    error(QuackTextErrors.CannotUseSpanAndHighlightAtSameTime)
  }

  @Suppress("NAME_SHADOWING")
  val typography = rememberInterceptedStyleSafely<QuackTypography>(style = typography, modifier = modifier)
  val currentTypography = remember(typography, calculation = typography::asComposeStyle)

  val maxLines = if (singleLine) 1 else Int.MAX_VALUE

  val quoteMeasurer = rememberLtrTextMeasurer()
  val quoteMeasurerResults =
    remember(currentTypography) {
      if (typography == QuackTypography.Quote) {
        listOf(
          quoteMeasurer.measure(
            text = "“",
            style = currentTypography,
            softWrap = false,
            maxLines = 1,
          ),
          quoteMeasurer.measure(
            text = "”",
            style = currentTypography,
            softWrap = false,
            maxLines = 1,
          ),
        )
      } else {
        null
      }
    }

  var quoteableModifier = composeModifier
  if (quoteMeasurerResults != null) {
    quoteableModifier =
      quoteableModifier
        .drawBehind {
          val (leftQuote, rightQuote) = quoteMeasurerResults
          drawText(
            textLayoutResult = leftQuote,
            color = QuackColor.Black.value,
            topLeft = Offset.Zero,
          )
          drawText(
            textLayoutResult = rightQuote,
            color = QuackColor.Black.value,
            topLeft = Offset(
              x = size.width - rightQuote.size.width,
              y = 0f,
            ),
          )
        }
        .layout { measurable, constraints ->
          val looseConstraints = constraints.asLoose(width = true, height = true)
          val quoteablePlaceable = measurable.measure(looseConstraints)

          val width = constraints.maxWidth
          val height = quoteablePlaceable.height

          layout(width = width, height = height) {
            quoteablePlaceable.place(
              x = Alignment.CenterHorizontally.align(
                size = quoteablePlaceable.width,
                space = width,
                layoutDirection = layoutDirection,
              ),
              y = 0,
            )
          }
        }
  }

  if (spanData != null) {
    BasicText(
      modifier = quoteableModifier,
      text = rememberSpanAnnotatedString(
        text = text,
        spanTexts = spanData.texts,
        spanStyle = spanData.style,
        annotationTexts = emptyList(),
      ),
      style = currentTypography,
      overflow = overflow,
      softWrap = softWrap,
      maxLines = maxLines,
    )
  } else if (highlightData != null) {
    ClickableText(
      modifier = quoteableModifier,
      text = text,
      highlightData = highlightData,
      style = currentTypography,
      overflow = overflow,
      softWrap = softWrap,
      maxLines = maxLines,
    )
  } else {
    BasicText(
      modifier = quoteableModifier,
      text = text,
      style = currentTypography,
      overflow = overflow,
      softWrap = softWrap,
      maxLines = maxLines,
    )
  }
}

@Composable
private fun ClickableText(
  modifier: Modifier,
  text: String,
  highlightData: TextHighlightData,
  style: TextStyle,
  softWrap: Boolean,
  overflow: TextOverflow,
  maxLines: Int,
) {
  val highlightTexts = remember(highlightData.highlights) {
    highlightData.highlights.fastMap(Pair<String, *>::first)
  }
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
public fun rememberSpanAnnotatedString(
  text: String,
  spanTexts: List<String>,
  spanStyle: SpanStyle,
  annotationTexts: List<String>,
): AnnotatedString =
  remember(text, spanTexts, spanStyle) {
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
