/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [KDocFieldsTest.kt] created by ricky_0_k on 22. 9. 2. 오전 2:07
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.lint.core

import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.common.lint.test.LintTestRule
import team.duckie.quackquack.common.lint.test.composableTestFile

/**
 * 테스트 성공 조건
 * 1. 정상적인 케이스
 * 2. 오직 KDocSection 에 대해서만 체크해야 합니다. (공백 등은 체크하지 않음)
 * 3. 함수에는 KDoc 이 명시되어야 합니다.
 * 4. @param 명세 개수가, 매개 변수 개수가 일치해야 합니다.
 * 5. @return, @throws, @description 에 오타가 없어야 합니다.
 * 6. @return, @description 은 반드시 명세되어야 합니다. (@throws 는 필요에 따라 명세되어야 합니다.)
 * 7. 함수 내 매개변수가 있을 시, @param 는 반드시 명세되어야 합니다
 */
class KDocFieldsTest {

    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `Great Function KDoc Case`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        import java.lang.Exception

                        /**
                         * @description 모든 어노테이션이 존재하는 함수 예제
                         *
                         * @param ex1 테스트 용 문자열
                         * @param efg 테스트 용 숫자
                         * @return 반환값은 없습니다.
                         * @throws Exception
                         */
                        fun `All annotations exist function Example`(ex1: String, efg: Int) {
                            throw Exception()
                        }

                        /**
                         * @description 즉시 반환형인 함수
                         *
                         * @param ex1 테스트 용 문자열
                         * @param efg 테스트 용 숫자
                         * @return 반환값은 없습니다.
                         * @throws Exception
                         */
                        fun `Reference_Expression Example`(ex1: String, efg: Int) {
                            throw Exception()
                        }

                        /**
                         * @description Composable 함수
                         *
                         * @return 반환값은 없습니다.
                         * @param list 테스트 용 리스트
                         * @throws Exception
                         */
                        @Composable
                        operator fun `Compose Function With Block Example`(list: MutableList<Any>) {
                            throw Exception()
                        }

                        /**
                         * @description 즉시 반환 형인 Composable 함수
                         *
                         * @return 반환값은 없습니다.
                         * @param list 테스트 용 리스트
                         */
                        @Composable
                        operator fun `Compose Function With Reference_Expression Example`(
                            list: MutableList<Any>
                        ) = list

                        /**
                         * @description 예외를 방출하지 않는 함수 예제
                         *
                         * @return 반환값은 없습니다.
                         */
                        fun `Not Emit throw Exception function Example`() {
                        }

