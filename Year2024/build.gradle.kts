plugins {
    kotlin("jvm")
}

group = "it.senape.aoc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":SwissKnife"))
    implementation("com.google.code.gson:gson:2.11.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}