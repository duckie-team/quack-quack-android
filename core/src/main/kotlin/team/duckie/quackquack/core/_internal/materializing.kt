/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.core._internal

import androidx.compose.ui.Modifier

internal fun materializerOf(modifier: Modifier): Pair<Modifier, List<QuackDataModifierModel>> {
    val quackDataModels = mutableListOf<QuackDataModifierModel>()
    val composeModifier = modifier.foldIn<Modifier>(Modifier) { acc, element ->
        if (element is QuackDataModifierModel) {
            quackDataModels += element
            acc
        } else {
            acc.then(element)
        }
    }

    return composeModifier to quackDataModels
}
