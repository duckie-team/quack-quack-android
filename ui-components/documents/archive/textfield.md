# QuackTextField 구현 노트

### 고려했던 대상

1. 다양한 사용자 font scale 에도 TextField 의 사이즈가 일정하게 보여져야 함
2. TextField 의 사이즈에 맞게 decoration item 들의 사이즈도 변경돼야 함

### 참고 사항

1. decoration item 은 항상 TextField 의 높이 사이에 center 로 들어감
2. TextField 는 항상 single line 임

### 구현 방법

1. TextField 의 높이에 맞게 decoration box 를 꽉 채우고, content align 을 center 로 줌
2. TextField 에 텍스트가 꽉 찰 수 있어서, decoration box 의 가로 길이를 구하고, 해당 가로 길이와 decoration box 와 TextField 사이의 간격 만큼 뺀 값으로 TextField 의 가로 길이를 지정함

### 시도했던 방법

```kotlin
private const val QuackTextFieldLayoutId = "QuackTextFieldContent"
private const val QuackTextFieldLeadingContentLayoutId = "QuackTextFieldLeadingContent"
private const val QuackTextFieldTrailingContentLayoutId = "QuackTextFieldTrailingContent"

@Composable
internal fun QuackTextFieldDecorationBox(
    width: QuackWidth,
    height: QuackHeight,
    quackTextField: @Composable () -> Unit,
    leadingContent: @Composable (() -> Unit)?,
    trailingContent: @Composable (() -> Unit)?,
) {
    Layout(
        modifier = Modifier
            .applyQuackSize(
                width = width,
                height = height,
            )
            .padding(
                bottom = QuackTextFieldBottomPadding,
            ),
        content = {
            if (leadingContent != null) {
                Box(
                    modifier = Modifier
                        .layoutId(
                            layoutId = QuackTextFieldLeadingContentLayoutId,
                        )
                        .wrapContentSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    leadingContent()
                }
            }
            Box(
                modifier = Modifier
                    .layoutId(
                        layoutId = QuackTextFieldLayoutId,
                    )
                    .wrapContentSize(),
                contentAlignment = Alignment.Center,
            ) {
                quackTextField()
            }
            if (trailingContent != null) {
                Box(
                    modifier = Modifier
                        .layoutId(
                            layoutId = QuackTextFieldTrailingContentLayoutId,
                        )
                        .wrapContentSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    trailingContent()
                }
            }
        },
    ) { measurables, constraints ->
        // TextField
        val quackTextFieldPlaceable = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldLayoutId
        }?.measure(
            constraints = constraints,
        ) ?: npe(
            lazyMessage = {
                notFoundRequiredLayoutIdForQuackTextFieldMessage(
                    layoutId = QuackTextFieldLayoutId,
                )
            },
        )

        var textFieldWidth = quackTextFieldPlaceable.width
        val decorationContentGap = QuackTextFieldDecorationContentHorizontalPadding.roundToPx()

        // decoration 아이템의 높이는 TextField 의 높이 보다 클 수 없음
        val decorationItemConstraints = constraints.copy(
            height = quackTextFieldPlaceable.height,
        )

        // leading content
        val leadingContentPlaceable = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldLeadingContentLayoutId
        }?.measure(
            constraints = decorationItemConstraints,
        )

        if (leadingContentPlaceable != null) {
            // TextField 의 가로 길이는 기본적으로 decoration 아이템들의 가로 길이를
            // 포함하고 있음. 만약 decoration 아이템이 존재한다면 TextField 의
            // 가로 길이를 decoration 아이템의 가로 길이만큼 줄여야 함.
            // 따라서 TextField 의 가로 길이에서 decoration 아이템의 가로 길이와
            // TextField 와 decoration 아이템 사이에 들어갈 간격 만큼 제외한 값으로
            // TextField 의 가로 길이로 설정함.
            textFieldWidth -= leadingContentPlaceable.width - decorationContentGap
        }

        // trailing content
        val trailingContentPlaceable = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldTrailingContentLayoutId
        }?.measure(
            constraints = decorationItemConstraints,
        )

        if (trailingContentPlaceable != null) {
            textFieldWidth -= trailingContentPlaceable.width - decorationContentGap
        }

        layout(
            width = quackTextFieldPlaceable.width,
            height = quackTextFieldPlaceable.height,
        ) {
            // leading content
            leadingContentPlaceable?.place(
                x = 0,
                y = 0,
            )

            // TextField
            quackTextFieldPlaceable.place(
                x = 0,
                y = 0,
            )

            // trailing content
            trailingContentPlaceable?.place(
                x = quackTextFieldPlaceable.width - trailingContentPlaceable.width,
                y = 0,
            )
        }
    }
}

@Suppress("SameParameterValue")
private fun notFoundRequiredLayoutIdForQuackTextFieldMessage(
    layoutId: String,
) = """
    |QuackTextField 를 구성하는데 필요한 필수 layoutId 를 가진 컴포저블을
    |찾을 수 없습니다. 올바른 방법으로 QuackTextField 를 사용했는지 확인해 보세요.

    |필요한 layoutId: $layoutId
    """.trimMargin()
```

이렇게 Layout 으로 아예 measurement 부터 다시 하는 방법을 4시간 끝에 생각해 냈으나, 위 방식으로 하게 되면 위 `# 구현 방법` 에서 2번째를 달성하기 위해 re-measure 가 필요해짐. 하지만 이는 컴포즈에서 금지된 방식임.

따라서 `Modifier.layout`을 통해 레이아웃 계산을 처음부터 다시 하는 방법을 채택함. 이는 QuackTab 에서도 사용하고 있는 방식임.

-> 6시간 고민 끝에 이 생각이 잘못됐음을 알아냄! 이 방법이 맞았음.

### 현재 구현의 한계

TextField 의 사이즈는 font scale 에 맞게 대응이 되나, decoration box 에 들어가는 아이콘들은 font scale 배수에 대응되지 않음.

이를 극복하기 위해선 TextField 의 높이에 따라 Icon 사이즈를 증가하는 일정된 배수가 필요함. [혜진님 슬랙 메시지](https://sungbinland.slack.com/archives/C03CM18M529/p1662186262016929?thread_ts=1662181872.638669&cid=C03CM18M529) 참고.
