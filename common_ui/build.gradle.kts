plugins {
    androidLib
    kotlinAndroid
    kotlinKapt
}

buildscript {
    apply(from = "../buildSrc/ktlint.gradle.kts")
}

android {
    namespace = "jp.android.app.common_ui"

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

    implementation(Dependencies.COMPOSE_UI)
    implementation(Dependencies.COMPOSE_UI_TOOLING_PREVIEW)
    debugImplementation(Dependencies.COMPOSE_UI_TOOLING)

    implementation(Dependencies.COMPOSE_MATERIAL)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.HILT_NAVIGATION_COMPOSE)
    implementation(Dependencies.NAVIGATION_ANIMATION)
    implementation(Dependencies.WINDOW)
    implementation(Dependencies.PLACE_HOLDER)

    implementation(Dependencies.COIL)
}