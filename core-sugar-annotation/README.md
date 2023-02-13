# core-sugar-annotation

본 모듈은 [`core-sugar`](../core-sugar)의 핵심 어노테이션을 제공합니다.

### `@Sugar(val name: String = DEFAULT_NAME)`

sugar를 제공할 컴포넌트임을 나타냅니다. `name`은 컴포넌트 이름을 나타내며, `Sugar.DEFAULT_NAME`은 원래의 컴포넌트 이름을 사용합니다.

### `@SugarToken`

디자인 토큰을 나타냅니다. `sealed class` 혹은 `enum class`에 직접적으로 한 번만 사용합니다.

### Example

```kotlin
@SugarToken
enum class QuackButton {
    Default,
    ;
}

@Sugar
fun Button(type: QuackButton) {}

@Sugar(name = "Btn")
fun Button(type: QuackButton, modifier: Modifier) {}

// generation
fun DefaultButton() {}
fun DefaultBtn(modifier: Modifier) {}
```
