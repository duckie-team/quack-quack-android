# core-sugar-processor-kotlinc

1. [Overview](#overview)
2. [Why not KSP?](#wht-not-ksp)
3. [Ir Visitor](#ir-visitor)
4. [Code Generation](#code-generation)
5. [Ir Transformer](#ir-transformer)
6. [Compile Options](#compile-options)
7. [Caveat](#caveat)

---

## Overview

이 모듈은 core 컴포넌트의 sugar syntax를 위한 컴포넌트를 자동 구현합니다. 이 모듈이 해결하고자 하는 문제와 기본 작동 예시는 [`core`](../core#%EB%AC%B8%EB%B2%95-%EC%84%A4%ED%83%95)의 README를 참고하세요.

`core-sugar-processor-kotlinc`는 다음과 같은 단계로 진행됩니다.

1. `Ir Visit`
2. `Code Generation`
3. `Ir Transform`

![flow](assets/flow.png)

이 중 `2. Code Generation`은 컴파일 옵션에 따라 생략할 수 있으며 모듈 내부에서는 [`poet`](https://square.github.io/kotlinpoet/) 이라는 네이밍을 사용합니다.

이 문서는 `core-sugar-processor-kotlinc`가 작동되는 세부 정책과 이러한 정책이 정해진 이유인 개발 초기의 고민들을 기록합니다.

## Wht not KSP?

초기에는 [KSP](https://github.com/google/ksp)로 접근하였지만([#487](https://github.com/duckie-team/quack-quack-android/pull/487)), 함수 인자의 default value 파싱이 상당히 어려운 문제가 있었습니다.

KSP는 [psi](https://plugins.jetbrains.com/docs/intellij/psi-elements.html) 기반으로 작동되고, value parameter의 symbol을 나타내는 `KSValueParameter`의 `defaultValue` 프로퍼티를 사용하면 인자의 기본 값을 `KtExpression`으로 조회할 수 있습니다.

문제는 `KtExpression`을 문자열로 나타낼 때 발생합니다.

아래와 같이 sibling이 없고 간단한 psi tree를 갖는 expression이라면 쉽게 나타낼 수 있지만,

```kotlin
true
```

<img src="assets/simple-psi-defaultvalue.jpeg" width="60%" alt="simple-psi-defaultvalue"/>

아래와 같이 sibling이 포함된 복잡한 psi tree를 갖는 expression이라면 파싱의 난이도가 급격히 상승합니다.

```kotlin
listOf(1, 2, 3)
```

<img src="assets/complex-psi-defaultvalue.jpeg" width="60%" alt="complex-psi-defaultvalue"/>

따라서 default value까지 copy하여 sugar component 코드를 생성하는 건 무리라고 판단하고 default value 지원을 TODO로 남기려 했지만, 컴포즈 환경에서 default value가 없다는 건 개발자에게 너무 치명적인 경험 저하라고 생각하였습니다.

이 문제를 해결하기 위해 KSP보다 코드에 더 low level로 접근할 수 있는 방안을 고민해 보았고, [IR](https://en.wikipedia.org/wiki/Intermediate_representation)이 떠올랐습니다.

코틀린은 IR 접근 API를 public experimental로 공개하고 있으며, Kotlin Compiler Plugin으로 가능합니다. Kotlin Compiler Plugin의 약간의 가설 검증([`29b6fb2`](https://github.com/duckie-team/quack-quack-android/pull/495/commits/29b6fb2913511664ede170a0bed6a3c9a0712774), [`IrValueParameter#defalutValue`](https://slack-chats.kotlinlang.org/t/10002221/hi-is-it-possible-to-get-the-default-value-of-an-irvaluepara#a047d5bf-c73a-48bf-92c8-d3cd7231ace3)) 후에 위와 같은 문제는 비슷한 이유로 해결이 어렵다고 느꼈지만, 해결할 수 있는 다른 방법을 찾아냅니다.

제가 생각한 방법은 이렇습니다.

1. Ir Visitor에서 default value의 Ir을 저장한다.
2. 생성할 코드의 default value는 `Any() as T`와 같은 식으로 컴파일 에러만 나지 않게 지정한다.
3. 생성된 코드의 default value IR을 Ir Transformer를 통해 Ir Visitor에서 저장한 Ir로 교체한다.

위와 같은 방법이 유효함을 로컬에서 증명하였고 `core-sugar-processor`가 `core-sugar-processor-kotlinc`로 바뀌게 됩니다([`0416f53`](https://github.com/duckie-team/quack-quack-android/pull/495/commits/0416f53e2d59add7fc8e0e2772b2fb459b216866)). 

## Ir Visitor

`core-sugar-processor-kotlinc`의 첫 번째 동작은 Ir Visit 입니다. 이 단계에서는 다음과 같은 정보를 수집합니다.

- `file`: IR이 제공된 파일
- `referFqn`: IR이 제공된 함수의 fully-qualified name
- `kdoc`: IR이 제공된 함수의 KDoc
- `sugarName`: 생성할 sugar component의 네이밍 규칙. `@SugarToken` 값을 가져옵니다.
- `sugarToken`: 생성할 sugar component의 Sugar Token에 해당하는 인자. `@SugarToken`이 달린 인자를 가져옵니다.
- `tokenFqExpressions`: Sugar Token의 expression 모음
- `parameters`: IR이 제공된 함수의 인자 모음. sugar component 생성에 필요한 정보만 수집합니다.

##### `tokenFqExpressions` 예시

```kotlin
package team.duckie.theme

@JvmInline
value class Theme(val index: Int) {
    companion object {
        val Default = Theme(1)
        val Dark = Theme(2)
        val Light = Theme(3)
        val System = Theme(4)
    }
}

// ["team.duckie.theme.Theme.Default", "team.duckie.theme.Theme.Dark", "team.duckie.theme.Theme.Light", "team.duckie.theme.Theme.System"]
```

##### `parameters`에서 수집하는 정보:

- `name`: 인자명
- `type`: 인자의 타입
- `isToken`: 인자가 Sugar Token인지 여부
- `isComposable`: 인자 타입에 `androidx.compose.runtime.Composable` 어노테이션이 있는지 여부
- `imports`: 인자 타입 외에 추가로 import가 필요한 클래스의 fully-qualified name으로 구성된 목록. 자세한 정보는 `@Imports` 문서를 확인하세요.
- `defaultValue`: 인자의 기본 값

## Code Generation

## Ir Transformer

## Compile Options

## Caveat
