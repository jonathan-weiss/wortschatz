
plugins {
    id("org.openapi.generator") version "5.3.0" apply false
    id("com.palantir.docker") version "0.31.0" apply false
}


// define what to do for github workflows
allprojects {
    group = "ch.jonathanweiss"
    version = "0.0.1-SNAPSHOT-2"


    tasks.register("github-ci") {
        group = "Github"

    }
}
