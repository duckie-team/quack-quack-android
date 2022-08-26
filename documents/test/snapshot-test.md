# Snapshot Test

**모든** UI 컴포저블은 스냅샷 테스트가 필수로 진행되야 합니다. 기본 테스팅 규칙은 [unit-test](unit-test.md) 의 규칙과 동일합니다.

### 왜?

덕키에서 스냅샷 테스트는 아래와 같은 이유로 진행합니다.

- 캡처한 UI 상태를 PNG 이미지 형태로 저장하여 해당 이미지 파일을 디자이너분께 바로 전달하여 좀 더 정확하게 UI 를 검증할 수 있습니다.

- 내가 의도하지 않은 UI 에 변화가 생겼는지를 검사할 수 있습니다. 예를 들어 안드로이드의 디자인 시스템은 기본적으로 머터리얼을 사용합니다. 만약 머터리얼이 업데이트 되면서 Checkbox 를 그리는 방식이 달라졌다면 머터리얼 changelog 를 보지 않는 이상 알아차리기 쉽지 않습니다 (Checkbox 의 사용 비중이 앱 내에서 크지 않다고 가정합니다). 의도치 않은 Checkbox 의 변화를 알아차리기 위해선 직접 Checkbox 가 쓰이고 있는 UI 에 도달해야 합니다. 하지만 스냅샷 테스트를 이용한다면 Checkbox 가 변하기 전의 golden 과 비교하여 쉽게 변경을 알아차릴 수 있습니다.

- 직접 확인하는 UI 테스트의 경우에는 확인하고 싶은 UI 를 보기 위해, 기기 실행 후 해당 UI 가 보여지는 depth 까지 매번 클릭하여 들어가야 하지만, 스냅샷 테스트를 이용한다면 내가 원하는 UI 가 캡처된 이미지 파일을 바로 클릭하는 것으로 원하는 UI 의 결과 확인이 가능합니다. 이렇게 스냅샷 테스트를 진행하는 것만으로도 많은 UI 테스트의 시간을 줄일 수 있습니다.
- 스냅샷 캡처만 하면 되므로 정말 쉽게 여러 variant 디자인을 테스트할 수 있습니다. 덕분에 font scale, 다크 모드, 폴더블 디바이스 등등 여러 분야 UI 테스트를 정말 쉽게 진행하게 됩니다.

### 라이브러리

