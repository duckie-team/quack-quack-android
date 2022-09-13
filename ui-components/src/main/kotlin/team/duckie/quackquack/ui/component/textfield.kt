/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:NoLiveLiterals

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.common.npe
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

// https://github.com/sungbinland/duckie-quack-quack/blob/6e0701a29711f2850d9b0449a286e8c282672982/ui-components/src/main/kotlin/team/duckie/quackquack/ui/component/textfield.kt

/**
 * TextField 양옆에 추가되는 Decoration 들과의 간격
 *
 * ```
 * Decoration - TextField - Decoration
 * ```
 *
 * 에서 `-` 사이에 들어갈 간격 입니다.
 */
private val QuackTextFieldDecorationContentHorizontalPadding = 8.dp

@Composable
fun QuackBasicTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (text: String) -> Unit,
    textStyle: QuackTextStyle = QuackTextStyle.Body1,
    width: QuackWidth = QuackWidth.Fill,
    height: QuackHeight = QuackHeight.Wrap,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    var textFieldSize by remember {
        mutableStateOf(
            value = IntSize.Zero,
        )
    }
    val density = LocalDensity.current

    BasicTextField(
        modifier = modifier
            .applyQuackSize(
                width = width,
                height = height,
            )
            .background(
                color = Color.Red,
            )
            .onPlaced { layoutCoordinates ->
                with(density) {
                    println("onPlaced: ${layoutCoordinates.size}")
                    println("onPlacedHeight with Dp: ${layoutCoordinates.size.height.toDp()}")
                    textFieldSize = layoutCoordinates.size
                }
            },
        value = text,
        onValueChange = onTextChanged,
        // TextField 는 애니메이션 적용 X
        textStyle = textStyle.change(
            textAlign = TextAlign.Start,
        ).asComposeStyle(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        onTextLayout = { layoutResult ->
            // textFieldSize = layoutResult.size
            // size 에서 height 가 0으로 나옴
            println("onTextLayout: ${layoutResult.size}")
        },
        decorationBox = { textField ->
            QuackTextFieldDecorationBox(
                textFieldSize = textFieldSize,
                textField = textField,
                leadingContent = leadingContent,
                trailingContent = trailingContent,
            )
        },
    )
}

private const val QuackTextFieldLayoutId = "QuackTextFieldContent"
private const val QuackTextFieldLeadingContentLayoutId = "QuackTextFieldLeadingContent"
private const val QuackTextFieldTrailingContentLayoutId = "QuackTextFieldTrailingContent"

private fun Placeable.toDebugString() =
    "$width x $height / measured: $measuredWidth x $measuredHeight"

