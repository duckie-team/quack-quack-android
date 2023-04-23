/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.catalog

import android.app.Activity
import android.widget.Toast

internal fun Activity.toast(message: String): Toast {
    return Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).also(Toast::show)
}
