/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground

import androidx.compose.runtime.Composable
import team.duckie.quackquack.playground.realworld.QuackLarge40WhiteButtonDemo
import team.duckie.quackquack.playground.realworld.QuackLargeButtonDemo
import team.duckie.quackquack.playground.realworld.QuackLargeWhiteButtonDemo
import team.duckie.quackquack.playground.realworld.QuackMediumBorderToggleButtonDemo
import team.duckie.quackquack.playground.realworld.QuackSmallBorderToggleButtonDemo
import team.duckie.quackquack.playground.realworld.QuackSmallButtonDemo
import team.duckie.quackquack.playground.realworld.QuackToggleChipDemo

@Composable
internal fun Benchmark() {
    QuackLargeButtonDemo()
    QuackLargeWhiteButtonDemo()
    QuackLarge40WhiteButtonDemo()
    QuackMediumBorderToggleButtonDemo()
    QuackSmallButtonDemo()
    QuackSmallBorderToggleButtonDemo()
    QuackToggleChipDemo()
}
