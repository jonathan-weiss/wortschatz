import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension

plugins {
    idea
    id("org.openapi.generator") version "5.3.0"
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    id("com.palantir.docker") version "0.31.0"
}

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
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
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val idea = the<org.gradle.plugins.ide.idea.model.IdeaModel>()
val sourceSets = the<SourceSetContainer>()


val mainSourceSet = sourceSets.named("main");
val testSourceSet = sourceSets.named("test");

mainSourceSet.configure {
    java {
        srcDir("src/main/kotlin-generated")
    }
}


idea.module {
    generatedSourceDirs = generatedSourceDirs + projectDir.resolve("src/main/kotlin-generated")
}

configure<OpenApiGeneratorGenerateExtension> {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/wortschatz-api-spec/wortschatz-api-v1.0.open-api.yaml")
    outputDir.set("$buildDir/generated")
    apiPackage.set("ch.jonathanweiss.wortschatz.rest.api")
    modelPackage.set("ch.jonathanweiss.wortschatz.rest.model")
    // see https://openapi-generator.tech/docs/generators/kotlin-spring/
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "useBeanValidation" to "false",
            "serviceInterface" to "true",
            "serviceImplementation" to "true",
            "interfaceOnly" to "false",


        )
    )
}

tasks.register<Copy>("copyOpenApiGeneratedFiles") {
    group = "OpenAPI Tools"
    description = "Copy all relevant generated files from task openApiGenerate into the src directory."
    dependsOn("openApiGenerate")

    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    from(project.buildDir.resolve("generated/src/main/kotlin")) {
        include("ch/jonathanweiss/wortschatz/rest/api/*Api.kt")
        include("ch/jonathanweiss/wortschatz/rest/api/*ApiService.kt")
        include("ch/jonathanweiss/wortschatz/rest/model/*")
    }

    into(project.projectDir.resolve("src/main/kotlin-generated"))
}

tasks.register<Delete>("cleanGeneratedFiles") {
    description = "Copy all relevant generated files from task openApiGenerate into the src directory."

    delete(project.projectDir.resolve("src/main/kotlin-generated"))
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

tasks.getByName<Jar>("jar") {
    // do not produce the wortschatz-backend-xxx-plain.jar, only the executable jar from task bootJar
    enabled = false
}

val bootJarTask = tasks.named<Jar>("bootJar")

tasks.named("dockerPrepare") {
    inputs.file(bootJarTask.get().archiveFile)
}

idea.module {
    excludeDirs = excludeDirs + project.file("dist")
}


tasks.named("compileKotlin") {
    dependsOn ("copyOpenApiGeneratedFiles")
}

tasks.named("clean") {
    dependsOn ("cleanGeneratedFiles")
}


// define what to do for github workflows
tasks.named("github-ci") {
    dependsOn ("build")
}
