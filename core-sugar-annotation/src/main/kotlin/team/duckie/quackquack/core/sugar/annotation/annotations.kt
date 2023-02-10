package team.duckie.quackquack.core.sugar.annotation

// TODO: 문서 제공
public annotation class Sugar(val name: String = DEFAULT_NAME) {
    public companion object {
        public const val DEFAULT_NAME: String = "DefaultName"
    }
}

// TODO: 문서 제공
public annotation class SugarToken
