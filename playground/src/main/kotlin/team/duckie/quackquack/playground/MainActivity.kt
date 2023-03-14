/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.playground

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import team.duckie.quackquack.core.component.QuackText
import team.duckie.quackquack.core.component.highlight
import team.duckie.quackquack.core.component.sugar.Test
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
                            "test" to ::toast,
                            "hi" to ::toast,
                        ),
                    ),
                    text = "test hi bye",
                    typography = QuackTypography.Body1,
                )
                Test()
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }
}
