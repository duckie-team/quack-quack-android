/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.util

/**
 * MagicNumber을 예외적으로 허용할 때 사용하는 어노테이션입니다.
 *
 * @param because MagicNumber를 허용하는 이유
 */
@Retention(AnnotationRetention.SOURCE)
public annotation class AllowMagicNumber(val because: String)

/** 주의깊게 사용해야 하는 API임을 나타냅니다. */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
  level = RequiresOptIn.Level.WARNING,
  message = "This is a delicate API and its use requires care. Make sure you fully read " +
    "and understand documentation of the declaration that is marked as a delicate API.",
)
public annotation class DelicateQuackQuackApi

/**
 * 매우 실험적인 함수이기에 사용하기 전에 테스트가 꼭 필요함을 나타냅니다.
 *
 * @param passed 테스트 성공 여부
 */
@Retention(AnnotationRetention.SOURCE)
public annotation class MustBeTested(val passed: Boolean)
