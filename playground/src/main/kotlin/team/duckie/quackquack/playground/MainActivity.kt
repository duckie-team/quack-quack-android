/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 8. 18. 오전 2:32
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 8. 18. 오전 2:14
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:59
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UNUSED_VARIABLE")

package team.duckie.quackquack.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.layout.QuackColumn

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Button()
        }
    }
}

@Composable
private fun Button() {
    throw NotImplementedError("stub")
}

@Composable
private fun QuackColumnTest() {
    QuackColumn {
        footer {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .background(color = Color.Black),
            )
        }
        content {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(color = Color.Blue),
            )
        }
    }
}

@Composable
private fun QuackButtonTest() {
    var enabled by remember { mutableStateOf(true) }

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
            imeAnimation = true,
            text = "QuackButton",
            onClick = {
                enabled = !enabled
            }
        )
    }
}
