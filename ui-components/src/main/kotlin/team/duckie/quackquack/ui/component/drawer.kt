import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import team.duckie.quackquack.ui.component.internal.QuackDrawerState

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */


@Composable
public fun QuackModalDrawer(
    drawerState: QuackDrawerState,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
): Unit = QuackModalDrawerImpl(
    drawerState = drawerState,
    drawerContent = drawerContent,
    content = content,
)

