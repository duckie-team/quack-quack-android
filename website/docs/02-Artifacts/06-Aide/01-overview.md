---
sidebar_label: 'Overview'
---

# aide

본 모듈은 디자인 시스템을 지키기 위한 일종의 보호벽을 제공합니다.

## Why?

[Jetpack Compose](https://developer.android.com/jetpack/compose)의 [`Modifier`](https://developer.android.com/jetpack/compose/modifiers)는 정말 강력합니다. 정말이지 너무 강력합니다. `Modifier`를 사용한다면 우리가 사전에 정한 디자인 스펙도 무시할 수 있습니다.

예를 들어 다음과 같은 컴포넌트가 있습니다.

![tag](images/tag.svg)

이 컴포넌트는 스펙에 따르면 배경색이 DuckieOrange로 보여야 하지만, `Modifier.background(color = Color.Blue)`를 사용한다면 파란색으로 변경할 수 있습니다.

또한 모든 디자인 컴포넌트는 기본적으로 font-scale을 존중한다는 원칙 하에 설계됩니다. 따라서 모든 컴포넌트는 사이즈를 고정하지 않으며, 패딩과 font-scale을 이용하여 동적으로 계산합니다. 하지만 `Modifier.size`를 사용한다면 이 원칙을 무시할 수 있습니다.

꽥꽥의 디자인 원칙은 사용자에게 최고의 경험을 선사하기 위해선 꼭 지켜져야 합니다. 이를 보장하기 위해 개발자가 원칙을 어겼을 때 경고를 발생시키고자 합니다.

컴포넌트에 맞지 않는 데코레이터 `Modifier`가 사용됐을 때도 경고를 발생시킵니다.

## How?

원칙을 어길 수 있는 `Modifier`가 꽥꽥 컴포넌트에 사용됨이 감지됐을 때 경우에 맞게 경고를 발생시킵니다.

원칙을 어길 수 있는 `Modifier`는 다음과 같습니다.

- `Modifier.background`
- `Modifier.size`
- `Modifier.graphicsLayer`
- `Modifier.border`
- `Modifier.alpha`
- `Modifier.clip`
- `Modifier.drawBehind`
- ... 기타 등등

컴포넌트에 맞지 않는 데코레이터 `Modifier`가 사용됐을 때도 경고를 발생시킵니다. 

예를 들어 Text 컴포넌트에서 Button 컴포넌트의 데코레이터 `Modifier`가 사용됐을 때 경고를 발생시킬 수 있습니다.

```kotlin
Button(
    type = Secondary,
    modifier = Modifier
        .leadingIcon(Close)
        .trailingIcon(Heart)
        .highlight { text -> // Modifier.highligh: Text의 데코레이터 사용됨 -> 빌드 에러 발생
            Highlight(text, "짱", SemiBold)
        },
    text = "나 좀 짱인듯? (짱 아님.. 짱되고 싶다",
    onClick = ::`am_I_awesome?`,
)
```

컴포넌트별 사용 가능한 데코레이터 `Modifier`는 컴포넌트의 도메인에 따라 결정되고, 컴포넌트의 도메인은 컴포넌트가 정의된 파일명으로 결정됩니다.

예를 들어 `text.kt` 파일에 있는 컴포넌트는 모두 text 도메인에 해당하고, `button.kt` 컴포넌트는 모두 button 도메인에 해당합니다.

컴포넌트별 사용 가능한 데코레이터 `Modifier`는 해당 컴포넌트의 도메인 파일 안에`@DecorateModifier` 어노테이션이 붙은 `Modifier`로 제한됩니다.

예를 들어 `text.kt` 파일에 다음과 같은 코드가 있습니다.

```kotlin
@Composable
fun Text(
    modifier: Modifier = Modifier,
    text: String,
) {
    BasicText(modifier = modifier, text = text)
}

@DecorateModifier
fun Modifier.highlight(text: String, color: Color): Modifier {
    // ... awesome code
}
```

위와 같은 경우에 `Text` 컴포넌트는 데코레이션 `Modifier`로 `Modifier.highlight`만 허용됩니다.

## Caveat

- 개별 statement로 분리된 `Modifier`는 감지하지 못 합니다.

## Download ![](https://img.shields.io/maven-central/v/team.duckie.quackquack.aide/aide?style=flat-square)

```kotlin
lintChecks("team.duckie.quackquack.aide:aide:${version}")
```