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
import androidx.compose.ui.Modifier
import team.duckie.quackquack.core.component.QuackText
import team.duckie.quackquack.core.material.QuackTypography
import team.duckie.quackquack.core.theme.QuackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuackTheme {
                QuackText(
                    modifier = Modifier.highlight(
                        highlights = listOf(
                            "one" to { },
                            "two" to { },
                            "three" to { },
                        ),
                    ),
                    text = "one two three four five",
                    typography = QuackTypography.Body1,
                )
            }
        }
    }
}
