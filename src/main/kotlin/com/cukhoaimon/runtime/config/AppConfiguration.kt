package com.cukhoaimon.runtime.config

import io.micronaut.context.annotation.ConfigurationInject
import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("app")
data class AppConfiguration @ConfigurationInject constructor(
  val database: DatabaseConfig,
) {
  @ConfigurationProperties("database")
  data class DatabaseConfig @ConfigurationInject constructor(
    val url: String,
    val name: String,
    val username: String,
    val password: String,
    val port: Int
  )
}

