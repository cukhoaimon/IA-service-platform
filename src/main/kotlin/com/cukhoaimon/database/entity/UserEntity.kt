package com.cukhoaimon.database.entity

import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.util.UUID

@Introspected
data class UserEntity(
  val id: UUID = UUID.randomUUID(),
  val email: String,
  val password: String,
  val createdAt: Instant = Instant.now(),
)
