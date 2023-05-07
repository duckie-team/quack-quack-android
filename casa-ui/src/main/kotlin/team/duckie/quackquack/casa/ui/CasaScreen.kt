/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

/* ktlint-disable wrapping */

@file:Suppress("Wrapping")
@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)

package team.duckie.quackquack.casa.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMap
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import team.duckie.quackquack.casa.material.CasaConfig
import team.duckie.quackquack.casa.material.CasaModel

@Composable
public fun CasaScreen(
  modifier: Modifier = Modifier,
  models: ImmutableList<CasaModel>,
  config: CasaConfig = CasaConfig(),
) {
  val contentLazyListState = rememberLazyListState()
  val focusRequester = remember { FocusRequester() }

  var searchState by rememberSaveable { mutableStateOf(false) }
  var searchTerm by rememberSaveable { mutableStateOf("") }

  val selectedDomains = rememberSaveable(
    saver = Saver<SnapshotStateList<String>, List<SnapshotStateList<String>>>(
      save = { stateList ->
        listOf(stateList)
      },
      restore = { list ->
        list.first()
      },
    ),
  ) {
    mutableStateListOf()
  }
  val displayedModels by remember(models) {
    derivedStateOf {
      models.filter { model ->
        (model.domain.contains(searchTerm, ignoreCase = true) ||
          model.name.contains(searchTerm, ignoreCase = true) ||
          model.kdocDefaultSection.contains(searchTerm, ignoreCase = true)) &&
          if (selectedDomains.isNotEmpty()) selectedDomains.contains(model.domain) else true
      }.toImmutableList()
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

  BackHandler(enabled = searchState) {
    searchState = false
    searchTerm = ""
  }

  BackHandler(enabled = selectedModel != null) {
    selectedModel = null
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
            modifier = Modifier
              .testTag("searchField")
              .statusBarsPadding()
              .fillMaxWidth()
              .height(64.dp)
              .focusRequester(focusRequester)
              .onGloballyPositioned {
                focusRequester.requestFocus()
              },
            value = searchTerm,
            onValueChange = { term ->
              searchTerm = term
            },
            onSearchImeAction = {
              searchState = false
              searchTerm = ""
            },
            onClear = {
              searchTerm = ""
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
    if (selectedModel == null) {
      val domains = remember(models) {
        models.fastMap(CasaModel::domain).toImmutableList()
      }

      CasaContentWithDoaminFilter(
        modifier = Modifier
          .fillMaxSize()
          .padding(padding),
        domains = domains,
        selectedDomains = selectedDomains,
        displayedModels = displayedModels,
        contentLazyListState = contentLazyListState,
        onModelSelected = { model ->
          searchState = false
          searchTerm = ""
          selectedModel = model
        },
      )
    } else {
      CasaComponents(
        modifier = Modifier.fillMaxSize(),
        model = selectedModel!!,
      )
    }
  }
}

@Composable
private fun CasaContentWithDoaminFilter(
  modifier: Modifier = Modifier,
  domains: ImmutableList<String>,
  selectedDomains: SnapshotStateList<String>,
  displayedModels: ImmutableList<CasaModel>,
  contentLazyListState: LazyListState,
  onModelSelected: (model: CasaModel) -> Unit,
) {
  Column(modifier = modifier) {
    FilterTabRow(
      modifier = Modifier
        .testTag("domainNavigator")
        .fillMaxWidth(),
      domains = domains,
      selectedDomains = selectedDomains,
      onFilterSelected = { domain ->
        if (!selectedDomains.contains(domain)) {
          selectedDomains += domain
        } else {
          selectedDomains -= domain
        }
      },
    )
    CasaContent(
      modifier = Modifier
        .testTag("casaComponents")
        .fillMaxSize(),
      models = displayedModels,
      selectedDomains = selectedDomains,
      lazyListState = contentLazyListState,
      onClick = onModelSelected,
    )
  }
}
