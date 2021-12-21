import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension
import com.github.gradle.node.npm.task.NpmTask

plugins {
  idea
  id("org.openapi.generator") version "5.3.0"
  id("com.github.node-gradle.node") version "3.1.1"
  id("com.palantir.docker") version "0.31.0"
}

repositories {
    mavenCentral()
}



configure<OpenApiGeneratorGenerateExtension> {
  generatorName.set("typescript-angular")
  inputSpec.set("$rootDir/wortschatz-api-spec/wortschatz-api-v1.0.open-api.yaml")
  outputDir.set("$buildDir/generated")
//  apiPackage.set("ch.jonathanweiss.wortschatz.api")
//  invokerPackage.set("ch.jonathanweiss.wortschatz.invoker")
//  modelPackage.set("ch.jonathanweiss.wortschatz.model")
  // see https://openapi-generator.tech/docs/generators/typescript-angular/
  configOptions.set(
    mapOf(
      "fileNaming" to "camelCase",
      "sortParamsByRequiredFlag" to "true",
      "supportsES6" to "false",
      "withInterfaces" to "true",
      "ngVersion" to "10.0",
      "useRxJS6" to "true",
      "removeEnumValuePrefix" to "false"
    )
  )

}

tasks.register<Copy>("copyOpenApiGeneratedFiles") {
  group = "OpenAPI Tools"
  description = "Copy all relevant generated files from task openApiGenerate into the src directory."
  dependsOn("openApiGenerate")

  duplicatesStrategy = DuplicatesStrategy.INCLUDE

  from(project.buildDir.resolve("generated/")) {
    include("*.ts")
    include("api/*")
    include("model/*")
  }

  into(project.projectDir.resolve("src/generated/openapi"))
}


node {
  // keep this version in sync with .npmrc
  version.set("16.13.1") // node version
  npmVersion.set("8.1.2") // npm version

  // If true, it will download node using above parameters.
  // If false, it will try to use globally installed node.
  download.set(true)

}

tasks.named<NpmTask>("npmInstall") {
  val workingDirectory = workingDir.map { it.asFile }.getOrElse(project.projectDir)
  inputs.file(workingDirectory.resolve("package.json"))
  inputs.file(workingDirectory.resolve("package-lock.json"))
  outputs.dir(workingDirectory.resolve("node_modules"))
}

tasks.register<Delete>("npmClean") {
  description = "Deletes all the generated files and compilations from the angular project"
  delete("coverage", "dist", "lib", "node_modules", "testreports")
  delete(project.projectDir.resolve("src/openapi"))
}

val buildAngularTask = tasks.register<NpmTask>("buildAngular") {
  description = "Build the angular productive version into 'dist' folder"
  dependsOn("copyOpenApiGeneratedFiles")
  dependsOn("npmInstall")


  // mark task as NOT up-to-date if there are changes in any of these files
  // Note: this does NOT define the files considered by 'npm run build',
  // but is really just for gradle's up-to-date logic
  inputs.files(project.files("src/*.ts", "src/*.json"))
  inputs.file(project.file("angular.json"))
  inputs.file(project.file("tsconfig.json"))
  inputs.file(project.file("package.json"))
  inputs.file(project.file("package-lock.json"))

  outputs.dir(projectDir.resolve("dist"))

  workingDir.set(projectDir)
  args.set(listOf("run", "build"))

}

tasks.register<NpmTask>("testAngular") {
  description = "Run the angular jasmine tests."
  dependsOn("buildAngular")

  workingDir.set(projectDir)
  args.set(listOf("run", "test"))

}

docker {

  val pathToDistDir = projectDir.resolve("dist")
  val dockerNameAndVersion = "${project.name}:${project.version}"

  // the name of the image to build including version
  name = dockerNameAndVersion

  // the Dockerfile to use for building
  setDockerfile(project.projectDir.resolve("docker/Dockerfile"))
  //arguments to inject into Dockerfile
  buildArgs(mapOf("DIST_DIRECTORY_NAME" to "wortschatz-app"))

  // files to attach to the docker build context (as flat file structure)
  files(pathToDistDir)

  // tags for pushing image
  tag("dockerHub", "docker.io/wortschatz/$dockerNameAndVersion")

  // options
  pull(false)
  noCache(false)
}

tasks.named("dockerPrepare") {
  inputs.files(buildAngularTask.get().outputs)
}


idea.module {
  excludeDirs = excludeDirs + project.file("build")
  excludeDirs = excludeDirs + project.file("dist")
  excludeDirs = excludeDirs + project.file(".angular")
}

// define what to do for github workflows
tasks.named("github-ci") {
  dependsOn ("testAngular")
}
