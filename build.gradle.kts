// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlinVersion apply false
    id("com.google.dagger.hilt.android") version Versions.hiltVersion apply false
    id ("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlinVersion apply false
    id ("org.jetbrains.kotlin.jvm") version Versions.kotlinVersion apply false
}