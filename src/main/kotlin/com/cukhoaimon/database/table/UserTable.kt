package com.cukhoaimon.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.timestamp

object User : UUIDTable("user") {
  val email = text("email").uniqueIndex()
  val password = text("password")
  val createdAt = timestamp("created_at")
}
