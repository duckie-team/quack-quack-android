/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("unused")

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.describeApi
import com.google.auto.service.AutoService

@AutoService(value = [IssueRegistry::class])
class CoreAideIssueRegistry : IssueRegistry() {
    override val issues = listOf(CoreAideDecorateModifierDetector.ISSUE)

    override val api = CURRENT_API

    /**
     * works with Studio 4.0 or later; see [describeApi].
     */
    override val minApi = 7

    override val vendor = Vendor(
        vendorName = "DUCKIE",
        identifier = "duckie-team/quack-quack-android",
        feedbackUrl = "https://github.com/duckie-team/quack-quack-android/issues",
        contact = "ji@sungb.in",
    )
}
