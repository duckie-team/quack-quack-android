/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:OptIn(ExperimentalMaterial3Api::class)

package team.duckie.quackquack.casa.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import team.duckie.quackquack.casa.material.CasaConfig
import team.duckie.quackquack.casa.material.CasaModel

@Composable
public fun CasaTopBar(
    modifier: Modifier = Modifier,
    selectedModel: CasaModel? = null,
    casaConfig: CasaConfig,
    onSearch: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = selectedModel?.name ?: casaConfig.casaName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        actions = {
            Box {
                Row {
                    if (selectedModel == null) {
                        IconButton(onClick = onSearch) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "Search",
                            )
                        }
                    }
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "Menu",
                        )
                    }
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = {
                        menuExpanded = false
                    },
                ) {
                    val sourceUrl = selectedModel?.toSourceUrl(casaConfig) ?: casaConfig.baseSourceUrl

                    CasaTopBarDropdownMenuContent(
                        sourceUrl = sourceUrl,
                        casaConfig = casaConfig,
                        updateMenuExpanded = { expanded ->
                            menuExpanded = expanded
                        },
                    )
                }
            }
        },
        navigationIcon = {
            if (selectedModel != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        },
    )
}

@Suppress("UnusedReceiverParameter")
@Composable
private fun ColumnScope.CasaTopBarDropdownMenuContent(
    sourceUrl: String,
    casaConfig: CasaConfig,
    updateMenuExpanded: (expand: Boolean) -> Unit,
) {
    val context = LocalContext.current

    DropdownMenuItem(
        onClick = {
            context.launchUrl(sourceUrl)
            updateMenuExpanded(false)
        },
        text = {
            Text(text = "Source code")
        },
    )
    DropdownMenuItem(
        onClick = {
            context.launchUrl(casaConfig.bugReportUrl)
            updateMenuExpanded(false)
        },
        text = {
            Text(text = "Report bug")
        },
    )
    DropdownMenuItem(
        onClick = {
            updateMenuExpanded(false)
            // TODO(2): 오픈소스 라이선스
        },
        text = {
            Text(text = "Opensource license")
        },
    )
}

private fun Context.launchUrl(url: String) {
    startActivity(
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        },
    )
}
