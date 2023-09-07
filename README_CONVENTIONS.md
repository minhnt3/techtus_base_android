# Coding conventions

### 1. Coding Rules with Ktlint

* **Use lib** https://github.com/pinterest/ktlint
* **Add buildScript** on build.gradle.kts file when create new module in project
```kotlin
buildscript {
    apply(from = "../buildSrc/ktlint.gradle.kts")
}
```

* **Use Terminal** to Check Kotlin code style and convention:
```kotlin
./gradlew ktlintCheck
```

* **To export result** to specified ktlint-reports.html file, open ktlint.gradle.kts and change args to:
```kotlin
args = listOf("src/main/**/*.kt", "--reporter=html,output=${project.rootDir}/ktlint/ktlint-reports.html")
}
```

### 2. Naming Rules
[Document](https://kotlinlang.org/docs/coding-conventions.html#naming-rules)

### 3. Formatting
[Document](https://kotlinlang.org/docs/coding-conventions.html#formatting)

### 4. Idiomatic use of language features
[Document](https://kotlinlang.org/docs/coding-conventions.html#idiomatic-use-of-language-features)