import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  java
  application
  id("com.github.johnrengelman.shadow") version "5.2.0"
}

repositories {
  mavenCentral()
}

val vertxVersion = "3.9.4"
val junitVersion = "5.3.2"
val mainVerticleName = "io.vertx.starter.MainVerticle"
val watchForChange = "src/**/*.java"
val doOnChange = "${projectDir}/gradlew classes"

// log
val slf4jVersion = "1.7.30"
val logbackVersion = "1.2.3"
// val modelVersion = ""
val groupId = "io.vertx.starter"
val artifactId = "starter"
val packaging = "jar"
val version = "1.0.0"
val name = "MainVerticle"

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))

  implementation("io.vertx:vertx-core")

  implementation("org.slf4j:slf4j-api:$slf4jVersion")
  implementation("ch.qos.logback:logback-core:$logbackVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")

  testImplementation("io.vertx:vertx-junit5")
  testImplementation("io.vertx:vertx-web-client")
  testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
}

application {
  mainClassName = "io.vertx.core.Launcher"
}

tasks {
  test {
    useJUnitPlatform()
  }

  getByName<JavaExec>("run") {
    // args = listOf("run", mainVerticleName, "--redeploy=${watchForChange}", "--launcher-class=${application.mainClassName}", "--on-redeploy=${doOnChange}")
    args = listOf("run", mainVerticleName, "--launcher-class=${application.mainClassName}")
  }

  withType<ShadowJar> {
    classifier = "fat"
    manifest {
      attributes["Main-Verticle"] = mainVerticleName
    }
    mergeServiceFiles()
  }
}