@Composable
private inline fun QuackTextFieldDecorationBox(
    textFieldSize: IntSize,
    textField: @Composable () -> Unit,
    noinline leadingContent: @Composable (() -> Unit)?,
    noinline trailingContent: @Composable (() -> Unit)?,
) {
    println(
        message = "textFieldSize: $textFieldSize",
    )
    val density = LocalDensity.current
    // QuackTextFieldDecorationContentHorizontalPadding 는 상수 값이라
    // remember 에 key 필요 없음
    val decorationItemGap = remember {
        with(
            receiver = density,
        ) {
            QuackTextFieldDecorationContentHorizontalPadding.roundToPx()
        }
    }

    Layout(
        modifier = Modifier
            .size(
                width = with(
                    receiver = density,
                ) {
                    textFieldSize.width.toDp().also {
                        println("textFieldSize.width.toDp(): $it")
                    }
                },
                height = with(
                    receiver = density,
                ) {
                    textFieldSize.height.toDp().also {
                        println("textFieldSize.height.toDp(): $it")
                    }
                },
            ),
        content = {
            if (leadingContent != null) {
                Box(
                    modifier = Modifier.layoutId(
                        layoutId = QuackTextFieldLeadingContentLayoutId,
                    ),
                    contentAlignment = Alignment.Center,
                    propagateMinConstraints = true,
                ) {
                    leadingContent()
                }
            }
            Box(
                modifier = Modifier.layoutId(
                    layoutId = QuackTextFieldLayoutId,
                ),
                contentAlignment = Alignment.Center,
                propagateMinConstraints = true,
            ) {
                textField()
            }
            if (trailingContent != null) {
                Box(
                    modifier = Modifier.layoutId(
                        layoutId = QuackTextFieldTrailingContentLayoutId,
                    ),
                    contentAlignment = Alignment.Center,
                    propagateMinConstraints = true,
                ) {
                    trailingContent()
                }
            }
        },
    ) { measurables, _ ->
        // decoration items 들은 height 는 TextField 와 일치해야 하고,
        // width 는 wrap_content 여야 함. 단, 최소 TextField 의 height 와는 같아야 함.
        // TODO: wrapContentWidth() 를 구현해야 함..
        /*val decorationItemConstraints = Constraints(
            minWidth = textFieldSize.height,
            maxWidth = Int.MAX_VALUE,
            minHeight = textFieldSize.height,
            maxHeight = textFieldSize.height,
        ).also {
            println("constraints: $it")
        }*/
        val decorationItemConstraints = Constraints.fixed(
            width = textFieldSize.height,
            height = textFieldSize.height,
        ).also {
            println("constraints: $it")
        }

        // leading content
        val leadingContentPlaceable = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldLeadingContentLayoutId
        }?.measure(
            constraints = decorationItemConstraints,
        )?.also {
            println("leadingContentPlaceable: ${it.toDebugString()}")
        }

        // trailing content
        val trailingContentPlaceable = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldTrailingContentLayoutId
        }?.measure(
            constraints = decorationItemConstraints,
        )?.also {
            println("trailingContentPlaceable: ${it.toDebugString()}")
        }

        val textFieldWidth = textFieldSize.width.let { _width ->
            var width = _width
            if (leadingContentPlaceable != null) {
                width -= leadingContentPlaceable.width + decorationItemGap
            }
            if (trailingContentPlaceable != null) {
                width -= trailingContentPlaceable.width + decorationItemGap
            }
            width.also {
                println(
                    """
                            ::: textFieldPlaceable debug :::
                            textFieldSize width: ${textFieldSize.width}
                            calc width int: $it
                            calc with dp: ${it.toDp()}
                            """.trimIndent()
                )
            }
        }.coerceAtLeast(
            minimumValue = 0,
        )
        // TextField
        val textFieldPlaceable = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldLayoutId
        }?.measure(
            constraints = Constraints(
                // TextField 의 가로 길이는 기본적으로 decoration 아이템들의 가로 길이를
                // 포함하고 있음. 만약 decoration 아이템이 존재한다면 TextField 의
                // 가로 길이를 decoration 아이템의 가로 길이만큼 줄여야 함.
                // 따라서 TextField 의 가로 길이에서 decoration 아이템의 가로 길이와
                // TextField 와 decoration 아이템 사이에 들어갈 간격 만큼 제외한 값으로
                // TextField 의 가로 길이로 설정함.

                // FIXED: 지금은 width 가 매번 텍스트가 입력될 때마다 유동적으로 텍스트 길이에 맞게
                // 변하고 있음. decoration item 사이로 match_content 가 되게 고정해야 함.
                minWidth = textFieldWidth,
                maxWidth = textFieldWidth,
                minHeight = 0,
                maxHeight = textFieldSize.height,
            )
        )?.also {
            println("textFieldPlaceable: ${it.toDebugString()}")
        } ?: npe(
            lazyMessage = {
                notFoundRequiredLayoutIdForQuackTextFieldMessage(
                    layoutId = QuackTextFieldLayoutId,
                )
            },
        )

        layout(
            width = textFieldSize.width,
            height = textFieldSize.height,
        ) {
            val textFieldStartOffset = leadingContentPlaceable?.width?.plus(
                other = decorationItemGap,
            ) ?: 0
            val textFieldYOffset = textFieldSize.height / 2 - textFieldPlaceable.height / 2

            leadingContentPlaceable?.place(
                x = 0,
                y = 0,
            )
            textFieldPlaceable.place(
                x = textFieldStartOffset.also {
                    println("textFieldStartOffset: $it")
                },
                y = textFieldYOffset,
            )
            trailingContentPlaceable?.place(
                x = (textFieldSize.width - trailingContentPlaceable.width).also {
                    println("trailingContentPlaceable x: $it")
                },
                y = 0,
            )
        }
    }
}

@Suppress("SameParameterValue")
private fun notFoundRequiredLayoutIdForQuackTextFieldMessage(
    layoutId: String,
) = """
    |QuackTextField 를 구성하는데 필요한 필수 layoutId 를 가진 컴포저블을
    |찾을 수 없습니다. 올바른 방법으로 QuackTextField 를 사용했는지 확인해 보세요.

    |필요한 layoutId: $layoutId
    """.trimMargin()
