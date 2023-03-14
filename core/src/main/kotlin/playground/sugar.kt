/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:OptIn(SugarCompilerApi::class)

package playground

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import team.duckie.quackquack.sugar.annotation.Imports
import team.duckie.quackquack.sugar.annotation.SugarCompilerApi
import team.duckie.quackquack.sugar.annotation.SugarToken
import team.duckie.quackquack.sugar.annotation.sugar

@SugarToken
public class Greet private constructor(public val name: String) {
    public companion object {
        public val Hi: Greet = Greet("hi~")
        public val Bye: Greet = Greet("bye~")
    }
}

public interface Gender {
    public val value: String
}

private object Male : Gender {
    override val value: String = "male"
}

@Composable
public fun Greet(
    greet: Greet = sugar(),
    @Imports(Male::class) gender: Gender = Male,
) {
    Text(text = "greeting ${greet.name} by $gender")
}
