/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackClickableText
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.Empty
import team.duckie.quackquack.ui.util.runIf

// [Note] @NonRestartableComposable 안한 이유: 인자로 받은 Text 는 동적으로 바뀔 수 있음
// TODO: 중복 코드 제거

/**
 * [QuackText] 에 [QuackTextStyle.Large1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트 표시에 한 줄만 사용할지 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackLarge1(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = modifier
        .quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
    text = text,
    style = QuackTextStyle.Large1.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
    overflow = overflow,
)

/**
 * [QuackText] 에 [QuackTextStyle.HeadLine1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트 표시에 한 줄만 사용할지 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackHeadLine1(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = modifier
        .quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
    text = text,
    style = QuackTextStyle.HeadLine1.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
    overflow = overflow,
)

/**
 * [QuackText] 에 [QuackTextStyle.HeadLine2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트 표시에 한 줄만 사용할지 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackHeadLine2(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    onClick: (() -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
): Unit = QuackText(
    modifier = modifier
        .quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
    text = text,
    style = QuackTextStyle.HeadLine2.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
    overflow = overflow,
)

/**
 * [QuackText] 에 [QuackTextStyle.Title1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트 표시에 한 줄만 사용할지 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackTitle1(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = modifier
        .quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
    text = text,
    style = QuackTextStyle.Title1.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
    overflow = overflow,
)

/**
 * [QuackText] 에 [QuackTextStyle.Title2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트 표시에 한 줄만 사용할지 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackTitle2(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = modifier
        .quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
    text = text,
    style = QuackTextStyle.Title2.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
    overflow = overflow,
)

/**
 * [QuackText] 에 [QuackTextStyle.Subtitle] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트 표시에 한 줄만 사용할지 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackSubtitle(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = modifier
        .quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
    text = text,
    style = QuackTextStyle.Subtitle.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
    overflow = overflow,
)

/**
 * [QuackText] 에 [QuackTextStyle.Subtitle2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트 표시에 한 줄만 사용할지 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackSubtitle2(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = modifier
        .quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
    text = text,
    style = QuackTextStyle.Subtitle2.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
    overflow = overflow,
)

/**
 * [QuackText] 에 [QuackTextStyle.Body1] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트 표시에 한 줄만 사용할지 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackBody1(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = modifier
        .quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
    text = text,
    style = QuackTextStyle.Body1.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
    overflow = overflow,
)

/**
 * [QuackText] 에 [QuackTextStyle.Body2] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트 표시에 한 줄만 사용할지 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackBody2(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = modifier
        .quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
    text = text,
    style = QuackTextStyle.Body2.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
    overflow = overflow,
)

/**
 * [QuackText] 에 [QuackTextStyle.Body3] 스타일을 적용하여
 * 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트 표시에 한 줄만 사용할지 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackBody3(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
): Unit = QuackText(
    modifier = modifier
        .quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
    text = text,
    style = QuackTextStyle.Body3.change(
        color = color,
        textAlign = align,
    ),
    singleLine = singleLine,
    overflow = overflow,
)

/**
 * [QuackHeadLine2] 의 원하는 부분에 밑줄로 강조하여 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param underlineTexts 강조할 텍스트 리스트
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param singleLine 텍스트를 한 줄로 사용할지 여부
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackUnderlineHeadLine2(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    underlineTexts: ImmutableList<String>,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    singleLine: Boolean = false,
    rippleEnabled: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
) {
    QuackText(
        modifier = modifier.quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
        annotatedText = rememberDecorationAnnotatedString(
            text = text,
            decorationTexts = underlineTexts,
            decorationStyle = SpanStyle(
                color = QuackColor.DuckieOrange.composeColor,
                textDecoration = TextDecoration.Underline,
            ),
        ),
        style = QuackTextStyle.HeadLine2.change(
            color = color,
            textAlign = align,
        ),
        singleLine = singleLine,
        overflow = overflow,
    )
}

/**
 * [QuackUnderlineBody3] 의 원하는 부분에 밑줄로 강조하여 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param underlineTexts 강조할 텍스트 리스트들
 * @param underlineColor 강조할 색상
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 * @param singleLine 텍스트를 한 줄로 사용할지 여부
 */
@Composable
public fun QuackUnderlineBody3(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    underlineTexts: ImmutableList<String>,
    underlineColor: QuackColor = QuackColor.DuckieOrange,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
) {
    QuackText(
        modifier = modifier.quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
        annotatedText = rememberDecorationAnnotatedString(
            text = text,
            decorationTexts = underlineTexts,
            decorationStyle = SpanStyle(
                color = underlineColor.composeColor,
                textDecoration = TextDecoration.Underline,
            ),
        ),
        style = QuackTextStyle.Body3.change(
            color = color,
            textAlign = align,
        ),
        singleLine = singleLine,
        overflow = overflow,
    )
}

/**
 * [QuackHighlightBody1] 의 원하는 부분에 SemiBold 로 강조하여 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param highlightTexts 강조할 텍스트 리스트들
 * @param color 텍스트의 색상
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 * @param singleLine 텍스트를 한 줄로 사용할지 여부
 */
@Composable
public fun QuackHighlightBody1(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    highlightTexts: ImmutableList<String>,
    color: QuackColor = QuackColor.Black,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
) {
    QuackText(
        modifier = modifier.quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
        annotatedText = rememberDecorationAnnotatedString(
            text = text,
            decorationTexts = highlightTexts,
            decorationStyle = SpanStyle(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp,
            ),
        ),
        style = QuackTextStyle.Body1.change(
            color = color,
            textAlign = align,
        ),
        singleLine = singleLine,
        overflow = overflow,
    )
}

