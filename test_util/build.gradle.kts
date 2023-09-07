plugins {
    androidLib
    kotlinAndroid
    kotlinKapt
}

buildscript {
    apply(from = "../buildSrc/ktlint.gradle.kts")
}

android {
    namespace = "jp.android.app.test_util"
}

dependencies {
    implementation(Dependencies.COMPOSE_UI_TEST_JUNIT)
    debugImplementation(Dependencies.COMPOSE_UI_TEST_MANIFEST)
    implementation(Dependencies.KOTLIN_JUNIT)
    implementation(Dependencies.JUNIT_KTX)
    implementation(Dependencies.ESPRESSO)
    implementation(Dependencies.COROUTINES_TEST)
}
