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
import team.duckie.quackquack.casa.ui.theme.CasaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CasaTheme {
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
                            name = "two",
                            path = "",
                            domain = "two",
                            kdocDefaultSection = "kdoc",
                            components = persistentListOf(),
                        ),
                        CasaModel(
                            name = "three",
                            path = "",
                            domain = "three",
                            kdocDefaultSection = "kdoc",
                            components = persistentListOf(),
                        ),
                        CasaModel(
                            name = "four",
                            path = "",
                            domain = "four",
                            kdocDefaultSection = "kdoc",
                            components = persistentListOf(),
                        ),
                        CasaModel(
                            name = "five",
                            path = "",
                            domain = "five",
                            kdocDefaultSection = "kdoc",
                            components = persistentListOf(),
                        ),
                        CasaModel(
                            name = "six",
                            path = "",
                            domain = "six",
                            kdocDefaultSection = "kdoc",
                            components = persistentListOf(),
                        ),
                        CasaModel(
                            name = "seven",
                            path = "",
                            domain = "seven",
                            kdocDefaultSection = "kdoc",
                            components = persistentListOf(),
                        ),
                        CasaModel(
                            name = "eight",
                            path = "",
                            domain = "eight",
                            kdocDefaultSection = "kdoc",
                            components = persistentListOf(),
                        ),
                        CasaModel(
                            name = "nine",
                            path = "",
                            domain = "nine",
                            kdocDefaultSection = "kdoc",
                            components = persistentListOf(),
                        ),
                        CasaModel(
                            name = "ten",
                            path = "",
                            domain = "ten",
                            kdocDefaultSection = "kdoc",
                            components = persistentListOf(),
                        ),
                    )
                }
                CasaScreen(models = testCasaModels)
            }
        }
    }
}
