/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import team.duckie.quackquack.common.npe

/**
 * State 를 Flow 로 변환합니다.
 *
 * @receiver 기존 State<T> 객체
 * @return Flow 로 바뀐 State<T> 객체
 */
private fun <T> State<T>.toFlow() = snapshotFlow { this }

/**
 * Quack 디자인 리소스의 개별 요소들에 애니메이션을 적용시켜서
 * 애니메이션이 적용되고 있는 새로운 Quack 디자인 리소스를 만듭니다.
 *
 * @param initialValue Quack 디자인 리소스의 초기 값
 * @param animationStates Quack 디자인 리소스의 개별 요소들에
 * 애니메이션이 적용되고 있는 상태 입니다. 상태는 [State] 객체로
 * 받아야 하며, Quack 디자인 리소스의 개별 요소 개수 만큼 리스트로
 * 받아야 합니다. 보통 animateAsState 로 애니메이션 [State] 객체를
 * 만들 수 있습니다.
 * @param targetBuilder Quack 디자인 리소스의 개별 요소들에 애니메이션을
 * 적용시키고, 애니메이션이 적용중인 개별 요소들을 하나의 Quack 디자인
 * 리소스로 만들 때 사용되는 람다 입니다. 개별 요소들이 합쳐져서 만들어질
 * Quack 디자인 리소스를 반환해야 합니다. 이렇게 만들어지는 타입은
 * [initialValue] 의 타입과 동일해야 합니다. 람다의 인자로는 애니메이션된
 * 개별 요소들의 **[State] 객체가 아닌 값이 바로** 들어옵니다.
 *
 * @return 애니메이션이 적용되고 있는 새로운 Quack 디자인
 * 리소스의 [State] wrapping 값
 */
// TODO: 개별 값들의 애니메이션 State 를 Flow 로 바꿔서, 해당 Flow 들을
//       combine 해서 새로운 객체를 만드는 방법이 올바른 방식인지
//       모르겠음. 추후 더 좋은 방법을 찾아서 고쳐야 함.
@Composable
internal inline fun <T> animateQuackAsState(
    initialValue: T,
    animationStates: List<State<*>>,
    crossinline targetBuilder: (animateValues: List<Any>) -> T,
): State<T> {
    val animationFlows: List<Flow<*>> = animationStates.map(State<*>::toFlow)
    return combine(
        flows = animationFlows,
    ) { _animationFlows ->
        targetBuilder(
            _animationFlows.mapIndexed { index, flow ->
                (flow as State<*>).value ?: npe(
                    lazyMessage = {
                        "애니메이션 요청된 $initialValue Quack 디자인 리소스의 ${index}번째 " +
                                "개별 요소의 애니메이션 값이 null 이어서 애니메이션을 적용할 수 없습니다."
                    },
                )
            },
        )
    }.collectAsState(
        initial = initialValue,
    )
}
