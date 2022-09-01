/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [BaseActivity.kt] created by Ji Sungbin on 22. 8. 31. 오전 7:21
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.base

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import land.sungbin.systemuicontroller.setSystemBarsColor
import team.duckie.quackquack.playground.util.PreferenceConfigs
import team.duckie.quackquack.playground.util.QuackFontScale
import team.duckie.quackquack.playground.util.dataStore
import team.duckie.quackquack.ui.animation.QuackAnimationMillis

open class BaseActivity : ComponentActivity() {
    private val isDarkMode by lazy {
        val uiMode = resources.configuration.uiMode
        (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

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

        lifecycleScope.launch {
            // whenStateAtLeast?
            // withStateAtLeast?
            // Coroutines + Lifecycle 학습 필요, 지금은 후순위
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                applicationContext.dataStore.data.collect { preference ->
                    QuackAnimationMillis = (preference[PreferenceConfigs.AnimationDurationKey]
                        ?: QuackAnimationMillis).coerceAtLeast(minimumValue = 0)
                    QuackFontScale = (preference[PreferenceConfigs.FontScaleKey]
                        ?: QuackFontScale).coerceAtLeast(minimumValue = 0.0)
                }
            }
        }
    }
}
