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

// TODO: 문서화
@Immutable
public data class CasaModel(
    val name: String,
    val domain: String,
    val kdocDefaultSection: String,
    val components: ImmutableList<Pair<String, @Composable () -> Unit>>,
) {
    public fun toSourceUrl(config: CasaConfig): String {
        return "${config.baseSourceUrl}/$domain"
    }
}
