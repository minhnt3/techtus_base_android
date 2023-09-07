plugins {
    kotlin
    kotlinKapt
}

buildscript {
    apply(from = "../buildSrc/ktlint.gradle.kts")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(Dependencies.COROUTINES_ANDROID)
    implementation(Dependencies.DAGGER_HILT_CORE)
    kapt(Dependencies.DAGGER_HILT_COMPILER)

    testImplementation(Dependencies.TEST_CORE)
    testImplementation(Dependencies.JUNIT)
    testImplementation(Dependencies.COROUTINES_TEST)
    testImplementation(Dependencies.KOTLIN_JUNIT)
    testImplementation(Dependencies.TEST_RULE)
    testImplementation(Dependencies.MOCKK)
}
