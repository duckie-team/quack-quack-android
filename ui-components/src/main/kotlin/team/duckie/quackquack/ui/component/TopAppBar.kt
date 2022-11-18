/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.DpSize
import team.duckie.quackquack.ui.util.runtimeCheck

/**
 * QuackTopAppBar 를 그리기 위한 리소스들을 정의합니다.
 */
private object QuackTopAppBarDefaults {
    val Height = 48.dp
    val BackgroundColor = QuackColor.White

    private val LeadingTypography = QuackTextStyle.HeadLine2
    private val CenterTypography = QuackTextStyle.Body1
    private val TrailingTypography = QuackTextStyle.Subtitle.change(
        color = QuackColor.Gray2,
    )

    private val TrailingTextPadding = PaddingValues(
        end = 16.dp,
    )

    private val LogoIcon = QuackIcon.TextLogo
    private val LogoIconSize = androidx.compose.ui.unit.DpSize(
        width = 40.dp,
        height = 18.dp,
    )

    private val IconSize = DpSize(
        all = 48.dp,
    )

    private val CenterTrailingIcon = QuackIcon.ArrowDown

    /**
     * center content 의 trailing 으로 적용되는 아이콘의 사이즈
     *
     * [IconSize] 는 leading/trailing content 에 적용되는 아이콘의 사이즈를 나타냅니다.
     */
    private val CenterIconSize = DpSize(
        all = 24.dp,
    )

    /**
     * ```
     * [Icon][Icon]
     * ```
     *
     * 위와 같은 형식에서 적용되는 개별 아이콘들의 사이즈
     *
     * 아이콘 사이 간격인 8 을, 각각 사이즈에 4 씩 더하여 배분하였습니다.
     *
     * [IconSize] 는 아이콘이 하나만 쓰일 때 적용된다.
     */
    private val ExtraIconSize = DpSize(
        all = 28.dp,
    )

    /**
     * trailing content 에 배치될 아이콘의 크기를 조건에 맞게 계산합니다.
     *
     * @param hasExtraIcon extra icon 을 가지고 있는지 여부
     *
     * @return trailing content 에 배치될 아이콘의 크기
     */
    @Stable
    private fun trailingIconSizeFor(
        hasExtraIcon: Boolean,
    ) = when (hasExtraIcon) {
        true -> ExtraIconSize
        false -> IconSize
    }

    private val CenterIconTint = QuackColor.Gray1

    /**
     * leading content 로 배치될 컴포저블을 계산합니다.
     *
     * @param icon 배치할 아이콘
     * @param text 배치할 텍스트. 선택적으로 값을 받습니다.
     * @param onIconClick [icon] 이 클릭됐을 때 실행될 람다
     *
     * @return 입력받는 인자로 계산된 leading content 의 컴포저블
     */
    @SuppressLint("ComposableNaming")
    @Composable
    fun LeadingContent(
        icon: QuackIcon,
        text: String? = null,
        onIconClick: () -> Unit,
    ): @Composable () -> Unit {
        return {
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                QuackImage(
                    src = icon,
                    size = IconSize,
                    rippleEnabled = false,
                    onClick = onIconClick,
                )
                text?.let {
                    // TODO: 최대 width 처리
                    // 아마 커스텀 레이아웃 필요할 듯
                    QuackText(
                        text = text,
                        style = LeadingTypography,
                        singleLine = true,
                    )
                }
            }
        }
    }

    /**
     * center content 로 배치될 컴포저블을 계산합니다.
     *
     * - [showLogo] 이 true 라면 [text] 값은 무시됩니다.
     *   로고 리소스로는 [QuackIcon.TextLogo] 를 사용합니다.
     * - [text] 값이 있다면 [showLogo] 값은 무시됩니다.
     *   또한 [text] 는 항상 trailing icon 으로 [QuackIcon.ArrowDown] 을 갖습니다.
     *
     * @param showLogo 덕키의 로고를 배치할지 여부
     * @param text 로고 대신에 표시할 텍스트
     * @param onClick center content 가 클릭됐을 때 실행될 람다
     *
     * @return 입력받는 인자로 계산된 center content 의 컴포저블
     */
    @SuppressLint("ComposableNaming")
    @Composable
    fun CenterContent(
        showLogo: Boolean? = null,
        text: String? = null,
        onClick: (() -> Unit)? = null,
    ): @Composable () -> Unit {
        runtimeCheck(
            value = showLogo != null || text != null,
        ) {
            "logoType or text must be not null"
        }
        if (showLogo == true) {
            runtimeCheck(
                value = text == null,
            ) {
                "로고와 텍스트를 동시에 표시할 수 없습니다"
            }
        }
        return {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .quackClickable(
                        rippleEnabled = false,
                        onClick = onClick,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (showLogo == true) {
                    QuackImage(
                        src = LogoIcon,
                        size = LogoIconSize,
                    )
                } else {
                    text?.let {
                        QuackText(
                            text = text,
                            style = CenterTypography,
                            singleLine = true,
                        )
                        QuackImage(
                            src = CenterTrailingIcon,
                            size = CenterIconSize,
                            tint = CenterIconTint,
                        )
                    }
                }
            }
        }
    }

    /**
     * trailing content 로 그려지는 컴포저블을 계산합니다.
     *
     * - [extraIcon] 과 [icon] 이 하나라도 들어왔다면 [text] 는 무시되며,
     *   `[extraIcon] [icon]` 순서로 배치됩니다.
     * - [text] 값이 입력되면 [extraIcon] 과 [icon] 값은 무시됩니다.
     *
     * @param icon 배치할 아이콘
     * @param extraIcon 추가로 배치할 아이콘
     * @param text 배치할 텍스트
     * @param onIconClick [icon] 이 클릭됐을 때 실행될 람다
     * @param onExtraIconClick [extraIcon] 이 클릭됐을 때 실행될 람다
     * @param onTextClick [text] 가 클릭됐을 때 실행될 람다
     *
     * @return 입력받는 인자로 계산된 trailing content 의 컴포저블
     */
    @SuppressLint("ComposableNaming")
    @Composable
    fun TrailingContent(
        icon: QuackIcon? = null,
        extraIcon: QuackIcon? = null,
        text: String? = null,
        onIconClick: (() -> Unit)? = null,
        onExtraIconClick: (() -> Unit)? = null,
        onTextClick: (() -> Unit)? = null,
    ): @Composable () -> Unit {
        runtimeCheck(
            value = icon != null || extraIcon != null || text != null,
        ) {
            "icon or extraIcon or text must be not null"
        }
        if (icon != null || extraIcon != null) {
            runtimeCheck(
                value = text == null,
            ) {
                "아이콘과 텍스트를 동시에 표시할 수 없습니다"
            }
        }
        if (text != null) {
            runtimeCheck(
                value = icon == null && extraIcon == null,
            ) {
                "텍스트와 아이콘을 동시에 표시할 수 없습니다"
            }
        }
        return {
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                text?.let {
                    QuackText(
                        modifier = Modifier
                            .quackClickable(
                                rippleEnabled = false,
                                onClick = onTextClick,
                            )
                            .padding(
                                paddingValues = TrailingTextPadding
                            ),
                        text = text,
                        style = TrailingTypography,
                        singleLine = true,
                    )
                }
                extraIcon?.let {
                    QuackImage(
                        src = extraIcon,
                        size = trailingIconSizeFor(
                            hasExtraIcon = true,
                        ),
                        onClick = onExtraIconClick,
                    )
                }
                icon?.let {
                    QuackImage(
                        src = icon,
                        size = trailingIconSizeFor(
                            hasExtraIcon = extraIcon != null,
                        ),
                        onClick = onIconClick,
                    )
                }
            }
        }
    }
}

