/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.catalog

import android.app.Application
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        isDebugInspectorInfoEnabled = BuildConfig.DEBUG
    }
}
