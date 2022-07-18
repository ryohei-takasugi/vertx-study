import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  java
  application
  eclipse
  maven
  checkstyle
  id("com.github.johnrengelman.shadow") version "5.2.0"
  id("com.diffplug.spotless") version "6.8.0"
}

repositories {
  mavenCentral()
  jcenter()
  mavenLocal()
}

val vertxVersion = "3.9.4"
val junitVersion = "5.3.2"
val mysqlVersion = "4.3.1"
val mainVerticleName = "jp.vertx.starter.MainVerticle"
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

val gsonVersion = "2.9.0"

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))

  implementation("io.vertx:vertx-web:$vertxVersion")
  implementation("io.vertx:vertx-core:$vertxVersion")
  implementation("io.vertx:vertx-config:$vertxVersion")
  implementation("io.vertx:vertx-mysql-client:$mysqlVersion")

  implementation("com.google.code.gson:gson:$gsonVersion")

  implementation("org.slf4j:slf4j-api:$slf4jVersion")
  implementation("ch.qos.logback:logback-core:$logbackVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")

  testImplementation("io.vertx:vertx-junit5")
  testImplementation("io.vertx:vertx-web-client")
  //testImplementation("org.sahagin:sahagin-groovy:0.10.1")
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

checkstyle {
    toolVersion = "10.3"
    configFile = file("$projectDir/config/checkstyle/google_checks.xml")
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
    // Ctrl + C でプロセスが停止しないので
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
