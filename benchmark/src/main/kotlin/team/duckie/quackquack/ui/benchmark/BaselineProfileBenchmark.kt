/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.benchmark

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaselineProfileBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startupNoCompilation() {
        startup(CompilationMode.None())
    }

    @Test
    fun startupBaselineProfile() {
        startup(CompilationMode.Partial(baselineProfileMode = BaselineProfileMode.Require))
    }

    private fun startup(compilationMode: CompilationMode) {
        benchmarkRule.measureRepeated(
            packageName = AppPackageName,
            metrics = listOf(StartupTimingMetric()),
            iterations = 10,
            startupMode = StartupMode.COLD,
            compilationMode = compilationMode
        ) {
            pressHome()
            startActivityAndWait()
        }
    }
}
