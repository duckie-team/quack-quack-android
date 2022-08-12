import org.gradle.api.artifacts.dsl.DependencyHandler as DependencyScope

private const val Implementation = "implementation"
private const val ImplementationDebug = "debugImplementation"
private const val TestRuntimeOnly = "testRuntimeOnly"
private const val TestImplementation = "testImplementation"
private const val Api = "api"

fun DependencyScope.setupJunit(core: Any, engine: Any) {
    delegate(method = TestImplementation, core)
    delegate(method = TestRuntimeOnly, engine)
}

fun DependencyScope.implementations(vararg paths: Any) {
    delegate(method = Implementation, paths = paths)
}

fun DependencyScope.debugImplementations(vararg paths: Any) {
    delegate(method = ImplementationDebug, paths = paths)
}

fun DependencyScope.apis(vararg paths: Any) {
    delegate(method = Api, paths = paths)
}

private fun DependencyScope.delegate(method: String, vararg paths: Any) {
    paths.forEach { path -> add(method, path) }
}