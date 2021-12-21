
// define what to do for github workflows
allprojects {
    group = "ch.jonathanweiss"
    version = "0.0.1-SNAPSHOT-2"


    tasks.register("github-ci") {
        group = "Github"

    }
}
