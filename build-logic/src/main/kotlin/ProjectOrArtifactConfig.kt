/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

import org.gradle.api.artifacts.ProjectDependency

/**
 * 로컬 모듈 의존성을 가지는 모듈을 publishing하면 해당 아티팩트를
 * 다운받았을 때 아티팩트 내부에서 참조하고 있는 로컬 모듈을 불러오지 못해
 * 싱크 에러가 발생합니다. 예를 들어 다음과 같은 상황에 해당합니다.
 *
 * ```
 * // file:build.gradle
 *
 * dependencies {
 *   implementation projects.util.common // local-module
 *   implementation projects.compose.ui // local-module
 * }
 * ```
 *
 * 이를 대응하기 위해 [useArtifact]가 true라면 로컬 모듈을
 * publishing된 아티팩트로 변환하여 참조합니다.
 *
 * ```
 * // file:build.gradle
 *
 * dependencies {
 *   implementation 'team.duckie.quackquack.util:util-common:$version'
 *   implementation 'team.duckie.quackquack.compose:compose-ui:$version'
 * }
 * ```
 *
 * 즉, 모듈을 안전하게 publishing하기 위해선 publishing 전에 먼저 [useArtifact]를
 * true로 설정해야 합니다.
 *
 * 로컬 모듈을 아티팩트로 변환하는 로직은 [ProjectDependency.orArtifact]를
 * 참고하세요.
 */
const val useArtifact = false