스냅샷 테스트를 위해 [paparazzi](https://github.com/cashapp/paparazzi) 라이브러리를 사용합니다. 현재 안드로이드에서 스냅샷 테스트를 위한 대표적인 라이브러리는 airbnb 의 [Showkase](https://github.com/airbnb/Showkase), facebook 의 [screenshot-tests-for-android](https://github.com/facebook/screenshot-tests-for-android), pedrovgs 의 [Shot](https://github.com/pedrovgs/Shot), cashapp 의 [paparazzi](https://github.com/cashapp/paparazzi) 가 있지만 paparazzi 만 유일하게 **에뮬레이터나 실기기 같은 디바이스 연결 없이** 단위 테스트 단에서 테스트가 진행됩니다.

하지만 paparazzi 역시 여러 단점이 존재합니다.

1. SDK 버전 33 이상을 타겟으로 하면 layoutlib 의 [Bridge](https://cs.android.com/android/platform/superproject/+/master:frameworks/layoutlib/bridge/src/com/android/layoutlib/bridge/Bridge.java;l=85) 클래스를 초기화하지 못해 빌드 에러가 발생합니다. 이 문제는 paparazzi 의 컴파일 SDK 버전을 32 로 변경하는 것으로 해결이 가능합니다. ([#489](https://github.com/cashapp/paparazzi/issues/489))
2. 항상 전체 화면으로 캡처합니다. 액티비티에 들어갈 전체 화면 자체를 캡처하는 경우에는 문제가 없지만, 세부 UI 컴포넌트를 단독으로 캡처하는 경우에는 디바이스 프레임이 배경으로 들어가서 디바이스 전체 사이즈로 캡처됩니다. 이 문제는 paparazzi 에서 캡처할 때 사용하는 가상 디바이스의 높이를 1로 제한하는 것으로 height 는 wrap_content 로 만듦으로써 50% 해결이 가능합니다. 50% 해결인 이유는 width 는 항상 match_parent 로 캡처됩니다. ([#383](https://github.com/cashapp/paparazzi/issues/383))
3. paparazzi 에서 안드로이드 리소스를 가져오는 구현의 한계로 library 모듈에만 작동합니다. 즉, app 모듈 하나로 구성된 단일 모듈 프로젝트에서는 paparazzi 를 사용할 수 없습니다. ([#105](https://github.com/cashapp/paparazzi/issues/105))

따라서 paparazzi 를 정상적으로 사용하기 위해선 아래와 같은 기본 설정이 필요합니다.

```kotlin
class Paparazzi {
  @get:Rule
  val paparazzi = Paparazzi(
    environment = detectEnvironment().copy( // #489 해결
      platformDir = "${androidHome()}/platforms/android-32",
      compileSdkVersion = 32,
    ),
    deviceConfig = ${본인이 원하는 디바이스 기종}.copy(
      softButtons = false, // 추가로 스냅샷 이미지에서 UI 컴포넌트 이미지만 확인하기 위해 Soft Button 을 가려야 합니다.
      screenHeight = 1, // #383 해결
    ),
    renderingMode = RenderingMode.V_SCROLL, // #383 해결
  )
}
```

### 스냅샷 테스트

캡처된 스냅샷 이미지들은 꽥꽥 repository 에 다음과 같은 사유로 저장되지 않습니다.

- PNG 로 repository 의 사이즈가 커지는 걸 방지하기 위함

따라서 로컬에서 golden image 를 캡처해서 보관하는 식으로 진행해야 합니다.

### CI 에서의 스냅샷

꽥꽥의 CI 에는 현재 UI 컴포저블들의 스냅샷을 찍어서 [quack-ui.duckie.team](https://quack-ui.duckie.team/) 에 배포하는 과정이 있습니다. 추후 이렇게 배포된 스냅샷 이미지들을 가지고 혜진님의 검토 후 merge 가 진행됩니다. 이런 이유로 모든 UI 컴포저블은 스냅샷 테스트가 작성돼야 합니다.

### 여러 디자인 variant 의 스냅샷

paparazzi + [Showkase](https://github.com/airbnb/Showkase) + ParameterizedTest 를 이용하여 여러 디자인 variant 를 보일러플레이트 없이 스냅샷을 캡처할 수 있습니다. 덕키는 스냅샷 테스트를 위해 Showkase 의 [ShowkaseElementsMetadata](https://github.com/airbnb/Showkase/blob/master/showkase/src/main/java/com/airbnb/android/showkase/models/ShowkaseElementsMetadata.kt) 만을 이용합니다.

**모든 UI 컴포저블은 다양한 variant 디자인의 검증을 위해 이 과정이 필수로 요구됩니다.**

###  ParameterizedTest

덕키에서 ParameterizedTest 는 편의를 위해 Google 의 [TestParameterInjector](https://github.com/google/TestParameterInjector) 를 사용합니다.

TestParameterInjector 는 테스트가 진행될 때 함수명 뒤에 `[${index}.${value.toString()}]` 을 자동으로 추가합니다. **따라서 모든 parameterized value 는 래퍼 클래스를 만들어서 `toString()` override 가 필요합니다.**

### 명명

**테스트 클래스와 함수의 네이밍은 스냅샷 이미지 HTML 리포트 생성에 영향을 미칩니다.** 스냅샷 이미지의 파일명은 아래와 같은 규칙이 적용됩니다.

```
[테스트 폴더 패키지명]_[테스트 클래스명]_[테스트 함수명][ParameterizedTest label]_[Paparazzi#snapshot 의 name 인자]
```

정확한 스냅샷 이미지 HTML 리포트 생성을 위해 요구되는 네이밍 조건은 다음과 같습니다.

- 테스트 폴더 패키지명과 테스트 클래스명에는 `_` 가 들어가면 안됩니다.
- 테스트 클래스명은 본인이 테스트하고자 하는 UI 파트의 이름이 들어가야 합니다. **즉, 하나의 클래스에는 하나의 UI 파트만 들어가야 합니다.** 예를 들어 클래스명으로 다음이 될 수 있습니다: Button, Toggle, Tab, Tag, TextField, Typography, DropDown, BottomSheet, Image, Label. **간단한 리포트를 위해 클래스명에 Snapshot 같은 접미어를 붙이면 안됩니다.** 오직 UI 파트명 하나로 명명돼야 합니다.
- 테스트 함수명으론 현재 테스트 하려는 UI 컴포넌트명을 대소문자 구분하여 그대로 사용해야 합니다.
- `Paparazzi#snapshot 의 name 인자` 로 `[`, `]` 접두사/접미사와 현재 value 의 타입과 이름을 `:` 로 구분하여 명시해야 합니다. 여러개의 variant 가 있다면 `-`로 구분해야 합니다. (`_` 가 아닙니다)

**띄어쓰기 하나도 HTML 리포트 생성에 영향을 미칩니다.** 위 규칙들을 따르는 올바른 테스트 클래스는 아래와 같습니다.

```kotlin
package team.duckie.quackquack.ui

@RunWith(TestParameterInjector::class)
class Button {
    @get:Rule
    val paparazzi = Paparazzi(
        environment = detectEnvironment().copy(
            platformDir = "${androidHome()}/platforms/android-32",
            compileSdkVersion = 32,
        ),
        deviceConfig = DeviceConfig.PIXEL_5.copy(
            softButtons = false,
            screenHeight = 1,
        ),
        renderingMode = SessionParams.RenderingMode.V_SCROLL,
    )

    private object ColorVariantProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues() = Showkase.getMetadata().colorList.map(::ColorVariant)
    }

    private object TypographyVariantProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues() = Showkase.getMetadata().typographyList.map(::TextStyleVariant)
    }

    @Test
    fun QuackLargeButton(
        @TestParameter(valuesProvider = ColorVariantProvider::class) colorVariant: ColorVariant,
        @TestParameter(valuesProvider = TypographyVariantProvider::class) textStyleVariant: TextStyleVariant,
    ) {
        paparazzi.snapshot(name = "[color:${colorVariant.name}]-[textStyle:${textStyleVariant.name}]") {
            Box( // 이 예제에서는 QuackLargeButton 대신 Box 를 캡처했지만 실제 테스트에서는 실제 UI 컴포넌트가 사용돼야 합니다.
                modifier = Modifier
                    .size(100.dp)
                    .background(color = colorVariant.color),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Bye, world!",
                    style = textStyleVariant.textStyle,
                )
            }
        }
    }
}

class ColorVariant(value: ShowkaseBrowserColor) {
    val color = value.color
    val name = value.colorName
    override fun toString() = "color"
}

class TextStyleVariant(value: ShowkaseBrowserTypography) {
    val textStyle = value.textStyle
    val name = value.typographyName
    override fun toString() = "typography"
}

```
