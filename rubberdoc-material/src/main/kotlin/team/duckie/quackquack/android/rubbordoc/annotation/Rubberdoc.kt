package team.duckie.quackquack.android.rubbordoc.annotation

public enum class RubberdocType {
  Icon,
  Color,
  Component,
}

@Target(AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
public annotation class Rubberdoc(
  public val title: String,
  public val document: String,
  public val type: RubberdocType = RubberdocType.Component,
)
