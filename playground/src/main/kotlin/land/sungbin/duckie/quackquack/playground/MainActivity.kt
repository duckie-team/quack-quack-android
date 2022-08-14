/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:59
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package land.sungbin.duckie.quackquack.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import land.sungbin.duckie.quackquack.ui.color.QuackColor
import land.sungbin.duckie.quackquack.ui.component.QuackButton
import land.sungbin.duckie.quackquack.ui.typography.QuackTextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val toast = rememberToast()

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                QuackButton(
                    backgroundColor = QuackColor.PumpkinOrange,
                    radius = 12.dp,
                    text = "QuackButton",
                    textStyle = QuackTextStyle.M1420
                ) {
                    toast("Bye, world!")
                }
            }
        }
    }
}
