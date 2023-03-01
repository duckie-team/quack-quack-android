# core-aide-annotation

본 모듈은 [`core-aide`](../core-aide)의 어노테이션을 제공합니다.

### `@Component(val type: Type)`

꽥꽥 컴포넌트임을 나타냅니다.

### `@Modifier(val type: Type)`

꽥꽥 컴포넌트에서 사용될 Modifier임을 나타냅니다.

### Type

대상 타입을 나타냅니다.

- `Text`

### Example

```kotlin
@Component(Type.Text)
fun QuackText(modifier: Modifier = Modifier, text: String) {}

@Modifier(Type.Text)
fun Modifier.span(text: String, span: SpanStyle) = this
```
