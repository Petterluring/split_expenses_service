plugins {
    alias(libs.plugins.kotlin.jvm)

    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"

    kotlin("plugin.spring") version "2.3.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}