/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [Showkase.kt] created by Ji Sungbin on 22. 8. 23. 오전 12:48
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.airbnb.android.showkase.annotation.ShowkaseColor
import com.airbnb.android.showkase.annotation.ShowkaseRoot
import com.airbnb.android.showkase.annotation.ShowkaseRootModule
import com.airbnb.android.showkase.annotation.ShowkaseTypography

@ShowkaseRoot
class ShowkaseData : ShowkaseRootModule

@ShowkaseColor(name = "Orange")
val QuackOrange = Color(255, 131, 0)

@ShowkaseColor(name = "Gray")
val QuackGray = Color(168, 168, 168)

@ShowkaseTypography(name = "Big")
val QuackBig = TextStyle(fontSize = 20.sp)

@ShowkaseTypography(name = "Small")
val QuackSmall = TextStyle(fontSize = 10.sp)
