plugins {
    kotlin
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(Dependencies.KTLINT_CORE)
}
