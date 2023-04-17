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
import com.google.auto.service.AutoService

@AutoService(IssueRegistry::class)
class AideIssueRegistry : IssueRegistry() {
    override val issues = listOf(AideDecorateModifierDetector.ISSUE)
    override val api = CURRENT_API
    override val vendor = Vendor(
        vendorName = "DUCKIE",
        identifier = "duckie-team/quack-quack-android",
        feedbackUrl = "https://github.com/duckie-team/quack-quack-android/issues",
        contact = "ji@sungb.in",
    )
}
