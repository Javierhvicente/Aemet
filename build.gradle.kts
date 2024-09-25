plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.michael-bull.kotlin-result:kotlin-result:2.0.0")

    implementation("org.lighthousegames:logging:1.3.0")
    implementation("ch.qos.logback:logback-classic:1.4.14")

    // Serialización JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}