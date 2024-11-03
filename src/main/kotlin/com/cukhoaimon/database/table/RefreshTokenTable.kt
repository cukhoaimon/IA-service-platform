package com.cukhoaimon.database.table

import java.time.Instant
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.timestamp

object RefreshTokenTable : UUIDTable("refresh_tokens") {
  val userId = uuid("user_id")
  val token = text("token")
  val createdAt: Column<Instant> = timestamp("created_at").default(Instant.now())
}
