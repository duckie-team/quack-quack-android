# core-aide-processor

본 모듈은 [`core-aide`](../core-aide)를 구현합니다.

기본 작동은 [`KSP`](https://kotlinlang.org/docs/ksp-overview.html)와 [`square/kotlinpoet`](https://github.com/square/kotlinpoet)으로 진행됩니다.

```kotlin
@Modifier(Type.Text)
fun Modifier.span(text: String, span: SpanStyle) = this

@Modifier(Type.Text)
fun Modifier.spans(texts: List<String>, span: SpanStyle) = this

// generated
val textModifiers = listOf("span", "spans")
```

### 작동 방식

1. `@Modifier`가 달린 Modifier를 찾습니다.
2. 찾은 Modifier의 이름을 해당 타입의 리스트에 추가합니다.
