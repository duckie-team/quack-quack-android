object Dependencies {
    const val Ksp = "com.google.devtools.ksp:symbol-processing-api:${Versions.Ksp}"
    const val FirebaseBom = "com.google.firebase:firebase-bom:${Versions.FirebaseBom}"
    const val Coroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Essential.Coroutines}"

    object Orbit {
        const val Test = "org.orbit-mvi:orbit-test:${Versions.Orbit}"
        const val Main = "org.orbit-mvi:orbit-viewmodel:${Versions.Orbit}"
    }

    object FirebaseEachKtx { // 각각 쓰이는 모듈이 다름
        const val Storage = "com.google.firebase:firebase-storage-ktx"
        const val Performance = "com.google.firebase:firebase-perf-ktx"
        const val Analytics = "com.google.firebase:firebase-analytics-ktx"
        const val RemoteConfig = "com.google.firebase:firebase-config-ktx"
        const val Crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    }

    object EachKtx {
        const val Core = "androidx.core:core-ktx:${Versions.Ktx.Core}"
        const val Fragment = "androidx.fragment:fragment-ktx:${Versions.Ktx.Fragment}"
        const val Activity = "androidx.activity:activity-ktx:${Versions.Ktx.Activity}"
        const val Lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Ktx.Lifecycle}"
    }

    object EachUi {
        const val Browser = "androidx.browser:browser:${Versions.Ui.Browser}"
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.Ui.AppCompat}"
        const val PhotoEditor = "com.burhanrashid52:photoeditor:${Versions.Ui.PhotoEditor}"
        const val Material = "com.google.android.material:material:${Versions.Ui.Material}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Ui.ConstraintLayout}"
    }

    val SharedKtx = listOf(
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Ktx.Lifecycle}",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Ktx.Lifecycle}"
    )

    val PresentationOnlyKtx = listOf(
        "com.google.android.play:core-ktx:${Versions.Ktx.PlayCore}",
    )

    val Compose = listOf(
        "androidx.compose.material:material:${Versions.Compose.Main}",
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "com.github.skydoves:landscapist-coil:${Versions.Compose.Landscapist}",
        "com.google.accompanist:accompanist-placeholder:${Versions.Compose.Accompanist}",
        "com.google.accompanist:accompanist-swiperefresh:${Versions.Compose.Accompanist}",
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.LifecycleViewModel}",
        "com.google.accompanist:accompanist-navigation-animation:${Versions.Compose.Accompanist}",
        "androidx.constraintlayout:constraintlayout-compose:${Versions.Compose.ConstraintLayout}"
    )

    val Ui = listOf(
        "androidx.core:core-splashscreen:${Versions.Ui.Splash}",
        "com.google.android.gms:play-services-oss-licenses:${Versions.OssLicense.Main}"
    )

    val Jackson = listOf(
        "com.fasterxml.jackson.core:jackson-core:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.core:jackson-databind:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.core:jackson-annotations:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.Util.Jackson}"
    )

    val Network = listOf(
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:converter-jackson:${Versions.Network.Retrofit}"
    )

    val Login = listOf(
        "com.kakao.sdk:v2-user:${Versions.Login.Kakao}",
        "com.navercorp.nid:oauth:${Versions.Login.Naver}"
    )

    object Util { // Erratum 은 :presentation 에서만 쓰임
        const val Erratum = "land.sungbin:erratum:${Versions.Util.Erratum}"
        const val Logeukes = "land.sungbin:logeukes:${Versions.Util.Logeukes}"
    }

    val Analytics = listOf(
        "com.github.anrwatchdog:anrwatchdog:${Versions.Analytics.AnrWatchDog}"
    )

    val Location = listOf(
        "com.github.BirjuVachhani:locus-android:${Versions.Location.Locus}",
        "com.google.android.gms:play-services-location:${Versions.Location.GmsLocation}",
    )

    val GoogleMap = listOf(
        "com.google.android.gms:play-services-maps:${Versions.Location.GmsMap}",
        "com.google.maps.android:maps-compose:${Versions.Location.GoogleMapCompose}",
    )

    object Jetpack {
        const val Room = "androidx.room:room-ktx:${Versions.Jetpack.Room}"
        const val Hilt = "com.google.dagger:hilt-android:${Versions.Jetpack.Hilt}"
        const val DataStore =
            "androidx.datastore:datastore-preferences:${Versions.Jetpack.DataStore}"
    }

    object Compiler {
        const val RoomKsp = "androidx.room:room-compiler:${Versions.Jetpack.Room}"
        const val Hilt = "com.google.dagger:hilt-android-compiler:${Versions.Jetpack.Hilt}"
    }

    object Test { // dependency scope 가 각각 다름
        const val Hamcrest = "org.hamcrest:hamcrest:${Versions.Test.Hamcrest}"
        const val JunitApi = "org.junit.jupiter:junit-jupiter-api:${Versions.Test.JUnit}"
        const val JunitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.Test.JUnit}"
        const val Coroutine =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Test.Coroutine}"
    }

    object Debug {
        const val LeakCanary =
            "com.squareup.leakcanary:leakcanary-android:${Versions.Util.LeakCanary}"
        val Compose = listOf(
            "androidx.compose.ui:ui-tooling:${Versions.Compose.Main}",
            "androidx.compose.ui:ui-tooling-preview:${Versions.Compose.Main}"
        )
    }
}
