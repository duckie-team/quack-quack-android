/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.benchmark.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImmutableCollectionsBenchmark()
            QuackAnimationSpecBenchmark()
            QuackColorBenchmark()
            QuackButtonBenchmark()
            QuackFabBenchmark()
            QuackImageBenchMark()
            QuackTabBenchMark()
            QuackTagBenchMark()
            QuackTextFieldBenchMark()
            QuackToggleBenchMark()
            QuackTypographyBenchMark()
            QuackHeightBenchMark()
            QuackWidthBenchMark()
            QuackIconBenchMark()
            QuackTextStyleBenchMark()
        }
    }
}
