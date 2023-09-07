repositories {
    mavenCentral()
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))
val ktlint by configurations.creating

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)
    description = "Check Kotlin code style."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("src/main/**/*.kt")
//    args = listOf("src/main/**/*.kt", "--reporter=html,output=${project.rootDir}/ktlint/ktlint-reports.html")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)
    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "src/main/**/*.kt")
}

dependencies {
    ktlint(Dependencies.KTLINT)
    ktlint(project(":ktlint-rules"))
}