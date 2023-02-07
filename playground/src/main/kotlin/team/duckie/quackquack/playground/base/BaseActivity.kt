/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.base

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import land.sungbin.systemuicontroller.setNavigationBarColor
import land.sungbin.systemuicontroller.setStatusBarColor

open class BaseActivity : ComponentActivity() {
    private val isDarkMode by lazy {
        val uiMode = resources.configuration.uiMode
        (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setStatusBarColor(
            color = Color.Transparent.toArgb(),
            darkIcons = !isDarkMode
        )
        setNavigationBarColor(
            color = Color.Black.copy(
                alpha = 0.3f,
            ).toArgb(),
            darkIcons = false,
        )

        onBackPressedDispatcher.addCallback(
            owner = this,
        ) {
            finish()
            overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
            )
        }
    }
}
