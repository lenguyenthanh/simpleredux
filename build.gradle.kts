buildscript {

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:0.16.3")
    }
}


plugins {
    kotlin("jvm") version "1.5.31"
    `maven-publish`
}

apply(plugin = "kotlinx-atomicfu")


group = "com.lenguyenthanh.redux"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots")}
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    testImplementation ("io.kotest:kotest-runner-junit5:5.0.0.M3")
    testImplementation ("io.kotest:kotest-assertions-core:5.0.0.M3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("binary") {
            from(components["java"])
        }
    }
}
