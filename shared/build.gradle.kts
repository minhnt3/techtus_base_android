plugins {
    androidLib
    kotlinAndroid
}

buildscript {
    apply(from = "../buildSrc/ktlint.gradle.kts")
}

android {
    namespace = "jp.android.app.shared"
}

dependencies {
    implementation(Dependencies.COROUTINES_CORE)
}
