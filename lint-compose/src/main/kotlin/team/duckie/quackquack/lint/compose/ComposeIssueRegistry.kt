/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [PreferredImmutableCollectionsIssueRegistry.kt] created by Ji Sungbin on 22. 8. 19. 오전 9:13
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

package team.duckie.quackquack.lint.compose

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Detector

/**
 * QuackQuack 의 Jetpack Compose 카테고리 린트 [Detector] 들을
 * 린트 시스템 등록해 주는 [IssueRegistry] 클래스
 */
// NOTE: 상수 값들 하드코딩 안하면 빌드 타임에서 레퍼런스를 참조하지 못해 빌드 에러뜸
class ComposeIssueRegistry : IssueRegistry() {
    override val issues = listOf(
        PreferredImmutableCollectionsIssue,
        TrailingCommaIssue,
        FixedModifierOrderIssue,
    )

    override val api = CURRENT_API

    override val minApi = 12

    override val vendor = Vendor(
        vendorName = "QuackQuack",
        feedbackUrl = "https://github.com/sungbinland/duckie-quack-quack/issues",
        contact = "https://github.com/sungbinland/duckie-quack-quack",
    )
}