/**
 * 주어진 옵션에 일치하는 데코레이션을 추가한 [AnnotatedString] 을 계산합니다.
 *
 * @param text 원본 텍스트
 * @param decorationTexts 데코레이션 처리할 텍스트들
 * @param decorationStyle 데코레이션 스타일
 *
 * @return 주어진 옵션에 맞게 데코레이션 처리된 [AnnotatedString]
 */
@Composable
private fun rememberDecorationAnnotatedString(
    text: String,
    decorationTexts: ImmutableList<String>,
    decorationStyle: SpanStyle,
): AnnotatedString {
    return remember(
        key1 = text,
        key2 = decorationTexts,
        key3 = decorationStyle,
    ) {
        buildAnnotatedString {
            append(
                text = text,
            )
            decorationTexts.fastForEach { annotatedText ->
                val annotatedStartIndex = text.indexOf(
                    string = annotatedText,
                )
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

/**
 * [QuackBody2] 의 원하는 부분에 해당 영역 클릭 이벤트와
 * SemiBold + Underline 강조를 추가하여 주어진 텍스트를 표시합니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param padding 적용할 패딩. 클릭 영역을 늘리기 위해 사용될 수 있습니다.
 * @param text 표시할 텍스트
 * @param highlightTextPairs 강조할 텍스트 및 그에 대응하는 클릭 이벤트를 나타내는 [Pair] 리스트
 * @param color 텍스트의 색상
 * @param underlineEnabled 밑줄 처리 활성화 여부
 * @param align 텍스트 정렬
 * @param rippleEnabled 텍스트 클릭시 ripple 발생 여부
 * @param singleLine 텍스트를 한 줄로 사용할지 여부
 * @param overflow 텍스트를 한 줄에 표시할 수 없을 때 표시 방식
 * @param onClick 텍스트가 클릭됐을 때 실행할 람다식
 */
@Composable
public fun QuackAnnotatedBody2(
    modifier: Modifier = Modifier,
    padding: PaddingValues? = null,
    text: String,
    highlightTextPairs: ImmutableList<Pair<String, (() -> Unit)?>>,
    color: QuackColor = QuackColor.Black,
    underlineEnabled: Boolean = false,
    align: TextAlign = TextAlign.Start,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
) {
    // QuackClickableText 에서 활용할 하이라이트 텍스트 목록 계산을 먼저 완료한 상태에서
    // 컴포저블 작업에 들어가므로 SnapshotStateList 로 만들 필요가 없음
    val highlightTextInfo = remember(
        key1 = highlightTextPairs,
    ) {
        buildList {
            // 전체 문자열 내에서, 검색 시작 index offset
            var startIndexOffset = 0
            highlightTextPairs.fastForEach { (targetText, targetTextClickEvent) ->
                val subStringStartIndex = text
                    .substring(
                        startIndex = startIndexOffset,
                    ).indexOf(
                        string = targetText,
                    )

                if (subStringStartIndex != -1) {
                    val realStartIndex = startIndexOffset + subStringStartIndex
                    val realEndIndex = realStartIndex + targetText.length

                    // 텍스트 클릭 이벤트 처리
                    targetTextClickEvent?.let {
                        // ClickEventTextInfo 데이터 추가
                        add(
                            element = QuackHighlightTextInfo(
                                text = targetText,
                                startIndex = realStartIndex,
                                endIndex = realEndIndex,
                                onClick = targetTextClickEvent,
                            ),
                        )
                    }

                    startIndexOffset = realEndIndex
                }
            }
        }.toImmutableList()
    }

    QuackClickableText(
        modifier = modifier.quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ).runIf(
            condition = padding != null,
        ) {
            padding(
                paddingValues = padding!!,
            )
        },
        clickEventTextInfo = highlightTextInfo,
        text = buildAnnotatedString {
            append(
                text = text,
            )

            highlightTextInfo.fastForEach { (text, startIndex, endIndex, onClick) ->
                // 텍스트 클릭 이벤트 처리
                onClick?.let {
                    // StringAnnotation 데이터 추가
                    addStringAnnotation(
                        tag = text,
                        annotation = String.Empty,
                        start = startIndex,
                        end = endIndex,
                    )
                }

                // 스타일링 처리
                val textDecoration = TextDecoration.Underline.takeIf {
                    underlineEnabled
                }
                addStyle(
                    style = SpanStyle(
                        color = QuackColor.DuckieOrange.composeColor,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.sp,
                        textDecoration = textDecoration,
                    ),
                    start = startIndex,
                    end = endIndex,
                )
            }
        },
        style = QuackTextStyle.Body2.change(
            color = color,
            textAlign = align,
        ),
        singleLine = singleLine,
        overflow = overflow,
        defaultOnClick = onClick,
    )
}

/**
 * 하이라이트 텍스트 처리를 위해 필요한 텍스트 정보
 *
 * @param text 텍스트 내용
 * @param startIndex 전체 텍스트 내에서 onClick 이벤트가 실행되는 시작 index
 * @param endIndex 전체 텍스트 내에서 onClick 이벤트가 실행되는 마지막 index
 * @param onClick 실행할 클릭 이벤트 (null 일 시 이벤트 없음)
 */
public data class QuackHighlightTextInfo(
    val text: String,
    val startIndex: Int,
    val endIndex: Int,
    val onClick: (() -> Unit)? = null,
)
