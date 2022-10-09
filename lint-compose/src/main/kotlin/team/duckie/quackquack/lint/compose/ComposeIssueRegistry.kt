/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

package team.duckie.quackquack.lint.compose

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Detector
import team.duckie.quackquack.common.lint.IssueProvider

/**
 * QuackQuack 의 Jetpack Compose 카테고리 린트 [Detector] 들을 린트 시스템 등록해 주는 [IssueRegistry] 클래스
 */
class ComposeIssueRegistry : IssueProvider(
    issues = listOf(
        PreferredImmutableCollectionsIssue,
        TrailingCommaIssue,
        FixedModifierOrderIssue,
        // NewLineArgumentIssue,
        SpecifyAnimationSpecIssue,
    ),
)
