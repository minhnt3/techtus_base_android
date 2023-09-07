import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

object Android {
    const val APPLICATION_ID = "jp.app.compose"

    const val COMPILE_SDK_VERSION = 33
    const val MIN_SDK_VERSION = 26
    const val TARGET_SDK_VERSION = 33

    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0"
    const val VERSION_CODE_DEV = 1
    const val VERSION_NAME_DEV = "1.0.0"
    const val VERSION_CODE_STAGING = 1
    const val VERSION_NAME_STAGING = "1.0.0"
}

object Versions {
    const val KOTLIN = "1.8.10"
    const val COMPOSE = "1.4.3"
    const val ACCOMPANIST = "0.30.1"
    const val COROUTINES = "1.7.2"
    const val DAGGER_HILT = "2.44.2"
    const val RETROFIT = "2.9.0"
    const val MOSHI = "1.15.0"
    const val ROOM = "2.4.2"
    const val KTLINT = "0.45.2"
}

object Dependencies {
    const val LEAK_CANARY = "com.squareup.leakcanary:leakcanary-android:2.9.1"

    const val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
    const val COMPOSE_UI_UTIL = "androidx.compose.ui:ui-util:${Versions.COMPOSE}"
    const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
    const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
    const val COMPOSE_UI_TEST_JUNIT = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
    const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE}"

    const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE}"
    const val COMPOSE_MATERIAL_3 = "androidx.compose.material3:material3:1.1.1"
    const val COMPOSE_MATERIAL_ICONS_EXTENDED = "androidx.compose.material:material-icons-extended:${Versions.COMPOSE}"
    const val COMPOSE_ANIMATION = "androidx.compose.animation:animation:${Versions.COMPOSE}"

    const val CORE_KTX = "androidx.core:core-ktx:1.10.1"
    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:1.7.2"
    const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:2.5.3"
    const val HILT_NAVIGATION_COMPOSE = "androidx.hilt:hilt-navigation-compose:1.0.0"
    const val WINDOW = "androidx.window:window:1.0.0"
    const val SPLASH_SCREEN = "androidx.core:core-splashscreen:1.0.1"

    const val NAVIGATION_ANIMATION = "com.google.accompanist:accompanist-navigation-animation:${Versions.ACCOMPANIST}"
    const val INSETS = "com.google.accompanist:accompanist-insets-ui:${Versions.ACCOMPANIST}"
    const val PLACE_HOLDER = "com.google.accompanist:accompanist-placeholder:${Versions.ACCOMPANIST}"
    const val PERMISSIONS = "com.google.accompanist:accompanist-permissions:1.4.3"

//    const val LIFECYCLE_VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.LIFECYCLE}"
//    const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
//    const val LIFECYCLE_COMMON_JAVA_8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.LIFECYCLE}"
//    const val LIFECYCLE_FRAGMENT = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT}"
//    const val LIFECYCLE_FRAGMENT_TEST = "androidx.fragment:fragment-testing:${Versions.FRAGMENT}"

    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"

    const val DAGGER_HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.DAGGER_HILT}"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.DAGGER_HILT}"
    const val DAGGER_HILT_CORE = "com.google.dagger:hilt-core:${Versions.DAGGER_HILT}"
    const val DAGGER_HILT_TEST = "com.google.dagger:hilt-android-testing:${Versions.DAGGER_HILT}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_MOSHI = "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}"
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2"

    const val MOSHI = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
    const val MOSHI_CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"
    const val JSON = "org.json:json:20220320"

    const val SECURITY_CRYPTO = "androidx.security:security-crypto:1.0.0"

    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_TEST = "androidx.room:room-testing:${Versions.ROOM}"
    const val ROOM_COROUTINES = "androidx.room:room-coroutines:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"

    const val JUNIT = "junit:junit:4.13.2"
    const val TEST_CORE = "androidx.test:core-ktx:1.4.0"
    const val TEST_RULE = "androidx.test:rules:1.4.0"
    const val JUNIT_KTX = "androidx.test.ext:junit-ktx:1.1.3"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:3.4.0"
    const val MOCKK = "io.mockk:mockk:1.13.5"
    const val MOCKK_ANDROID = "io.mockk:mockk-android:1.13.5"
    const val MOCKK_AGENT = "io.mockk:mockk-agent:1.13.5"
    const val KOTLIN_JUNIT = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.KOTLIN}"
    const val ARCH_CORE_TEST = "androidx.arch.core:core-testing:2.1.0@aar"
    const val GOOGLE_TRUTH = "com.google.truth:truth:1.1.2"

    const val KTLINT = "com.pinterest:ktlint:${Versions.KTLINT}"
    const val KTLINT_CORE = "com.pinterest.ktlint:ktlint-core:${Versions.KTLINT}"

    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:30.2.0"
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics-ktx"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics-ktx"

    const val COIL = "io.coil-kt:coil-compose:2.4.0"

    const val ARSCENEVIEW = "io.github.sceneview:arsceneview:0.10.1"
}

inline val PluginDependenciesSpec.androidApplication: PluginDependencySpec get() = id("com.android.application")
inline val PluginDependenciesSpec.androidLib: PluginDependencySpec get() = id("com.android.library")
inline val PluginDependenciesSpec.kotlinAndroid: PluginDependencySpec get() = id("kotlin-android")
inline val PluginDependenciesSpec.kotlin: PluginDependencySpec get() = id("kotlin")
inline val PluginDependenciesSpec.kotlinKapt: PluginDependencySpec get() = id("kotlin-kapt")
inline val PluginDependenciesSpec.hilt: PluginDependencySpec get() = id("dagger.hilt.android.plugin")
inline val PluginDependenciesSpec.kotlinParcelize: PluginDependencySpec get() = id("kotlin-parcelize")
inline val PluginDependenciesSpec.googleServices: PluginDependencySpec get() = id("com.google.gms.google-services")
inline val PluginDependenciesSpec.firebaseCrashlytics: PluginDependencySpec get() = id("com.google.firebase.crashlytics")

inline val DependencyHandler.domain get() = project(":domain")
inline val DependencyHandler.data get() = project(":data")
inline val DependencyHandler.shared get() = project(":shared")
inline val DependencyHandler.resources get() = project(":resources")
inline val DependencyHandler.commonUi get() = project(":common_ui")
inline val DependencyHandler.testUtil get() = project(":test_util")
inline val DependencyHandler.features get() = project(":features")
