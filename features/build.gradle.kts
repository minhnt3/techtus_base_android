plugins {
    androidLib
    kotlinAndroid
    kotlinKapt
    hilt
}

buildscript {
    apply(from = "../buildSrc/ktlint.gradle.kts")
}

android {
    namespace = "jp.android.app.features"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE
    }
}

dependencies {
    implementation(domain)
    implementation(shared)
    implementation(resources)
    implementation(commonUi)

    implementation(Dependencies.COMPOSE_UI)
    implementation(Dependencies.COMPOSE_UI_TOOLING_PREVIEW)
    debugImplementation(Dependencies.COMPOSE_UI_TOOLING)

    implementation(Dependencies.COMPOSE_MATERIAL)
    implementation(Dependencies.COMPOSE_MATERIAL_ICONS_EXTENDED)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.HILT_NAVIGATION_COMPOSE)
    implementation(Dependencies.NAVIGATION_ANIMATION)
    implementation(Dependencies.ACTIVITY_COMPOSE)
    implementation(Dependencies.SPLASH_SCREEN)

    implementation(Dependencies.COROUTINES_ANDROID)

    implementation(Dependencies.DAGGER_HILT_ANDROID)
    implementation(Dependencies.DAGGER_HILT_CORE)
    kapt(Dependencies.DAGGER_HILT_COMPILER)

    //unit test
    testImplementation(Dependencies.TEST_CORE)
    testImplementation(Dependencies.JUNIT)
    testImplementation(Dependencies.COROUTINES_TEST)
    testImplementation(Dependencies.KOTLIN_JUNIT)
    testImplementation(Dependencies.JUNIT_KTX)
    testImplementation(Dependencies.ESPRESSO)
    testImplementation(Dependencies.MOCKK)
    androidTestImplementation(testUtil)
    androidTestImplementation(Dependencies.COMPOSE_UI_TEST_JUNIT)
    debugImplementation(Dependencies.COMPOSE_UI_TEST_MANIFEST)
    androidTestImplementation(Dependencies.KOTLIN_JUNIT)
    androidTestImplementation(Dependencies.JUNIT_KTX)
    androidTestImplementation(Dependencies.ESPRESSO)
    androidTestImplementation(Dependencies.MOCKK_ANDROID)
    androidTestImplementation(Dependencies.MOCKK_AGENT)
    androidTestImplementation(Dependencies.COROUTINES_TEST)
}
