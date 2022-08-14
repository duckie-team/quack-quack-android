/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [column.kt] created by Ji Sungbin on 22. 8. 14. 오후 5:17
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package land.sungbin.duckie.quackquack.ui.layout

// layout 문서 참고
/*
private typealias ColumnContent = @Composable ColumnScope.() -> Unit

@Immutable
interface QuackColumnScope {
    @Stable
    fun header(content: ColumnContent)

    @Stable
    fun content(content: ColumnContent)

    @Stable
    fun footer(content: ColumnContent)
}

private class QuackColumnScopeInstance(
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
*/
