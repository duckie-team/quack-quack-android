/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)

package team.duckie.quackquack.casa.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import kotlinx.collections.immutable.ImmutableList
import team.duckie.quackquack.casa.material.CasaModel

@Composable
public fun CasaScreen(
    modifier: Modifier = Modifier,
    models: ImmutableList<CasaModel>,
    config: CasaConfig = CasaConfig(),
) {
    val focusRequester = remember { FocusRequester() }
    var searchState by rememberSaveable { mutableStateOf(false) }
    var searchTerm by rememberSaveable(searchState) { mutableStateOf("") }
    val selectedDomains = rememberSaveable { mutableStateListOf<String>() }
    val displayedModels by remember(models) {
        derivedStateOf {
            models.filter { model ->
                model.domain.contains(searchTerm, ignoreCase = true) ||
                        model.name.contains(searchTerm, ignoreCase = true) ||
                        model.kdocDefaultSection.contains(searchTerm, ignoreCase = true)
            }
        }
    }
    var selectedModel by rememberSaveable(
        stateSaver = Saver<CasaModel?, Int>(
            save = { model ->
                model.hashCode()
            },
            restore = { savedModelHashCode ->
                models.find { model ->
                    model.hashCode() == savedModelHashCode
                }
            },
        ),
    ) {
        mutableStateOf(null)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            AnimatedContent(
                targetState = searchState,
                transitionSpec = {
                    if (targetState) {
                        expandHorizontally { it / 8 } + fadeIn(tween(100)) with fadeOut()
                    } else {
                        fadeIn(tween(500, delayMillis = 100)) with
                                shrinkHorizontally(tween(400)) { it / 8 } + fadeOut(tween(400))
                    }
                },
                contentAlignment = Alignment.CenterEnd,
            ) { isSearchState ->
                if (isSearchState) {
                    CasaSearchTopAppBar(
                        searchTerm = searchTerm,
                        focusRequester = focusRequester,
                        onSearch = {
                            searchState = false
                        },
                        onClear = {
                            searchState = false
                        },
                        onValueChange = { term ->
                            searchTerm = term
                        },
                    )
                } else {
                    CasaTopBar(
                        selectedModel = selectedModel,
                        casaConfig = config,
                        onSearch = {
                            searchState = true
                        },
                        onBackClick = {
                            selectedModel = null
                        },
                    )
                }
            }
        },
    ) { padding ->
        val domains = remember(models) { models.map(CasaModel::domain) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            FilterTabRow(
                modifier = Modifier.fillMaxWidth(),
                domains = domains,
                selectedDomains = selectedDomains,
                onSelectFilter = { domain ->
                    if (!selectedDomains.contains(domain)) {
                        selectedDomains += domain
                    } else {
                        selectedDomains -= domain
                    }
                },
            )
        }
    }
}