                        /**
                         * @description 추상 함수 예제
                         *
                         * @return 반환값은 없습니다.
                         */
                        abstract fun `abstract function Example`()
                        """
                ),
            ),
            issues = listOf(
                KDocFieldsIssue,
            ),
            expectedCount = 0,
        )
    }

    @Test
    fun `Only Check KDocSection`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        import java.lang.Exception

                        /**
                         *
                         *
                         *
                         * @description 모든 어노테이션이 존재하는 함수 예제
                         *
                         * @param ex1 테스트 용 문자열
                         * 가나다라
                         *
                         * @param efg 테스트 용 숫자
                         * abcdefg
                         * @return 반환값은 없습니다.
                         * 0123461#@!%@!
                         * @throws Exception
                         */
                        fun `All annotations exist function Example`(ex1: String, efg: Int) {
                            throw Exception()
                        }

                        /**
                         * @description "params" 어노테이션이 없는 함수 예제
                         *
                         * @return 반환값은 없습니다.
                         * abcdefg
                         * 궭뉅
                         * @throws Exception
                         */
                        fun `None parameter function Example`() {
                            throw Exception()
                        }

                        /**
                         *
                         * a
                         *
                         * b
                         * @description 예외를 방출하지 않는 함수 예제
                         *
                         * @return 반환값은 없습니다.
                         */
                        fun `Not Emit throw Exception function Example`() {
                        }
                        """
                ),
            ),
            issues = listOf(
                KDocFieldsIssue,
            ),
            expectedCount = 0,
        )
    }

    @Test
    fun `Function must have a KDoc specified`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        import java.lang.Exception

                        fun kDocNotExist() {
                            throw Exception();
                        }
                        """
                ),
            ),
            issues = listOf(
                KDocFieldsIssue,
            ),
            expectedCount = 1,
        )
    }

    @Test
    fun `the number @param's and the number of parameters must match`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        import java.lang.Exception

                        /**
                         * @description 실패 케이스입니다.
                         *
                         * @param ex1 테스트 용 문자열
                         * @param efg 명세되지 않은 params
                         * @return 반환값은 없습니다.
                         * @throws Exception
                         */
                        fun failed1(ex1: String) {
                            throw Exception();
                        }

                        /**
                         * @description 실패 케이스입니다.
                         *
                         * @param efg 명세되지 않은 params
                         * @return 반환값은 없습니다.
                         * @throws Exception
                         */
                        fun failed2() {
                            throw Exception();
                        }
                        """
                ),
            ),
            issues = listOf(
                KDocFieldsIssue,
            ),
            expectedCount = 2,
        )
    }

    @Test
    fun `@return, @throws, @description must not contain invalid inputs`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        import java.lang.Exception

                        /**
                         * @ddescription 실패 케이스입니다.
                         *
                         * @return 반환값은 없습니다.
                         * @throws Exception
                         */
                        fun failed1() {
                            throw Exception();
                        }

                        /**
                         * @description 실패 케이스입니다.
                         *
                         * @rreturn 반환값은 없습니다.
                         * @throws Exception
                         */
                        fun failed2() {
                            throw Exception();
                        }

                        /**
                         * @description 실패 케이스입니다.
                         *
                         * @return 반환값은 없습니다.
                         * @tthrows Exception
                         */
                        fun failed3() {
                            throw Exception();
                        }
                        """
                ),
            ),
            issues = listOf(
                KDocFieldsIssue,
            ),
            expectedCount = 3,
        )
    }

    @Test
    fun `@return, @description must be specified (@throws is Optional)`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        import java.lang.Exception

                        /**
                         * description 이 없는 경우입니다.
                         *
                         * @param ex1 테스트 용 문자열
                         * @param efg 테스트 용 숫자
                         * @return 반환값은 없습니다.
                         * @throws Exception
                         */
                        override fun failed1(ex1: String, efg: Int) {
                            throw Exception()
                        }

                        /**
                         * @description return 이 없는 경우입니다.
                         *
                         * @param ex1 테스트 용 문자열
                         * @param efg 테스트 용 숫자
                         */
                        override fun failed2(ex1: String, efg: Int) {
                        }

                        /**
                         * @description @throws 가 없는 경우입니다.
                         *
                         * @param ex1 테스트 용 문자열
                         * @param efg 테스트 용 숫자
                         */
                        override fun failed3(ex1: String, efg: Int) {
                            throw Exception()
                        }
                        """
                ),
            ),
            issues = listOf(
                KDocFieldsIssue,
            ),
            expectedCount = 3,
        )
    }

    @Test
    fun `When there are parameters in a function, @param must be specified`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        import java.lang.Exception

                        /**
                         * @description param(ex1, efg) 명세가 없는 경우입니다.
                         *
                         * @return 반환값은 없습니다.
                         * @throws Exception
                         */
                        override fun failed1(ex1: String, efg: Int) {
                            throw Exception()
                        }

                        /**
                         * @description 일부 param(efg) 명세가 없는 경우입니다.
                         *
                         * @param ex1 테스트 용 문자열
                         * @return 반환값은 없습니다.
                         * @throws Exception
                         */
                        override fun failed2(ex1: String, efg: Int) {
                            throw Exception()
                        }

                        /**
                         * @description 일부 param(efg) 명세에 오타가 있는 경우입니다.
                         *
                         * @param ex2 테스트 용 문자열
                         * @param efg 테스트 용 숫자
                         * @return 반환값은 없습니다.
                         * @throws Exception
                         */
                        override fun failed2(ex1: String, efg: Int) {
                            throw Exception()
                        }
                        """
                ),
            ),
            issues = listOf(
                KDocFieldsIssue,
            ),
            expectedCount = 3,
        )
    }
}
