/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.util.compose.snapshot.test

import android.app.Activity
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

public val getActivityViaRobolectric: Activity
  inline get() {
    val activityController = Robolectric.buildActivity(Activity::class.java)
      .also(ActivityController<Activity>::setup)
    return activityController.get()
  }
