/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
)

package team.duckie.quackquack.playground.base

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.zIndex
import androidx.datastore.preferences.core.edit
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlin.math.roundToInt
import kotlin.reflect.KClass
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch
import team.duckie.quackquack.playground.R
import team.duckie.quackquack.playground.util.PreferenceConfigs
import team.duckie.quackquack.playground.util.dataStore
import team.duckie.quackquack.playground.util.rememberToast
import team.duckie.quackquack.ui.animation.QuackAnimationMillis
import team.duckie.quackquack.ui.animation.QuackDefaultAnimationMillis
import team.duckie.quackquack.ui.modifier.QuackAlwaysShowRipple
import team.duckie.quackquack.ui.modifier.QuackDefaultAlwaysShowRipple
import team.duckie.quackquack.ui.modifier.quackClickable

const val DefaultFontScale = 1f

/**
 * ????????? ??????????????? font scale
 */
var fontScale by mutableStateOf(
    value = DefaultFontScale,
)

const val DefaultShowComponentBounds = false

/**
 * ??????????????? ??????(?????????)??? ???????????? ??????
 */
var showComponentBounds by mutableStateOf(
    value = DefaultShowComponentBounds,
)

/**
 * ??????????????? ?????????????????? ?????? ???????????????.
 *
 * @param withFinish ??????????????? ????????? ??? ?????? ??????????????? finish ?????? ??????
 * @param intentBuilder ????????? ??????????????? [Intent] ??? ????????? ??????
 */
private inline fun Activity.startActivityWithAnimation(
    withFinish: Boolean = false,
    intentBuilder: () -> Intent,
) {
    startActivity(intentBuilder())
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    if (withFinish) {
        finish()
    }
}

/**
 * Playground ?????? ????????? ?????????????????? ???????????????.
 * ?????? ????????????????????? ????????? ????????? ?????????????????? ????????????.
 *
 * @param title Playground ??? ???????????? ????????? ??????????????? ?????????
 * @param activities Playground ??? ????????? ???????????????
 */
@Composable
fun PlaygroundActivities(
    title: String = "QuackQuack Playground",
    activities: ImmutableList<KClass<out PlaygroundActivity>>,
) {
    var playgroundSettingAlertVisible by remember {
        mutableStateOf(
            value = false,
        )
    }
    val currentActivity = LocalContext.current as Activity

    PlaygroundSettingDialog(
        visible = playgroundSettingAlertVisible,
        onDismissRequest = {
            playgroundSettingAlertVisible = !playgroundSettingAlertVisible
        },
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                    )
                }
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding(),
                ),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            ),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp + padding.calculateBottomPadding(),
            ),
        ) {
            stickyHeader {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        playgroundSettingAlertVisible = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                    ),
                ) {
                    Text(
                        text = "Playground ??????",
                    )
                }
            }

            items(
                items = activities,
                key = { activity ->
                    activity.simpleName ?: activity
                }
            ) { activity ->
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        currentActivity.startActivityWithAnimation {
                            Intent(
                                currentActivity,
                                activity.java,
                            )
                        }
                    },
                ) {
                    Text(
                        text = (activity.simpleName ?: activity.toString()).removeSuffix(
                            suffix = "Playground",
                        ),
                    )
                }
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        currentActivity.startActivityWithAnimation {
                            Intent(
                                currentActivity,
                                OssLicensesMenuActivity::class.java,
                            )
                        }
                    },
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.opensource_license,
                        ),
                    )
                }
            }
        }
    }
}

/**
 * [PreviewDialog] ??? ????????? ?????? ????????? ?????????????????? ???????????????.
 *
 * @param title ????????? ????????? ?????????????????? ??????
 * @param items ????????? ????????? ?????????????????? ???????????? ?????????
 */
