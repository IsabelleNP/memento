import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.4.10"
    kotlin("plugin.jpa") version "1.4.10"
    id("io.gitlab.arturbosch.detekt") version "1.14.2"
    application
    jacoco
}

group = "com.memento"

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
    jcenter {
        content {
            includeGroup("org.jetbrains.kotlinx")
        }
    }
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    // Hibernate
    implementation("org.hibernate.validator:hibernate-validator")

    // PostgreSQL
    runtimeOnly("org.postgresql:postgresql")

    // Kotest
    testImplementation("io.kotest:kotest-runner-junit5:4.3.0")
    testImplementation("io.kotest:kotest-extensions-spring:4.3.0")
    
    // Detekt
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.13.1")
    
    // Mockk
    testImplementation("io.mockk:mockk:1.10.0")

    // Jackson
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.4.0")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "com.memento.ApplicationKt"
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

detekt {
    autoCorrect = true
}

jacoco {
    toolVersion = "0.8.6"
}

tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        xml.destination = file("${buildDir}/reports/jacoco/report.xml")
    }
}

tasks.jacocoTestReport { dependsOn(tasks.test) }