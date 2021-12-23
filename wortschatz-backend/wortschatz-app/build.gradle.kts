import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    idea
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.palantir.docker")

}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom (org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation("org.springframework.security:spring-security-test")


    implementation("org.axonframework:axon-spring-boot-starter:4.5.1")

    implementation(project(":wortschatz-backend:wortschatz-rest"))
    implementation(project(":wortschatz-backend:wortschatz-business"))
}

java.sourceCompatibility = JavaVersion.VERSION_11


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName<Jar>("jar") {
    // do not produce the wortschatz-backend-xxx-plain.jar, only the executable jar from task bootJar
    enabled = false
}

docker {
    val pathToJar = project.buildDir.resolve("libs/${project.name}-${project.version}.jar")
    val dockerNameAndVersion = "${project.name}:${project.version}"

    // the name of the image to build including version
    name = dockerNameAndVersion

    // the Dockerfile to use for building
    setDockerfile(project.projectDir.resolve("docker/Dockerfile"))
    //arguments to inject into Dockerfile
    buildArgs(mapOf("JAR_FILE" to pathToJar.name))
    // files to attach to the docker build context (as flat file structure)
    files(pathToJar)

    // tags for pushing image
    tag("dockerHub", "docker.io/wortschatz/$dockerNameAndVersion")

    // options
    pull(false)
    noCache(false)
}


tasks.named("dockerPrepare") {
    val bootJarTask = tasks.named<Jar>("bootJar")

    inputs.file(bootJarTask.get().archiveFile)
}


// define what to do for github workflows
tasks.named("github-ci") {
    dependsOn ("build")
}
