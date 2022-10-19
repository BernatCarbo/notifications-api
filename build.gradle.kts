import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    idea
    application
    kotlin("plugin.serialization") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val koinVersion = "3.2.2"
val ktorVersion = "2.1.2"
val exposedVersion = "0.39.2"
val hikariVersion = "5.0.1"
val h2DatabaseVersion = "2.1.214"
val sqliteVersion = "3.39.3.0"
val flywayVersion = "9.4.0"
val log4j2Version = "2.17.0"
val kotlinLoggingVersion = "2.1.21"

dependencies {
    // Dependency Injection
    implementation("io.insert-koin:koin-core:$koinVersion")
    // HTP API
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-resources:$ktorVersion")
    implementation("io.ktor:ktor-server-default-headers:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    // Persistence
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("com.zaxxer:HikariCP:$hikariVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    runtimeOnly("com.h2database:h2:$h2DatabaseVersion")
    runtimeOnly("org.xerial:sqlite-jdbc:$sqliteVersion")
    // Logging
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4j2Version")
    implementation("org.apache.logging.log4j:log4j-core:$log4j2Version")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4j2Version")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("notifications.NotificationsApi")
}