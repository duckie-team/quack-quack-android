/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [CoreIssueRegistry.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:57
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.lint.core

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Detector

/**
 * QuackQuack 의 Core 카테고리 린트 [Detector] 들을
 * 린트 시스템 등록해 주는 [IssueRegistry] 클래스
 */
// 상수 값들 하드코딩 안하면 빌드 타임에서 레퍼런스를 참조하지 못해 빌드 에러뜸
class CoreIssueRegistry : IssueRegistry() {
    override val issues = listOf(
        MutableCollectionPublicIssue,
    )

    override val api = CURRENT_API

    override val minApi = 12

    override val vendor = Vendor(
        vendorName = "QuackQuack",
        feedbackUrl = "https://github.com/sungbinland/duckie-quack-quack/issues",
        contact = "https://github.com/sungbinland/duckie-quack-quack",
    )
}
