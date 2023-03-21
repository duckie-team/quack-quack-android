# core-aide-processor

본 모듈은 [`core-aide`](../core-aide)가 작동되기 위한 각각 도메인별 허용하는 데코레이터 Modifier의 모음을 자동으로 생성합니다.

기본 작동은 [`KSP`](https://kotlinlang.org/docs/ksp-overview.html)와 [`kotlinpoet`](https://github.com/square/kotlinpoet)으로 진행됩니다.

```kotlin
// file: text.kt
@DecorateModifier
fun Modifier.highlight(text: Highlight, color: Color): Modifier {
    // ... awesome code
}

@DecorateModifier
fun Modifier.span(text: String, color: Color): Modifier {
    // ... awesome code
}

// generated
internal val aideModifiers: Map<String, List<String>> = run {
  val aide = mutableMapOf<String, List<String>>()

  aide["text"] = listOf("span", "highlight")
  aide["span"] = emptyList()
  aide["highlight"] = emptyList()

  aide
}
```

### `aide["text"] = listOf("span", "highlight")`

key는 컴포넌트의 도메인을 나타내고, value는 해당 도메인 컴포넌트에서 사용 가능한 데코레이션 Modifier의 이름들을 나타냅니다.

### `aide["span"] = emptyList()`, `aide["highlight"] = emptyList()`

이 부분은 감지한 Modifier가 꽥꽥의 데코레이션 Modifier인지 확인할 때 O(1)만에 진행하기 위해 생성됩니다.

```kotlin
val quackModifiers = modifiers.mapNotNull { expression ->
    expression.takeIf { modifier ->
        val identifier = modifier.asCall()?.methodIdentifier ?: return@mapNotNull null
        aideModifiers[identifier.name] != null
    }
}
```

만약 `aideModifiers[identifier.name]` 값이 null이 아니라면 유효한 데코레이션 Modifier인 것으로 간주할 수 있습니다.