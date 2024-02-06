import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jmailen.kotlinter") version "4.2.0"
    `java-library`
    `maven-publish`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // ktor
    api(group = "io.ktor", name = "ktor-client", version = "2.3.8")

    // Jackson
    implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = "2.16.1")
    api(group = "io.ktor", name = "ktor-serialization-jackson", version = "2.3.8")
    api(group = "io.ktor", name = "ktor-client-content-negotiation", version = "2.3.8")

    // Use the Kotlin JDK 8 standard library.
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib-jdk8")

    // Use the Kotlin test library.
    testImplementation(group = "org.jetbrains.kotlin", name = "kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation(group = "org.jetbrains.kotlin", name = "kotlin-test-junit")
    testImplementation(group = "io.ktor", name = "ktor-client-jetty", version = "2.3.8")
}

val sourcesJar by tasks.registering(Jar::class) {
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}

publishing {
    publications {
        register<MavenPublication>("ClovaTTS-kt") {
            from(components["java"])
            artifact(sourcesJar)
        }
    }
}
