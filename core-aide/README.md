# core-aide

본 모듈은 디자인 시스템을 지키기 위한 일종의 보호벽을 제공합니다.

### Why?

[Jetpack Compose](https://developer.android.com/jetpack/compose)의 [Modifier](https://developer.android.com/jetpack/compose/modifiers)는 정말 강력합니다. 정말이지 너무 강력합니다. Modifier를 사용한다면 우리가 사전에 정한 디자인 스펙도 무시할 수 있습니다.

예를 들어 다음과 같은 컴포넌트가 있습니다.

![tag](assets/tag.svg)

이 컴포넌트는 스펙에 따르면 배경색이 DuckieOrange로 보여야 하지만, `Modifier.background(color = Color.Blue)`를 사용한다면 파란 색으로 변경할 수 있습니다.

또한 모든 디자인 컴포넌트는 기본적으로 font-scale을 존중한다는 원칙 하에 설계됩니다. 따라서 모든 컴포넌트는 사이즈를 고정하지 않으며, 패딩과 font-scale을 이용하여 동적으로 계산합니다. 하지만 `Modifier.size`를 사용한다면 이 원칙을 무시할 수 있습니다.

꽥꽥의 디자인 원칙은 사용자에게 최고의 경험을 선사하기 위해선 꼭 지켜져야 합니다. 이를 보장하기 위해 개발자가 원칙을 어겼을 때 빌드 에러 혹은 경고를 발생시키고자 합니다.

### How?

방법은 간단합니다. 원칙을 어길 수 있는 Modifier가 꽥꽥 컴포넌트에 사용됨이 감지됐을 때 경우에 맞게 빌드 에러 혹은 경고를 발생시킵니다.

원칙을 어기는 Modifier는 다음과 같습니다.

- `Modifier.background`
- `Modifier.size`
- `Modifier.graphicsLayer`
- `Modifier.border`
- `Modifier.alpha`
- `Modifier.clip`
- `Modifier.drawBehind`
- ... 기타 등등 (TBD)
