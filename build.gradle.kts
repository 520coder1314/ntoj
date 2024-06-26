plugins {
    kotlin("jvm") apply false
    java
    idea
}

allprojects {
    group = "com.github.ntoj.app"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

subprojects {
    task("generateVersionResource") {
        doLast {
            val version = project.version.toString()
            file("build/generated-resources").mkdirs()
            val propertiesFile = file("build/generated-resources/version.properties")
            propertiesFile.writeText("version=$version")
        }
    }

    afterEvaluate {
        sourceSets {
            main {
                resources {
                    srcDir("build/generated-resources")
                }
            }
        }
    }

    tasks {
        withType<JavaCompile> {
            dependsOn("generateVersionResource")
        }
    }
}

idea {
    module {
        excludeDirs = setOf(file("data"), file("build"), file("web/dist"))
    }
}
