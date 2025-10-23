plugins {
    kotlin("jvm") version "2.2.21"
}

group = "org.jesperancinha.smtd"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}