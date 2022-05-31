buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://www.jitpack.io")
        }
    }
}
allprojects {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://www.jitpack.io")
        }
    }
}