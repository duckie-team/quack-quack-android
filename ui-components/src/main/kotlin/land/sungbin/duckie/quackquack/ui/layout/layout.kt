/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [layout.kt] created by Ji Sungbin on 22. 8. 14. 오후 5:17
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package land.sungbin.duckie.quackquack.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private typealias ColumnContent = @Composable ColumnScope.() -> Unit

@Immutable
object QuackLayoutDefault {
    @Stable
    val PaddingValues = PaddingValues(
        all = 16.dp,
    )
}

@Immutable
interface QuackColumnScope {
    @Stable
    fun header(content: ColumnContent)

    @Stable
    fun content(content: ColumnContent)

    @Stable
    fun footer(content: ColumnContent)
}

@PublishedApi
internal class QuackColumnScopeInstance(
    columnScope: ColumnScope,
) : QuackColumnScope, ColumnScope by columnScope {
    var headerContent: ColumnContent = {}
    var contentContent: ColumnContent = {}
    var footerContent: ColumnContent = {}

    override fun header(content: ColumnContent) {
        headerContent = content
    }

    override fun content(content: ColumnContent) {
        contentContent = content
    }

    override fun footer(content: ColumnContent) {
        footerContent = content
    }
}

// NOT WORKING
@Composable
inline fun QuackColumn(
    contentPadding: PaddingValues = QuackLayoutDefault.PaddingValues,
    content: @DisallowComposableCalls QuackColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val quackColumnScope = remember(this) {
            QuackColumnScopeInstance(
                columnScope = this,
            )
        }
        quackColumnScope.content()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            quackColumnScope.headerContent
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(
                    weight = 1f,
                )
                .fillMaxHeight()
        ) {
            quackColumnScope.contentContent
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            quackColumnScope.footerContent
        }
    }
}
