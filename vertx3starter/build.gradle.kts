import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  java
  application
  eclipse
  id("com.github.johnrengelman.shadow") version "7.1.0"
  id("com.diffplug.spotless") version "6.8.0"
}

repositories {
  mavenCentral()
  jcenter()
  mavenLocal()
}

val modowner: String by project
val groupId = "${modowner}"
// vert.x
val version: String by project
val vertxVersion: String by project
val junitVersion: String by project
// json
val gsonVersion: String by project
// log
val slf4jVersion: String by project
val logbackVersion: String by project

val mainVerticleName = "jp.vertx.starter.MainVerticle"
val watchForChange = "src/**/*.java"
val doOnChange = "${projectDir}/gradlew classes"

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))

  implementation("io.vertx:vertx-web:$vertxVersion")
  implementation("io.vertx:vertx-core:$vertxVersion")
  implementation("io.vertx:vertx-config:$vertxVersion")

  implementation("com.google.code.gson:gson:$gsonVersion")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4")

  implementation("org.slf4j:slf4j-api:$slf4jVersion")
  implementation("ch.qos.logback:logback-core:$logbackVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")

  testImplementation("io.vertx:vertx-junit5")
  testImplementation("io.vertx:vertx-web-client")
  testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
}

application {
  mainClassName = "io.vertx.core.Launcher"
}
eclipse {
  project {
    name = rootProject.name
  }
}

spotless {
  format("misc") {
    target("*.java")
    indentWithSpaces(2)
    endWithNewline()
  }
  java {
    target("src/*/java/**/*.java")
    // Use the default importOrder configuration
    importOrder()
    // optional: you can specify import groups directly
    // note: you can use an empty string for all the imports you didn't specify explicitly, and '\\#` prefix for static imports
    // importOrder("java", "io", "vert.x", "starter")
    // optional: instead of specifying import groups directly you can specify a config file
    // export config file: https://github.com/diffplug/spotless/blob/main/ECLIPSE_SCREENSHOTS.md#creating-spotlessimportorder
    // importOrderFile('eclipse-import-order.txt') // import order file as exported from eclipse

    removeUnusedImports()

    googleJavaFormat() // has its own section below
    //eclipse()          // has its own section below
    //prettier()         // has its own section below
    //clangFormat()      // has its own section below

    // licenseHeader("/* (C) $YEAR */") // or licenseHeaderFile
  }
  json {
    target("src/**/*.json")                      // you have to set the target manually
    // simple().indentWithSpaces(2)                 // has its own section below
    // prettier().config(mapOf("parser" to "json")) // see Prettier section below
    // eclipseWtp("json")                           // see Eclipse web tools platform section
    gson().indentWithSpaces(2)                                       // has its own section below
  }
}

tasks {
  test {
    useJUnitPlatform()
  }

  getByName<JavaExec>("run") {
    args = listOf("run", mainVerticleName, "--redeploy=${watchForChange}", "--launcher-class=${application.mainClassName}", "--on-redeploy=${doOnChange}")
  }

  withType<ShadowJar> {
    classifier = "fat"
    manifest {
      attributes["Main-Verticle"] = mainVerticleName
    }
    mergeServiceFiles()
  }
}
