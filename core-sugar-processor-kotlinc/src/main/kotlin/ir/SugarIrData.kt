/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import org.jetbrains.kotlin.ir.expressions.IrExpressionBody

internal data class SugarIrData(
    // val refer: String,
    val defaultValue: IrExpressionBody,
)
