import java.text.SimpleDateFormat
import java.util.Date

plugins {
    androidApplication
    kotlinAndroid
    kotlinKapt
    hilt
    googleServices
    firebaseCrashlytics
}

buildscript {
    apply(from = "../buildSrc/ktlint.gradle.kts")
}

android {
    namespace = "jp.android.app"
    compileSdk = Android.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = Android.APPLICATION_ID
        minSdk = Android.MIN_SDK_VERSION
        targetSdk = Android.TARGET_SDK_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("dev-release") {
            storeFile = file("../keystore/123456.jks")
            storePassword = "123456"
            keyAlias = "key0"
            keyPassword = "123456"
        }
    }
    applicationVariants.all {
        outputs.map {
            it as com.android.build.gradle.internal.api.BaseVariantOutputImpl
        }.forEachIndexed { i, it ->
            val flavor = productFlavors[i]
            it.outputFileName = "Android-${flavor.name}-${flavor.versionName}-${SimpleDateFormat("ddMMyyyy").format(Date())}.apk"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("dev-release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    flavorDimensions.add("default")
    productFlavors {
        create("develop") {
            applicationIdSuffix = ".dev"
            versionCode = Android.VERSION_CODE_DEV
            versionName = Android.VERSION_NAME_DEV
            manifestPlaceholders["applicationName"] = "Compose Dev"
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://thinhnd-nal.github.io\""
            )
        }
        create("staging") {
            applicationIdSuffix = ".stg"
            versionCode = Android.VERSION_CODE_STAGING
            versionName = Android.VERSION_NAME_STAGING
            manifestPlaceholders["applicationName"] = "@string/app_name"
        }
        create("qa") {
            applicationIdSuffix = ".qa"
            versionCode = Android.VERSION_CODE
            versionName = Android.VERSION_NAME
            manifestPlaceholders["applicationName"] = "@string/app_name"
        }
        create("production") {
            versionCode = Android.VERSION_CODE
            versionName = Android.VERSION_NAME
            manifestPlaceholders["applicationName"] = "@string/app_name"
        }
    }
}

configurations {
    debugImplementation {
        exclude(group = "junit", module = "junit")
    }
}

dependencies {
    implementation(
        fileTree(
            mapOf(
                "dir" to "libs",
                "include" to listOf("*.jar")
            )
        )
    )
    implementation(domain)
    implementation(data)
    implementation(features)
    implementation(shared)
    implementation(resources)

    implementation(Dependencies.DAGGER_HILT_ANDROID)
    implementation(Dependencies.DAGGER_HILT_CORE)
    kapt(Dependencies.DAGGER_HILT_COMPILER)

    implementation(platform(Dependencies.FIREBASE_BOM))
    implementation(Dependencies.FIREBASE_ANALYTICS)
    implementation(Dependencies.FIREBASE_CRASHLYTICS)
}
