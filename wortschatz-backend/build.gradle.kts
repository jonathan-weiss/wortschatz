
plugins {
    kotlin("jvm") version "1.6.0" apply false
    kotlin("plugin.spring") version "1.6.0" apply false
    id("org.springframework.boot") version "2.6.1" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply true
    idea
}


// define what to do for github workflows
tasks.named("github-ci") {
    dependsOn ("build")
}
