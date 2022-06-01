plugins {
    kotlin("jvm") version "1.6.10"
    java
}
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
tasks {
    val sourcesJar by creating(Jar::class) {
        dependsOn(JavaPlugin.CLASSES_TASK_NAME)
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }
   /* val javadocJar by creating(Jar::class) {
        dependsOn(JavaPlugin.JAVADOC_TASK_NAME)
        archiveClassifier.set("javadoc")
        from(javadoc)
    }*/

    artifacts {
        add("archives", sourcesJar)
        //add("archives", javadocJar)
    }

    withType<Test>().configureEach {
        useJUnitPlatform {
        }
        testLogging {
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            showStandardStreams = true
        }
    }
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}