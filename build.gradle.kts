group = "ch.jonathanweiss"
version = "0.0.1-SNAPSHOT"

// define what to do for github workflows
allprojects {
    group = "Github"
    tasks.register("github-ci") {}
}
