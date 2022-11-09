/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("UnstableApiUsage")

package team.duckie.quackquack.lint.quack

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Detector

/**
 * QuackQuack 의 DesignSystem 린트 [Detector] 를
 * 린트 시스템 등록해 주는 [IssueRegistry] 클래스
 */
// NOTE: 상수 값들 하드코딩 안하면 빌드 타임에서 레퍼런스를 참조하지 못해 빌드 에러뜸
class DesignSystemIssueRegistry : IssueRegistry() {
    override val issues = listOf(
        DesignSystemIssue,
    )

    override val api = CURRENT_API

    override val minApi = 12

    override val vendor = Vendor(
        vendorName = "QuackQuack",
        feedbackUrl = "https://github.com/sungbinland/duckie-quack-quack/issues",
        contact = "https://github.com/sungbinland/duckie-quack-quack",
    )
}
