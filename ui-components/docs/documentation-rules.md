## 문서화 규칙

1. 의미 없는 문서화를 진행하지 않음 (필수 X)
   ```kotlin
   /**
    * [QuackMediumBorderToggleButton] 의 모양
    */
   val QuackMediumButtonShape = RoundedCornerShape(
       size = 12.dp,
   )
   ```
   변수명이 곧 문서화를 나타내는 필드들엔 주석을 명시하지 않음
2. 모든 함수와 클래스에는 KDoc 을 작성함
