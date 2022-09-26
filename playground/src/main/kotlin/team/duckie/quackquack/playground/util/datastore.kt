/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object PreferenceConfigs {
    const val Name = "quack_playground_datastore"

    val FontScaleKey = doublePreferencesKey(
        name = "font_scale",
    )

    val AnimationDurationKey = intPreferencesKey(
        name = "animation_duration",
    )

    val ShowComponentsBound = booleanPreferencesKey(
        name = "components_bound",
    )
}

val Context.dataStore by preferencesDataStore(
    name = PreferenceConfigs.Name,
)
