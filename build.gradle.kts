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
}

apply(plugin = "kotlinx-atomicfu")


group = "com.lenguyenthanh.redux"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    // RxJava
    api("io.reactivex.rxjava2:rxjava:2.2.8")
}
