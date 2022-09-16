/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.lint.core

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Detector
import team.duckie.quackquack.common.lint.IssueProvider

/**
 * QuackQuack 의 Core 카테고리 린트 [Detector] 들을 린트 시스템 등록해 주는 [IssueRegistry] 클래스
 */
class CoreIssueRegistry : IssueProvider(
    issues = listOf(
        MutableCollectionPublicIssue,
        KDocFieldsIssue,
        SpecifyLambdaParamsNameIssue,
    ),
)
