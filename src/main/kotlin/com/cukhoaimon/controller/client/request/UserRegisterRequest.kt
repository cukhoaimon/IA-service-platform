package com.cukhoaimon.client.request

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import org.jetbrains.annotations.NotNull

@Serdeable.Deserializable
@Introspected
data class UserRegisterRequest(
  @field:NotNull
  val username: String,
  @field:NotNull
  val password: String,
)
