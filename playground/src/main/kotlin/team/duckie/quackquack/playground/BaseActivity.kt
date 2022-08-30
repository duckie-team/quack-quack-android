/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [BaseActivity.kt] created by Ji Sungbin on 22. 8. 31. 오전 7:21
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.core.view.WindowCompat
import land.sungbin.systemuicontroller.setSystemBarsColor

open class BaseActivity : ComponentActivity() {
    private val isDarkMode by lazy {
        val uiMode = resources.configuration.uiMode
        (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setSystemBarsColor(
            color = Color.TRANSPARENT,
            darkIcons = !isDarkMode
        )
        onBackPressedDispatcher.addCallback(
            owner = this,
        ) {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}
