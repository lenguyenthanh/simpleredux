buildscript {

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:0.16.1")
    }
}


plugins {
    kotlin("jvm") version "1.5.20"
    `maven-publish`
}

apply(plugin = "kotlinx-atomicfu")


group = "com.lenguyenthanh.redux"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    // RxJava
    api("io.reactivex.rxjava2:rxjava:2.2.8")
}

publishing {
    publications {
        create<MavenPublication>("binary") {
            from(components["java"])
        }
    }
}
