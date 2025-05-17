import com.ncorti.ktfmt.gradle.tasks.KtfmtCheckTask
import com.ncorti.ktfmt.gradle.tasks.KtfmtFormatTask

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ktfmt)
    `maven-publish`
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

val archiveName = "menus"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = archiveName
            version = project.version.toString()
        }
    }
    repositories {
        maven {
            name = "nexus"
            url = uri("https://nexus.runicrealms.com/repository/maven-releases/")
            credentials {
                username = System.getenv("NEXUS_USERNAME")
                password = System.getenv("NEXUS_PASSWORD")
            }
        }
    }
}