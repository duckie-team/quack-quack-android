enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")
rootProject.name = "quack-quack"
include(
    ":playground",
    ":lint-compose",
    ":lint-core",
    ":lint-writing",
    ":ui-components",
)
