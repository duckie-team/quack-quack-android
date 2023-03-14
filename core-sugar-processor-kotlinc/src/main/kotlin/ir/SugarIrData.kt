/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction

internal data class SugarIrData(
    val refer: String,
) {
    init {
        TODO()
    }

    companion object {
        fun from(declaration: IrSimpleFunction): SugarIrData {
            return TODO()
        }
    }
}