@Composable
fun PlaygroundSection(
    title: String,
    items: ImmutableList<Pair<String, @Composable () -> Unit>>,
    usePreviewDialog: Boolean,
) {
    var playgroundSettingDialogVisible by remember {
        mutableStateOf(
            value = false,
        )
    }
    val previewVisibleStates = remember(
        key1 = items,
    ) {
        mutableStateListOf(
            elements = Array(
                size = items.size,
                init = { false },
            )
        )
    }

    PlaygroundSettingDialog(
        visible = playgroundSettingDialogVisible,
        onDismissRequest = {
            playgroundSettingDialogVisible = !playgroundSettingDialogVisible
        },
    )

    if (usePreviewDialog) {
        items.fastForEachIndexed { index, (_, composable) ->
            PreviewDialog(
                visible = previewVisibleStates[index],
                onBackPressed = {
                    previewVisibleStates[index] = !previewVisibleStates[index]
                },
                content = composable,
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                    )
                }
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding(),
                ),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
            ),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp + padding.calculateBottomPadding(),
            ),
        ) {
            stickyHeader {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        playgroundSettingDialogVisible = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                    ),
                ) {
                    Text(
                        text = "Playground ??????",
                    )
                }
            }

            itemsIndexed(
                items = items,
                key = { _, item -> item.first /* name */ },
            ) { index, item ->
                val (_name, composable) = item
                val name = _name.removeSuffix(
                    suffix = "Demo",
                )

                if (usePreviewDialog) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            previewVisibleStates[index] = true
                        },
                    ) {
                        Text(
                            text = name,
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.wrapContentSize(),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 4.dp,
                        ),
                    ) {
                        Text(
                            text = name,
                        )
                        DebugLayout {
                            composable()
                        }
                    }
                }
            }
        }
    }
}

/**
 * Playground ?????? ????????? ????????? ??????????????? ?????? ????????? ???????????????.
 * ?????????????????? ?????? ????????? font scale ??? ????????? ??? ????????????.
 *
 * @param visible Playground ?????? ?????????????????? visible ??????
 * @param onDismissRequest Playground ?????? ?????????????????? ?????? ??? ???????????? ??????
 */
@Composable
private fun PlaygroundSettingDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
) {
    if (visible) {
        val toast = rememberToast()
        val context = LocalContext.current.applicationContext
        val coroutineScope = rememberCoroutineScope()

        @Stable
        fun Int.msToSecondString() = (toDouble() / 1000.toDouble()).toString()

        var animationDurationInputState by remember {
            mutableStateOf(
                value = QuackAnimationMillis.msToSecondString(),
            )
        }
        var fontScaleInputState by remember {
            mutableStateOf(
                value = fontScale.toString(),
            )
        }
        var showComponentBoundsState by remember {
            mutableStateOf(
                value = showComponentBounds,
            )
        }
        var alwaysShowRipple by remember {
            mutableStateOf(
                value = QuackAlwaysShowRipple,
            )
        }

        @Stable
        fun dismiss(
            reset: Boolean = false,
        ) {
            coroutineScope.launch {
                if (animationDurationInputState.isEmpty()) {
                    animationDurationInputState = QuackDefaultAnimationMillis.msToSecondString()
                }
                if (fontScaleInputState.isEmpty()) {
                    fontScaleInputState = "1"
                }

                context.dataStore.edit { preference ->
                    preference[PreferenceConfigs.AnimationDurationKey] = when (reset) {
                        true -> QuackDefaultAnimationMillis
                        else -> (animationDurationInputState.toDouble() * 1000.toDouble()).roundToInt()
                    }.also { newAnimationMillis ->
                        QuackAnimationMillis = newAnimationMillis.coerceAtLeast(
                            minimumValue = 0,
                        )
                    }
                    preference[PreferenceConfigs.FontScaleKey] = when (reset) {
                        true -> DefaultFontScale
                        else -> fontScaleInputState.toFloat()
                    }.also { newFontScale ->
                        fontScale = newFontScale.coerceAtLeast(
                            minimumValue = 1f,
                        )
                    }
                    preference[PreferenceConfigs.ShowComponentBounds] = when (reset) {
                        true -> DefaultShowComponentBounds
                        else -> showComponentBoundsState
                    }.also { newShowComponentBounds ->
                        showComponentBounds = newShowComponentBounds
                    }
                    preference[PreferenceConfigs.AlwaysShowRipple] = when (reset) {
                        true -> QuackDefaultAlwaysShowRipple
                        else -> alwaysShowRipple
                    }.also { newAlwaysShowRipple ->
                        QuackAlwaysShowRipple = newAlwaysShowRipple
                    }
                }
            }
            onDismissRequest()
        }

        AlertDialog(
            modifier = Modifier.wrapContentSize(),
            title = {
                Text(
                    text = "Playground ??????",
                )
            },
            text = {
                Column(
                    modifier = Modifier.wrapContentSize(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "???????????? ?????? ??????",
                        )
                        Checkbox(
                            checked = showComponentBoundsState,
                            onCheckedChange = { checked ->
                                showComponentBoundsState = checked
                            },
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(
                                top = 5.dp,
                            )
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "?????? ?????? ?????? ??????",
                        )
                        Checkbox(
                            checked = alwaysShowRipple,
                            onCheckedChange = { checked ->
                                alwaysShowRipple = checked
                            },
                        )
                    }
                    Text(
                        modifier = Modifier.padding(
                            top = 20.dp,
                        ),
                        text = "??????????????? ?????? ??????",
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 4.dp,
                            ),
                        value = animationDurationInputState,
                        onValueChange = { newAnimationDuration ->
                            animationDurationInputState = newAnimationDuration
                        },
                        singleLine = true,
                        trailingIcon = {
                            Text(
                                modifier = Modifier.padding(
                                    end = 16.dp,
                                ),
                                text = "??????: ???",
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                        ),
                    )
                    Text(
                        modifier = Modifier.padding(
                            top = 20.dp,
                        ),
                        text = "Font Scale",
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 4.dp,
                            ),
                        value = fontScaleInputState,
                        onValueChange = { newFontScale ->
                            fontScaleInputState = newFontScale
                        },
                        singleLine = true,
                        trailingIcon = {
                            Text(
                                modifier = Modifier.padding(
                                    end = 16.dp,
                                ),
                                text = "??????",
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                        ),
                    )
                }
            },
            onDismissRequest = ::dismiss,
            confirmButton = {
                Button(
                    onClick = ::dismiss,
                ) {
                    Text(
                        text = "??????",
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        dismiss(
                            reset = true,
                        )
                        toast(
                            message = "Playground ????????? ????????? ?????????",
                        )
                    },
                ) {
                    Text(
                        text = "?????????",
                    )
                }
            },
        )
    }
}

