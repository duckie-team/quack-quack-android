/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
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
            QuackImageBenchmark()
            QuackTabBenchmark()
            QuackTagBenchmark()
            QuackTextFieldBenchmark()
            QuackToggleBenchmark()
            QuackTypographyBenchmark()
            QuackHeightBenchmark()
            QuackWidthBenchmark()
            QuackIconBenchmark()
            QuackTextStyleBenchmark()
            QuackBottomSheetBenchmark()
            QuackBottomNavigationBenchmark()
            QuackDropDownBenchmark()
            QuackLabelBenchmark()
            QuackSelectableImageBenchmark()
            QuackTopAppBarBenchmark()
            QuackModalDrawerBenchmark()
            QuackDividerBenchmark()
        }
    }
}
