/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

/* ktlint-disable wrapping */

@file:Suppress("Wrapping")
@file:OptIn(ExperimentalMaterial3Api::class)

package team.duckie.quackquack.casa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// TODO: 문서화
@Composable
internal fun FilterTabRow(
    modifier: Modifier = Modifier,
    domains: List<String>,
    selectedDomains: List<String>,
    onFilterSelected: (domain: String) -> Unit,
) {
    if (domains.isEmpty()) return

    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(domains) { domain ->
            val selected by remember {
                derivedStateOf {
                    selectedDomains.contains(domain)
                }
            }

            FilterChip(
                selected = selected,
                onClick = {
                    onFilterSelected(domain)
                },
                label = {
                    Text(text = domain.lowercase())
                },
                leadingIcon = (@Composable {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = null,
                        modifier = Modifier.size(FilterChipDefaults.IconSize),
                    )
                }).takeIf { selected },
            )
        }
    }
}
