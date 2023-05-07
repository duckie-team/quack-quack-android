/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("MaximumLineLength", "MaxLineLength")

package team.duckie.quackquack.casa.material

import androidx.compose.runtime.Immutable

/**
 * casa-ui에서 사용할 UI 정보를 제공합니다.
 *
 * @param casaName catalog 앱 이름
 * @param bugReportUrl 버그 신고시 이동할 웹 링크
 * @param baseSourceUrl 소스코드 보기에 기본으로 사용할 웹 링크. [baseSourceUrl]에 코드 파일 경로를
 *     이어서 사용합니다.
 */
@Immutable
public data class CasaConfig(
  public val casaName: String = "QuackQuack catalog",
  public val bugReportUrl: String = "https://github.com/duckie-team/quack-quack-android/issues/new?assignees=jisungbin&labels=bug&template=bug_report.md&title=",
  public val baseSourceUrl: String = "https://github.com/duckie-team/quack-quack-android/tree/main/core/src/main/kotlin",
)
