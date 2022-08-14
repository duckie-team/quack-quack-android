/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:59
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UNUSED_VARIABLE")

package land.sungbin.duckie.quackquack.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import land.sungbin.duckie.quackquack.ui.component.QuackLargeButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var enabled by remember { mutableStateOf(true) }
            val toast = rememberToast()

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "Click Me!",
                    onValueChange = {},
                )
                QuackLargeButton(
                    enabled = enabled,
                    imeSupport = true,
                    text = "QuackButton",
                ) {
                    enabled = !enabled
                }
            }
        }
    }
}
