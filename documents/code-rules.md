# code-rules

꽥꽥 코드를 작성하면서 매번 헷갈리는 코드 규칙들을 기술합니다.

> **Note**: 이 규칙들은 꽥꽥 린트와 별개로 관리됩니다.

---

## Stability 명시

- 안정 상태가 `Stable` 로 추론되는 클래스에는 안정성을 명시하지 않습니다.
- 안정 상태가 `Unstable` 로 추론되는 클래스에는 필요할 경우 안정성을 명시합니다.
- 변수에는 안정 상태를 명시하지 않습니다.
- 함수에는 해당 함수가 컴포저블 안에서 직접적으로 사용되는 경우에만 안정성을 명시합니다.

## 람다식 lambda arguments

모든 람다식에는 lambda arguments 를 아래와 같은 타입으로 명시합니다.

```kotlin
fun lambda(foo: (bar: Int) -> Unit) {
    foo(
        /* bar = */ 
        1,
    )
}
``` 

## `{ ~ }` 는 항상 new-line

`{ ~ }` 는 항상 new-line 으로 작성합니다.

```kotlin
val foo = { bar: Int ->
    bar
}
``` 
