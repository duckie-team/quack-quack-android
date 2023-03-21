## 안정성 (OUT-OF-DATED)

만약 `Text`의 데코레이터를 `Button`에 추가하면 어쩌지? 하는 걱정이 들 수 있습니다.

```kotlin
Button(
    type = Secondary,
    modifier = Modifier
        .leadingIcon(Close)
        .trailingIcon(Heart)
        .highlight { text ->
            Highlight(text, "짱", SemiBold)
        },
    text = "나 좀 짱인듯? (짱 아님.. 짱되고 싶다",
    onClick = ::`am_I_awesome?`,
)
```

`Modifier.highlight`는 `Text`의 데코레이터이지만 위 코드를 보면 `Button`에 사용되고 있습니다. 하지만 걱정하지 마세요. 위는 예시일 뿐, 실제 코드에선 불가능합니다.

사실, 모든 컴포넌트에는 각각 도메인에 맞는 receiver가 붙습니다.

```kotlin
@Composable
fun QuackButton.Button(...) {}
```

위는 `Button` 도메인의 컴포넌트이므로 `QuackButton` receiver가 붙었습니다. `Modifier.highlight`는 `Text` 도메인의 데코레이터입니다.

```kotlin
interface QuackText {
    fun Modifier.highlight(...): Modifier
}
```

따라서 위 `Button`에서는 `highlight` 데코레이터를 사용할 수 없습니다.

```kotlin
@Composable
fun QuackText.Text(...) {}
```

오직 `Text`에서만 사용 가능합니다.

---

# Please see [#471](https://github.com/duckie-team/quack-quack-android/issues/471).
