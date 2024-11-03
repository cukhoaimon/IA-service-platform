package com.cukhoaimon.database.entity

import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.util.UUID

@Introspected
data class RefreshTokenEntity(
  val userId: UUID,
  val token: String,
  val createdAt: Instant = Instant.now(),
)
