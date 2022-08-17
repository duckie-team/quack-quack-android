/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [DesignSystemIssueRegistry.kt] created by Ji Sungbin on 22. 8. 18. 오전 2:20
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [DesignSystemIssueRegistry.kt] created by Ji Sungbin on 22. 8. 18. 오전 2:14
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [DesignSystemIssueRegistry.kt] created by Ji Sungbin on 22. 8. 16. 오후 6:34
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.lint.compose

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

@Suppress("UnstableApiUsage") // IssueRegistry
class DesignSystemIssueRegistry : IssueRegistry() {
    override val issues = listOf(DesignSystemDetector.ISSUE)

    override val api = CURRENT_API

    override val minApi = 12

    override val vendor = Vendor(
        vendorName = "QuackQuack",
        identifier = "team.duckie.quack:quack-lint-compose:{version}",
        feedbackUrl = "https://github.com/sungbinland/duckie-quack-quack/issues",
        contact = "https://github.com/sungbinland/duckie-quack-quack",
    )
}
