# core-sugar-processor

본 모듈은 [`core-sugar`](../core-sugar)의 핵심 구현을 나타냅니다.

기본 작동은 [`KSP`](https://kotlinlang.org/docs/ksp-overview.html)와 [`square/kotlinpoet`](https://github.com/square/kotlinpoet)으로 진행됩니다.

```kotlin
@SugarToken
enum class QuackButton {
    Default,
    ;
}

@Sugar
@Composable
fun Button(type: QuackButton, modifier: Modifier = Modifier, text: String) {}

// generated
fun DefaultButton(modifier: Modifier = Modifier, text: String) {}
```

### 작동 방식

1. `@Sugar`가 달린 컴포저블 함수를 찾습니다.
2. 찾은 함수의 인자에서 `@SugarToken`이 달린 디자인 토큰을 찾습니다.
3. 찾은 디자인 토큰의 모든 필드를 조회합니다. 단, `@IgnoreSugarToken`이 달린 필드는 제외합니다.
4. `@Sugar`의 `name`을 조회합니다.
5. 3번 과정으로 찾은 필드를 순회하며 4번 과정으로 찾은 `name` 접미사를 붙이며 네이밍을 결정합니다.
6. 5번 과정으로 결정된 네이밍 배열을 순회하며 코드 생성을 시작합니다.
   1. 기존 컴포저블 함수의 인자를 복사합니다. 단, 2번 과정으로 찾은 디자인 토큰 인자는 제외합니다.
   2. default argument가 있다면 해당 값도 동일하게 복사합니다.
   3. 해당 sugar 컴포넌트가 나타내는 디자인 토큰에 맞게 함수의 본문을 복사합니다. 
   ```kotlin
   @Sugar
   @Composable
   fun Button(type: QuackButton, modifier: Modifier = Modifier, text: String) {
     ButtonImpl(
       type = type,
       modifier = modifier,
       text = text,
     )
   }
   
   // generated
   @Composable
   fun DefaultButton(modifier: Modifier = Modifier, text: String) {
     ButtonImpl(
       type = QuackButton.Default,
       modifier = modifier,
       text = text,
     )
   }
   ```
