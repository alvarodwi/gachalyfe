import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.spring") version "2.0.20"
    kotlin("plugin.jpa") version "2.0.20"
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.jmailen.kotlinter") version "4.4.1"
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("com.github.ben-manes.versions") version "0.51.0"
}

group = "me.gachalyfe"
version = "0.1.0"


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // spring web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // doc
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.6.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // data
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate.orm:hibernate-community-dialects")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.xerial:sqlite-jdbc")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kotlinter {
    ignoreFailures = false
    reporters = arrayOf("checkstyle", "plain")
}

tasks.withType<Jar>() {
    enabled = true
    archiveClassifier = ""
    archiveFileName.set("${project.name}.jar")
}

// ben-manes versions checking
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
        version.uppercase().contains(it)
    }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    // reject all non stable versions
    rejectVersionIf {
        isNonStable(candidate.version)
    }
    gradleReleaseChannel = "current"

    // optional parameters
    checkForGradleUpdate = true
    outputFormatter = "json"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}
