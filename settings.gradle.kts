include(":common")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "quack-quack"
include(
    ":playground",
    ":lint-compose",
    ":lint-core",
    ":lint-writing",
    ":ui-components",
)
