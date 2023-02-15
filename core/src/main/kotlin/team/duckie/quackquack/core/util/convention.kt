/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.core.util

import team.duckie.quackquack.core.theme.QuackScope

/**
 * MagicNumber을 예외적으로 허용할 때 사용하는 어노테이션 입니다.
 *
 * @param because MagicNumber를 허용하는 이유
 */
@Retention(AnnotationRetention.SOURCE)
internal annotation class AllowMagicNumber(val because: String)

/**
 * 주의깊게 사용해야 하는 API임을 나타냅니다.
 *
 * 이 어노테이션이 붙는 API는 조심히 사용해야 합니다.
 * 문서를 충분히 읽고 올바른 사용 사례임을 확신할 수 있을 때만 사용하세요.
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "이 API는 조심히 사용해야 합니다." +
            " 문서를 충분히 읽고 올바른 사용 사례임을 확신할 수 있을 때만 사용하세요.",
)
public annotation class DelicateApi

/**
 * 매우 실험적인 함수이기에 사용하기 전에 테스트가 꼭 필요함을 나타냅니다.
 */
@Retention(AnnotationRetention.SOURCE)
internal annotation class MustBeTested

/**
 * [QuackScope]의 표준 함수가 아닌, 개발자의 편의를 위한 sugar-extension이므로
 * 별도 extension으로 분리됨을 나타냅니다.
 */
@Retention(AnnotationRetention.SOURCE)
internal annotation class SugarExtension