/**
 * ????????? ??????????????? Preview ??? ??????????????? ????????? ???????????????.
 *
 * ?????????????????? ???????????? ?????? ????????? ??????????????? ????????? ???????????? [AlertDialog] ???
 * ???????????? ??????, [Box] ??? ????????? ??????????????? ????????? ???????????????.
 *
 * @param visible preview dialog ??? visible ??????
 * @param onBackPressed preview dialog ??? back button ??? ????????? ??? ????????? ??????
 * @param content preview dialog ??? ????????? ????????? ????????????
 */
@Composable
private fun PreviewDialog(
    visible: Boolean,
    onBackPressed: () -> Unit,
    content: @Composable () -> Unit,
) {
    val shape = remember {
        RoundedCornerShape(
            size = 15.dp,
        )
    }

    BackHandler(
        enabled = visible,
        onBack = onBackPressed,
    )

    AnimatedVisibility(
        modifier = Modifier
            .zIndex(
                zIndex = 1f,
            )
            .fillMaxSize(),
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 200,
            ),
        ),
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = 200,
            ),
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Black.copy(
                        alpha = 0.8f,
                    ),
                )
                .quackClickable(
                    rippleEnabled = false,
                    onClick = onBackPressed,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        shape = shape,
                    )
                    .fillMaxWidth(
                        fraction = 0.8f,
                    )
                    .fillMaxHeight(
                        fraction = 0.7f,
                    )
                    .background(
                        color = Color.White,
                    )
                    // prevent click event
                    .quackClickable(
                        rippleEnabled = false,
                        onClick = {},
                    )
                    .padding(
                        horizontal = 16.dp,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                DebugLayout(
                    content = content,
                )
            }
        }
    }
}

@Composable
fun DebugLayout(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalDensity provides Density(
            density = LocalDensity.current.density,
            fontScale = fontScale,
        ),
    ) {
        if (showComponentBounds) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .border(
                        width = 0.1.dp,
                        color = Color.LightGray,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                content()
            }
        } else {
            content()
        }
    }
}
