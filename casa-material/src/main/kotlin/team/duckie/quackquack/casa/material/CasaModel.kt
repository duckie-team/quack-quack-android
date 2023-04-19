/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.casa.material

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

/**
 * casa-ui에 표시할 모델 정보를 나타냅니다.
 *
 * @param name 모델 이름
 * @param domain 모델 도메인
 * @param kdocDefaultSection 모델의 KDoc Default Section 값
 * @param components 모델에 속한 컴포넌트 모음. `Pair<컴포넌트명, 컴포넌트 람다식>` 요소를 가집니다.
 */
@Immutable
public data class CasaModel(
    val name: String,
    val domain: String,
    val kdocDefaultSection: String,
    val components: ImmutableList<Pair<String, @Composable () -> Unit>>,
) {
    /**
     * [CasaConfig.baseSourceUrl]에 [코드 파일 경로][domain]를 이어서 반환합니다.
     */
    public fun toSourceUrl(config: CasaConfig): String {
        return "${config.baseSourceUrl}/$domain"
    }
}
