plugins {
  id("org.jetbrains.kotlin.jvm") version "1.9.25"
  id("org.jetbrains.kotlin.plugin.allopen") version "1.9.25"
  id("com.google.devtools.ksp") version "1.9.25-1.0.20"
  id("com.github.johnrengelman.shadow") version "8.1.1"
  id("io.micronaut.application") version "4.4.2"
  id("io.micronaut.test-resources") version "4.4.2"
  id("io.micronaut.aot") version "4.4.2"
}

version = "0.1"
group = "com.cukhoaimon"

repositories {
  mavenCentral()
}

val kotlinVersion = project.properties.get("kotlinVersion")
val exposedVersion: String by project
val jacksonVersion: String by project
val retrofitVersion: String by project
val arrowVersion: String by project
val okhttpVersion: String by project
val reactorVersion: String by project

dependencies {
  ksp("io.micronaut:micronaut-http-validation")
  ksp("io.micronaut.openapi:micronaut-openapi")
  ksp("io.micronaut.openapi:micronaut-openapi-adoc")
  ksp("io.micronaut.serde:micronaut-serde-processor")
  implementation("io.micronaut:micronaut-retry")
  implementation("io.micronaut.flyway:micronaut-flyway")
  implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
  implementation("io.micronaut.serde:micronaut-serde-bson")
  implementation("io.micronaut.serde:micronaut-serde-jackson")
  implementation("io.micronaut.serde:micronaut-serde-jsonp")
  implementation("io.micronaut.sql:micronaut-jdbc-hikari")
  implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
  compileOnly("io.micronaut:micronaut-http-client")
  compileOnly("io.micronaut.openapi:micronaut-openapi-annotations")
  runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
  runtimeOnly("org.flywaydb:flyway-database-postgresql")
  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("org.yaml:snakeyaml")
  testImplementation("io.micronaut:micronaut-http-client")
  implementation("org.apache.commons:commons-lang3:3.9")

  ksp("io.micronaut.security:micronaut-security-annotations")
  implementation("io.micronaut.security:micronaut-security")
  implementation("io.micronaut.security:micronaut-security-jwt")

  runtimeOnly("ch.qos.logback:logback-classic")
  implementation("org.slf4j:slf4j-api:2.0.12")
  testImplementation("ch.qos.logback:logback-classic:1.5.3")
  implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")

  implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-money:$exposedVersion")

//  implementation("com.fasterxml.jackson.core:jackson-annotations:${jacksonVersion}")
//  implementation("com.fasterxml.jackson.core:jackson-core:${jacksonVersion}")
//  implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
//  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonVersion}")
//  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${jacksonVersion}")
//  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${jacksonVersion}")
//  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-avro:${jacksonVersion}")
//
//  implementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
//  implementation("com.squareup.retrofit2:converter-jackson:${retrofitVersion}")

  implementation("io.arrow-kt:arrow-core:$arrowVersion")
  implementation("io.arrow-kt:arrow-fx-coroutines:$arrowVersion")

  implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
  implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")
  implementation("com.squareup.okhttp3:okhttp-urlconnection:$okhttpVersion")
  implementation("com.squareup.okhttp3:mockwebserver:$okhttpVersion")

  implementation("io.projectreactor:reactor-core:$reactorVersion")

}


application {
  mainClass = "com.cukhoaimon.Main"
}
java {
  sourceCompatibility = JavaVersion.toVersion("17")
}


graalvmNative.toolchainDetection = false

micronaut {
  runtime("netty")
  testRuntime("junit5")
  processing {
    incremental(true)
    annotations("com.cukhoaimon.*")
  }
  testResources {
    additionalModules.add("jdbc-postgresql")
  }
  aot {
    // Please review carefully the optimizations enabled below
    // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
    optimizeServiceLoading = false
    convertYamlToJava = false
    precomputeOperations = true
    cacheEnvironment = true
    optimizeClassLoading = true
    deduceEnvironment = true
    optimizeNetty = true
    replaceLogbackXml = true
  }
}



