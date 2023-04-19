<p align="center">
  <img src="https://raw.githubusercontent.com/duckie-team/quack-quack-android/2.x.x/assets/logo-icon.svg" width="25%" alt="quackquack" />
</p>
<h1 align="center">QuackQuack</h1>
<h4 align="center">Highly automated and Modifier-driven Jetpack Compose design system.<br/>It's almost well-tested and adheres to the stability system.</h4>

<img src="assets/QuackTextSnapshot_ModifierSpan.png" align="right"/>

```kotlin
QuackText(
    modifier = Modifier
        .background(color = Color.White)
        .span(
            texts = listOf("QuackQuack", "Duckie"),
            style = SpanStyle(color = QuackColor.DuckieOrange.value),
        )
        .padding(30.dp),
    text = "QuackQuack is an awesome design system created by the Duckie team.",
    typography = QuackTypography.Body1,
)
```

---

## The full documentation is available on the project website: https://quackquack.duckie.team/android
