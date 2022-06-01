plugins {
    kotlin("jvm") version "1.6.10"
    java
    `maven-publish`
}
version = "1.0"
group = "plumy.path"
sourceSets {
    main {
        java.srcDir("src")
    }
    test {
        java.srcDir("test")
    }
}
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform {
    }
    testLogging {
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStandardStreams = true
    }
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.liplum"
            artifactId = "library"
            version = "1.0"

            from(components["java"])
        }
    }
}