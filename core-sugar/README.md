# core-sugar

본 모듈은 [`core`](../core) 모듈의 문법 설탕을 제공합니다.

### Example

```kotlin
 @SugarToken
 enum class QuackButton {
     Default,
     Normal,
     Flat,
     ;
 }

 @Sugar
 fun Button(type: QuackButton, modifier: Modifier = Modifier, text: String) {}

 // generation
 fun DefaultButton(modifier: Modifier = Modifier, text: String) {} // equals to Button(type = QuackButton.Default, ...)
 fun NormalButton(modifier: Modifier = Modifier, text: String) {} // equals to Button(type = QuackButton.Normal, ...)
 fun FlatButton(modifier: Modifier = Modifier, text: String) {} // equals to Button(type = QuackButton.Flat, ...)
 ```
