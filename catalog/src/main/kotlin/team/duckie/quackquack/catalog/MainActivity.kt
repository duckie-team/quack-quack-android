/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.catalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                val testCasaComponents = remember {
                    persistentListOf<Pair<String, @Composable () -> Unit>>(
                        "one" to { Text(text = "one") },
                        "two" to { Text(text = "two") },
                        "three" to { Text(text = "three") },
                        "four" to { Text(text = "four") },
                        "five" to { Text(text = "five") },
                        "six" to { Text(text = "six") },
                        "seven" to { Text(text = "seven") },
                        "eight" to { Text(text = "eight") },
                        "nine" to { Text(text = "nine") },
                        "ten" to { Text(text = "ten") },
                    )
                }
                val testCasaModels = remember {
                    persistentListOf(
                        CasaModel(
                            name = "one",
                            path = "",
                            domain = "one",
                            kdocDefaultSection = "kdoc",
                            components = testCasaComponents,
                        ),
                        CasaModel(
                            name = "two",
                            path = "",
                            domain = "two",
                            kdocDefaultSection = "kdoc",
                            components = testCasaComponents,
                        ),
                        CasaModel(
                            name = "three",
                            path = "",
                            domain = "three",
                            kdocDefaultSection = "kdoc",
                            components = testCasaComponents,
                        ),
                        CasaModel(
                            name = "four",
                            path = "",
                            domain = "four",
                            kdocDefaultSection = "kdoc",
                            components = testCasaComponents,
                        ),
                        CasaModel(
                            name = "five",
                            path = "",
                            domain = "five",
                            kdocDefaultSection = "kdoc",
                            components = testCasaComponents,
                        ),
                        CasaModel(
                            name = "six",
                            path = "",
                            domain = "six",
                            kdocDefaultSection = "kdoc",
                            components = testCasaComponents,
                        ),
                        CasaModel(
                            name = "seven",
                            path = "",
                            domain = "seven",
                            kdocDefaultSection = "kdoc",
                            components = testCasaComponents,
                        ),
                        CasaModel(
                            name = "eight",
                            path = "",
                            domain = "eight",
                            kdocDefaultSection = "kdoc",
                            components = testCasaComponents,
                        ),
                        CasaModel(
                            name = "nine",
                            path = "",
                            domain = "nine",
                            kdocDefaultSection = "kdoc",
                            components = testCasaComponents,
                        ),
                        CasaModel(
                            name = "ten",
                            path = "",
                            domain = "ten",
                            kdocDefaultSection = "kdoc",
                            components = testCasaComponents,
                        ),
                    )
                }
                CasaScreen(models = testCasaModels)
            }
        }
    }
}
