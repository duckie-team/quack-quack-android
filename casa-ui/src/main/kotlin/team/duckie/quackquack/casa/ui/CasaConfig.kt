/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.casa.ui

import androidx.compose.runtime.Immutable

// TODO: 문서화
@Immutable
public data class CasaConfig(
    public val casaName: String = "QuackQuack catalog",
    public val bugReportUrl: String = "https://github.com/duckie-team/quack-quack-android/issues/new?assignees=jisungbin&labels=bug&template=bug_report.md&title=",
    public val baseSourceUrl: String = "https://github.com/duckie-team/quack-quack-android/tree/2.x.x/core/src/main/kotlin",
)
