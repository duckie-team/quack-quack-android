# UI Test

덕키에서 **로직이 담긴** UI 컴포저블은 UI Test 가 필수로 진행되야
합니다. [Compose UI Test](https://developer.android.com/jetpack/compose/testing) 를 이용하여 진행되며, 기본 테스팅
규칙은 [unit-test](unit-test.md) 의 규칙과 동일합니다.

### 왜?

덕키에서 UI 테스트는 아래와 같은 이유로 진행합니다.

- 많은 UI 가 있는데 모든 UI 들에 직접 여러 상태의 depth 에 도달하면서 테스트를 진행하는건 시간적으로 매우 비효율적 입니다.
- 유저가 하나의 UI 에서 정말 많은 상태를 만들 수 있으므로 모든 상태에 대해 테스트가 항상 필요합니다.

### Finder

모든 컴포저블은 테스트 코드에서 쉽고 명확한 접근을 위해 Finder
중에서 [`onNodeWithTag`](https://developer.android.com/reference/kotlin/androidx/compose/ui/test/package-summary#(androidx.compose.ui.test.SemanticsNodeInteractionsProvider).onNodeWithTag(kotlin.String,kotlin.Boolean))
또는 [`onAllNodesWithTag`](https://developer.android.com/reference/kotlin/androidx/compose/ui/test/package-summary#(androidx.compose.ui.test.SemanticsNodeInteractionsProvider).onAllNodesWithTag(kotlin.String,kotlin.Boolean))
만을 사용해야 합니다. 따라서 테스트가 필요한 모든
컴포저블에는 [`Modifier.testTag`](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier#(androidx.compose.ui.Modifier).testTag(kotlin.String))
가 강제됩니다.

### Modifier.testTag

`Modifier.testTag` 를 사용할 때 LiveLiteral 때문에 skippable 이
깨지는 [issue](https://issuetracker.google.com/issues/203559524) 가 있습니다. 이를 항상 인지하고 모든 컴포저블의 성능 벤치마크는
release 모드로 해야 합니다. testTag 때문에 LiveLiteral 를 비활성화하는건 아주 큰 손실이기 때문에 이 부분에 대해서는 LiveLiteral 을 비활성화 하지
않습니다.

### UI 테스트 범위

모든 컴포저블은 각각 디자인 variant 에 따라 디자인 변화 외에 로직에는 변화가 없을 경우 메인 variant 하나만 UI 테스트를 진행합니다. 즉, 로직에 변화가 생기는
variant 별로 하나씩만 필수로 진행해야 합니다.

### Device Configuration

UI 테스트는 디바이스의 구성 환경에 따라 여러 결과가 나올 수 있습니다. 예를 들어 폴더블 디바이스에서는 더 큰 화면에 맞게 따로 제작된 다른 testTag 를 가진 컴포저블이
보여져야 할 수도 있습니다. 덕키는 Z 플립이나 폴더볼 디바이스를 고려하지 않고 일반 디바이스를 기준으로 디자인됨으로 Google 의 Pixel 5 와 같은 일반 디바이스를
기준으로 한 번만 테스트를 진행해도 무방합니다.

### Test Rule

**컴포즈의 Test Rule
은 [`ComposeTestRule`](https://developer.android.com/reference/kotlin/androidx/compose/ui/test/junit4/ComposeTestRule)
만을
사용합니다.** [`AndroidComposeTestRule`](https://developer.android.com/reference/kotlin/androidx/compose/ui/test/junit4/AndroidComposeTestRule)
은 사용하지 않습니다. `AndroidComposeTestRule` 의 경우에는 `Activity` 를 지정해야 합니다. 매번 테스트를 위해 테스트할 컴포저블들이
있는 `Activity` 를 만들어서 지정하는건 꽤 큰 비용으로 적용될 수 있습니다. 따라서 테스트 코드를 작성할
때마다 [`ComposeTestRule.setContent`](https://developer.android.com/reference/kotlin/androidx/compose/ui/test/junit4/AndroidComposeTestRule#setContent(kotlin.Function0))
를 통해 테스트할 컴포저블을 바로 명시해야 합니다. 이렇게 테스트할 컴포저블을 바로 명시하는 것이 좀 더 직관적으로 쉽게 테스트를 파악할 수 있습니다.

