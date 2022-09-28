/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "FunctionName",
    "NOTHING_TO_INLINE",
)

package team.duckie.quackquack.ui.benchmark.app

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import team.duckie.quackquack.ui.animation.QuackDefaultAnimationMillis
import team.duckie.quackquack.ui.color.QuackColor

inline fun ImmutableCollectionsBenchmark() {
    persistentListOf<Any>()
    emptyList<Any>().toPersistentList()
}

inline fun QuackAnimationSpecBenchmark() {
    QuackDefaultAnimationMillis
}

inline fun QuackColorBenchmark() {
    QuackColor.Black
    QuackColor.DuckieOrange
    QuackColor.Gray1
    QuackColor.Gray2
    QuackColor.Gray3
    QuackColor.Gray4
    QuackColor.OrangeRed
    QuackColor.White
}
