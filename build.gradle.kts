import com.ncorti.ktfmt.gradle.tasks.KtfmtCheckTask
import com.ncorti.ktfmt.gradle.tasks.KtfmtFormatTask

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ktfmt)
}

group = "com.runicrealms.menus"

version = "0.1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

kotlin { compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") } }

java { toolchain.languageVersion.set(JavaLanguageVersion.of(21)) }

dependencies {
    // Kotlin
    implementation(rootProject.libs.kotlin.stdlib)
    implementation(rootProject.libs.kotlin.test)

    // Paper
    compileOnly(rootProject.libs.paper.api)
}

ktfmt {
    kotlinLangStyle()
    srcSetPathExclusionPattern = Regex(".*generated.*")
}

tasks.withType<KtfmtFormatTask>().configureEach {
    source = project.fileTree(rootDir)
    include("**/*.kt")
    exclude("**/generated/**")
}

tasks.withType<KtfmtCheckTask>().configureEach {
    source = project.fileTree(rootDir)
    include("**/*.kt")
    exclude("**/generated/**")
}