/**
 * 덕키의 Top Navigation Bar 를 그립니다.
 * [QuackTopAppBar] 는 몇몇 중요한 특징이 있습니다.
 *
 * - 항상 상위 컴포저블의 가로 길이에 꽉차게 그립니다.
 * - [showLogoAtCenter] 이 true 라면 [centerText] 값은 무시됩니다.
 *   로고 리소스로는 [QuackIcon.TextLogo] 를 사용합니다.
 * - [centerText] 값이 있다면 [showLogoAtCenter] 값은 무시됩니다.
 *   또한 [centerText] 는 항상 trailing icon 으로 [QuackIcon.ArrowDown] 을 갖습니다.
 * - [trailingExtraIcon] 과 [trailingIcon] 이 하나라도 들어왔다면 [trailingText] 는 무시되며,
 *   `[trailingExtraIcon] [trailingIcon]` 순서로 배치됩니다.
 * - [trailingText] 값이 입력되면 [trailingExtraIcon] 과 [trailingIcon] 값은 무시됩니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param leadingIcon trailing content 로 배치할 아이콘
 * @param leadingText trailing content 로 배치할 텍스트. 선택적으로 값을 받습니다.
 * @param onLeadingIconClick [leadingIcon] 이 클릭됐을 때 실행될 람다
 * @param showLogoAtCenter center content 로 덕키의 로고를 배치할지 여부
 * @param centerText center content 에 로고 대신에 표시할 텍스트
 * @param onCenterClick center content 가 클릭됐을 때 실행될 람다
 * @param trailingIcon 배치할 아이콘
 * @param trailingExtraIcon 추가로 배치할 아이콘
 * @param trailingText 배치할 텍스트
 * @param onTrailingIconClick [trailingIcon] 이 클릭됐을 때 실행될 람다
 * @param onTrailingExtraIconClick [trailingExtraIcon] 이 클릭됐을 때 실행될 람다
 * @param onTrailingTextClick [trailingText] 가 클릭됐을 때 실행될 람다
 */
@Composable
public fun QuackTopAppBar(
    modifier: Modifier = Modifier,
    leadingIcon: QuackIcon,
    leadingText: String? = null,
    onLeadingIconClick: () -> Unit,
    showLogoAtCenter: Boolean? = null,
    centerText: String? = null,
    onCenterClick: (() -> Unit)? = null,
    trailingIcon: QuackIcon? = null,
    trailingExtraIcon: QuackIcon? = null,
    trailingText: String? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    onTrailingExtraIconClick: (() -> Unit)? = null,
    onTrailingTextClick: (() -> Unit)? = null,
): Unit = with(
    receiver = QuackTopAppBarDefaults,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = Height,
            )
            .background(
                color = BackgroundColor.composeColor,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        LeadingContent(
            icon = leadingIcon,
            text = leadingText,
            onIconClick = onLeadingIconClick,
        )
        CenterContent(
            showLogo = showLogoAtCenter,
            text = centerText,
            onClick = onCenterClick,
        )
        TrailingContent(
            icon = trailingIcon,
            extraIcon = trailingExtraIcon,
            text = trailingText,
            onIconClick = onTrailingIconClick,
            onExtraIconClick = onTrailingExtraIconClick,
            onTextClick = onTrailingTextClick,
        )
    }
}