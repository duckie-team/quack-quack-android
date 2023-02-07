/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.playground.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object PreferenceConfigs {
    const val Name = "quack_playground_datastore"

    val FontScaleKey = floatPreferencesKey(
        name = "font_scale",
    )

    val AnimationDurationKey = intPreferencesKey(
        name = "animation_duration",
    )

    val ShowComponentBounds = booleanPreferencesKey(
        name = "component_bounds",
    )

    val AlwaysShowRipple = booleanPreferencesKey(
        name = "always_show_ripple",
    )
}

val Context.dataStore by preferencesDataStore(
    name = PreferenceConfigs.Name,
)
