plugins {
    androidLib
    kotlinAndroid
    kotlinKapt
}

buildscript {
    apply(from = "../buildSrc/ktlint.gradle.kts")
}

android {
    namespace = "jp.android.app.data"
}

dependencies {
    implementation(domain)
    implementation(shared)

    implementation(Dependencies.COROUTINES_ANDROID)
    implementation(Dependencies.DAGGER_HILT_ANDROID)
    implementation(Dependencies.DAGGER_HILT_CORE)
    kapt(Dependencies.DAGGER_HILT_COMPILER)

    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER_MOSHI)

    implementation(Dependencies.MOSHI)
    kapt(Dependencies.MOSHI_CODEGEN)

    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_KTX)
    kapt(Dependencies.ROOM_COMPILER)

    implementation(Dependencies.SECURITY_CRYPTO)

    //unit test
    testImplementation(Dependencies.TEST_CORE)
    testImplementation(Dependencies.JUNIT)
    testImplementation(Dependencies.COROUTINES_TEST)
    testImplementation(Dependencies.KOTLIN_JUNIT)
    androidTestImplementation(Dependencies.JUNIT_KTX)
    testImplementation(Dependencies.ARCH_CORE_TEST)
    testImplementation(Dependencies.TEST_RULE)
    testImplementation(Dependencies.MOCKK)

    testImplementation(Dependencies.GOOGLE_TRUTH)
    androidTestImplementation(Dependencies.GOOGLE_TRUTH)

}
