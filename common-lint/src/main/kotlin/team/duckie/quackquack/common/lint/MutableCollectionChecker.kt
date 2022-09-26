/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.common.lint

import org.jetbrains.kotlin.psi.KtTypeReference

/**
 * MutableCollection 접미사들
 *
 * Collection 으로 판단돼야 하는 클래스들의 접미사 입니다.
 * 사용자가 @Collection 어노테이션을 붙인 클래스들의 이름도 추가됩니다.
 */
private val CollectionsSuffix: List<String> = mutableListOf(
    "Collection",
    "List",
    "Set",
    "Map",
)/*.apply {
    addAll(CustomRule.Collection)
}*/

/**
 * ImmutableCollection 이름들
 *
 * kotlinx.collections.immutable 패키지에 있는 Collection 이름들 입니다.
 * 사용자가 @ImmutableCollection 어노테이션을 붙인 클래스들의 이름도 추가됩니다.
 */
private val ImmutableCollectionsName: List<String> = mutableListOf(
    "ImmutableCollection",
    "ImmutableList",
    "ImmutableSet",
    "ImmutableMap",
    "PersistentCollection",
    "PersistentList",
    "PersistentSet",
    "PersistentMap",
)/*.apply {
    addAll(CustomRule.ImmutableCollection)
}*/

private val KtTypeReference.isCollection
    get() = CollectionsSuffix.any { suffix ->
        // ListWrapper 같은 경우도 있어서 contains 로 체크 (endsWith 했다가 취소)
        text.contains(suffix)
    }

val String.isCollection
    get() = CollectionsSuffix.any { suffix ->
        this.contains(suffix)
    }

private val KtTypeReference.isImmutableCollection get() = ImmutableCollectionsName.contains(text)

val KtTypeReference.isMutableCollection get() = isCollection && !isImmutableCollection
