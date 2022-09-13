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

// old codebase:
// https://github.com/sungbinland/duckie-quack-quack/blob/6e0701a29711f2850d9b0449a286e8c282672982/ui-components/src/main/kotlin/team/duckie/quackquack/ui/component/textfield.kt

/**
 * Space between Decorations added on both sides of TextField
 *
 * ```
 * Decoration - TextField - Decoration
 * ```
 *
 * In the [this](https://user-images.githubusercontent.com/40740128/189829870-cba93fd6-d5f4-4016-b826-c6093cfbb386.png) image, the decoration item is the part inside the orange rectangle,
 * and the TextField is the "placeholder" part.
 */
private val QuackTextFieldDecorationContentHorizontalPadding = 8.dp

/**
 * Draw the most basic QuackTextField.
 * Add only decoration items that fit QuackTextField to BasicTextField.
 *
 * The current implementation only works if the height is statically fixed.
 * That is, if you specify height as wrap_content, height is always displayed as 0.
 * This should be fixed.
 *
 * @param modifier [Modifier] to use to draw the QuackBasicTextField
 * @param text text to display
 * @param onTextChanged Callback to be invoked when new text is entered
 * @param textStyle The style of the text to be displayed.
 * @param width Width of QuackTextField
 * @param height height of QuackTextField
 * @param keyboardOptions keyboard options in QuackTextField
 * @param keyboardActions Keyboard actions in QuackTextField
 * @param leadingContent leading decoration content of QuackTextField
 * @param trailingContent trailing decoration content of QuackTextField
 */
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
                textFieldSize = layoutCoordinates.size
            },
        value = text,
        onValueChange = onTextChanged,
        // TextField's TextStyle should not be animated
        textStyle = textStyle.change(
            textAlign = TextAlign.Start,
        ).asComposeStyle(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        // TextField is always single line
        // TextArea is always multi line
        singleLine = true,
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

/**
 * A decoration box for drawing BasicTextField as QuackTextField.
 *
 * @param textFieldSize The size of QuackTextField layout
 * @param textField BasicTextField to be treated as QuackTextField
 * @param leadingContent The leading content of QuackTextField
 * @param trailingContent The trailing content of QuackTextField
 *
 * @see QuackTextFieldDecorationContentHorizontalPadding
 */
@Composable
private inline fun QuackTextFieldDecorationBox(
    textFieldSize: IntSize,
    textField: @Composable () -> Unit,
    noinline leadingContent: @Composable (() -> Unit)?,
    noinline trailingContent: @Composable (() -> Unit)?,
) {
    val density = LocalDensity.current
    // `QuackTextFieldDecorationContentHorizontalPadding` is a constant value,
    // so no need to remembered key
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
                    textFieldSize.width.toDp()
                },
                height = with(
                    receiver = density,
                ) {
                    textFieldSize.height.toDp()
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
        // The current implementation assumes that the size of the
        // decoration item is always square. However, the actual size of
        // the decoration item may not be square. See images below.
        //
        // [TextField decoration items - square version]
        // https://user-images.githubusercontent.com/40740128/189829870-cba93fd6-d5f4-4016-b826-c6093cfbb386.png
        //
        // [TextField decoration items - rectangular version]
        // https://user-images.githubusercontent.com/40740128/189829922-667b1297-86fa-4e86-9db0-56de1d3ac778.png
        //
        // However, the height of the decoration item is always the same as
        // the height of the TextField. Therefore, it is necessary to make
        // the width of the decoration item to wrap_content.
        val decorationItemConstraints = Constraints.fixed(
            width = textFieldSize.height,
            height = textFieldSize.height,
        )

        // leading content
        val leadingContentPlaceable = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldLeadingContentLayoutId
        }?.measure(
            constraints = decorationItemConstraints,
        )

        // trailing content
        val trailingContentPlaceable = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldTrailingContentLayoutId
        }?.measure(
            constraints = decorationItemConstraints,
        )

        // The width of the TextField is determined by subtracting the width of
        // both decoration items and the padding between the decoration item and
        // the TextField from the total size of the TextField's layout.
        val textFieldWidth = textFieldSize.width.let { _width ->
            var width = _width
            if (leadingContentPlaceable != null) {
                width -= leadingContentPlaceable.width + decorationItemGap
            }
            if (trailingContentPlaceable != null) {
                width -= trailingContentPlaceable.width + decorationItemGap
            }
            width
        }.coerceAtLeast(
            minimumValue = 0,
        )

        // TextField
        val textFieldPlaceable = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldLayoutId
        }?.measure(
            constraints = Constraints(
                // The width of the TextField layout basically includes the width
                // of decoration items. If there is a decoration item, the width of
                // the TextField should be reduced by the width of the decoration item.
                // Therefore, it is set as the width of the TextField by subtracting
                // the width of the decoration item and the space between the TextField
                // and the decoration item from the width of the TextField layout.
                minWidth = textFieldWidth,
                maxWidth = textFieldWidth,
                minHeight = 0,
                maxHeight = textFieldSize.height,
            ),
        ) ?: npe(
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

            // Center the TextField in the TextField's layout
            val textFieldYOffset = textFieldSize.height / 2 - textFieldPlaceable.height / 2

            leadingContentPlaceable?.place(
                x = 0,
                y = 0,
            )
            textFieldPlaceable.place(
                x = textFieldStartOffset,
                y = textFieldYOffset,
            )
            trailingContentPlaceable?.place(
                x = textFieldSize.width - trailingContentPlaceable.width,
                y = 0,
            )
        }
    }
}

/**
 * Return an exception message when it cannot find the layoutId
 * needed to measure the QuackTextField.
 *
 * @param layoutId The layoutId needed to measure the QuackTextField
 * @return The exception message
 */
@Suppress("SameParameterValue")
private fun notFoundRequiredLayoutIdForQuackTextFieldMessage(
    layoutId: String,
) = """
    |Could not find composable with the required layoutId for measurement QuackTextField.
    |Make sure you use QuackTextField in the right way.

    |Required layoutId: $layoutId
    """.trimMargin()
