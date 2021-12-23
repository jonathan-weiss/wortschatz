import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    idea
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    id("org.openapi.generator") version "5.3.0"

}

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom (org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-beans")
    implementation("org.springframework:spring-context")

    implementation("org.springframework:spring-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")



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
