/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [DesignSystemIssueRegistry.kt] created by Ji Sungbin on 22. 8. 16. 오후 6:34
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

package team.duckie.quackquack.lint.quack

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

// 상수 값들 하드코딩 안하면 빌드 타임에서 레퍼런스를 참조하지 못해 빌드 에러뜸
class DesignSystemIssueRegistry : IssueRegistry() {
    override val issues = listOf(
        DesignSystemIssue,
    )

    override val api = CURRENT_API

    override val minApi = 12

    override val vendor = Vendor(
        vendorName = "QuackQuack",
        identifier = "team.duckie.quack:quack-lint-quack:{version}",
        feedbackUrl = "https://github.com/sungbinland/duckie-quack-quack/issues",
        contact = "https://github.com/sungbinland/duckie-quack-quack",
    )
}
