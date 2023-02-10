/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.playground.util.rememberToast
import team.duckie.quackquack.ui.component.QuackDialog

private const val DialogTitle = "Dialog Title"
private const val DialogMessage = "Dialog Message\nDialog Message"
private const val DialogFullWidthButtonText = "Full Width Button"

class DialogPlayground : PlaygroundActivity(name = "Dialog") {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackDialogNoButtonDemo.name to { QuackDialogNoButtonDemo() },
        ::QuackDialogFullWidthButtonDemo.name to { QuackDialogFullWidthButtonDemo() },
        ::QuackDialogRightButtonDemo.name to { QuackDialogRightButtonDemo() },
        ::QuackDialogFullButtonDemo.name to { QuackDialogFullButtonDemo() },
    )
}

@Composable
fun QuackDialogNoButtonDemo() {
    var state by remember { mutableStateOf(false) }

    QuackDialog(
        title = DialogTitle,
        message = DialogMessage,
        visible = state,
        onDismissRequest = { state = false },
    )

    Button(onClick = { state = true }) {
        Text(text = "Show Dialog")
    }
}

@Composable
fun QuackDialogFullWidthButtonDemo() {
    var state by remember { mutableStateOf(false) }
    val toast = rememberToast()

    QuackDialog(
        title = DialogTitle,
        message = DialogMessage,
        visible = state,
        onDismissRequest = { state = false },
        fullWidthButtonText = DialogFullWidthButtonText,
        fullWidthButtonOnClick = { toast("QuackDialogFullWidthButtonDemo") },
    )

    Button(onClick = { state = true }) {
        Text(text = "Show Dialog")
    }
}

@Composable
fun QuackDialogRightButtonDemo() {
    var state by remember { mutableStateOf(false) }
    val toast = rememberToast()

    QuackDialog(
        title = DialogTitle,
        message = DialogMessage,
        visible = state,
        onDismissRequest = { state = false },
        rightButtonText = "Button",
        rightButtonOnClick = { toast("QuackDialogRightButtonDemo") },
    )

    Button(onClick = { state = true }) {
        Text(text = "Show Dialog")
    }
}

@Composable
fun QuackDialogFullButtonDemo() {
    var state by remember { mutableStateOf(false) }
    val toast = rememberToast()

    QuackDialog(
        title = DialogTitle,
        message = DialogMessage,
        visible = state,
        onDismissRequest = { state = false },
        rightButtonText = "Right Button",
        rightButtonOnClick = { toast("QuackDialogFullButtonDemo") },
        leftButtonText = "Left Button",
        leftButtonOnClick = { toast("QuackDialogFullButtonDemo") },
    )

    Button(onClick = { state = true }) {
        Text(text = "Show Dialog")
    }
}
