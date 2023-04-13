/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.casa.material.CasaModel
import team.duckie.quackquack.casa.ui.CasaScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val testCasaModels = remember {
                persistentListOf(
                    CasaModel(
                        name = "one",
                        path = "",
                        domain = "one",
                        kdocDefaultSection = "kdoc",
                        components = persistentListOf(),
                    ),
                    CasaModel(
                        name = "one",
                        path = "",
                        domain = "two",
                        kdocDefaultSection = "kdoc",
                        components = persistentListOf(),
                    ),
                    CasaModel(
                        name = "one",
                        path = "",
                        domain = "three",
                        kdocDefaultSection = "kdoc",
                        components = persistentListOf(),
                    ),
                    CasaModel(
                        name = "one",
                        path = "",
                        domain = "four",
                        kdocDefaultSection = "kdoc",
                        components = persistentListOf(),
                    ),
                    CasaModel(
                        name = "one",
                        path = "",
                        domain = "five",
                        kdocDefaultSection = "kdoc",
                        components = persistentListOf(),
                    ),
                    CasaModel(
                        name = "one",
                        path = "",
                        domain = "five",
                        kdocDefaultSection = "kdoc",
                        components = persistentListOf(),
                    ),
                    CasaModel(
                        name = "one",
                        path = "",
                        domain = "five",
                        kdocDefaultSection = "kdoc",
                        components = persistentListOf(),
                    ),
                    CasaModel(
                        name = "one",
                        path = "",
                        domain = "five",
                        kdocDefaultSection = "kdoc",
                        components = persistentListOf(),
                    ),
                    CasaModel(
                        name = "one",
                        path = "",
                        domain = "five",
                        kdocDefaultSection = "kdoc",
                        components = persistentListOf(),
                    ),
                )
            }
            CasaScreen(models = testCasaModels)
        }
    }
}
