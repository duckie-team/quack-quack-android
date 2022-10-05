/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.component.QuackBottomSheet
import team.duckie.quackquack.ui.component.QuackBottomSheetItem
import team.duckie.quackquack.ui.component.QuackHeadlineBottomSheet
import team.duckie.quackquack.ui.component.QuackSimpleBottomSheet
import team.duckie.quackquack.ui.component.QuackSubtitleBottomSheet
import team.duckie.quackquack.ui.component.QuackTitle1

class BottomSheetPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackBottomSheet" to { QuackBottomSheetDemo() },
        "QuackSimpleBottomSheet" to { QuackSimpleBottomSheetDemo() },
        "QuackHeadlineBottomSheet" to { QuackHeadlineBottomSheetDemo() },
        "QuackSubtitleBottomSheet" to { QuackSubtitleBottomSheetDemo() },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "BottomSheet",
                    items = items,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuackBottomSheetDemo() {

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()
    QuackBottomSheet(
        useHandle = true,
        bottomSheetState = bottomSheetState,
        sheetContent = {
            Text("Hello World")
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {

                QuackTitle1(
                    text = "QuackBottomSheet",
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuackSimpleBottomSheetDemo() {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()
    QuackSimpleBottomSheet(
        bottomSheetState = bottomSheetState,
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                QuackTitle1(
                    text = "QuackSimpleBottomSheet",
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                )
            }
        },
        items = persistentListOf(
            QuackBottomSheetItem(
                title = "subtitle",
                isImportant = false,
            ),
            QuackBottomSheetItem(
                title = "subtitle",
                isImportant = false,
            ),
            QuackBottomSheetItem(
                title = "subtitle",
                isImportant = true,
            ),
        ),
        onClick = {

        },
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuackHeadlineBottomSheetDemo() {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()
    QuackHeadlineBottomSheet(
        bottomSheetState = bottomSheetState,
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                QuackTitle1(
                    text = "QuackHeadlineBottomSheet",
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                )
            }
        },
        items = persistentListOf(
            QuackBottomSheetItem(
                title = "subtitle",
                isImportant = false,
            ),
            QuackBottomSheetItem(
                title = "subtitle",
                isImportant = false,
            ),
            QuackBottomSheetItem(
                title = "subtitle",
                isImportant = true,
            ),
        ),
        headline = "Headline 2",
        onClick = {

        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuackSubtitleBottomSheetDemo() {

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()
    QuackSubtitleBottomSheet(
        bottomSheetState = bottomSheetState,
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                QuackTitle1(
                    text = "QuackSubtitleBottomSheet",
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                )
            }
        },
        items = persistentListOf(
            QuackBottomSheetItem(
                title = "subtitle",
                isImportant = false,
            ),
            QuackBottomSheetItem(
                title = "subtitle",
                isImportant = false,
            ),
            QuackBottomSheetItem(
                title = "subtitle",
                isImportant = true,
            ),
        ),
        subtitle = "subtitle",
        onClick = {

        },
    )

}
