package com.cukhoaimon.database.entity

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.time.Instant
import java.util.UUID

@Introspected
@Serdeable
data class UserEntity(
  val id: UUID = UUID.randomUUID(),
  val email: String,
  val password: String,
  val createdAt: Instant = Instant.now(),
)
