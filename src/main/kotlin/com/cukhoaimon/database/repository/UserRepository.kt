package com.cukhoaimon.database.repository

import com.cukhoaimon.database.entity.UserEntity
import com.cukhoaimon.database.table.UserTable
import java.util.UUID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

interface UserRepository {
  fun findById(id: UUID): UserEntity?
  fun findByEmail(email: String): UserEntity?
  fun insert(user: UserEntity): UserEntity
  fun listAll(): List<UserEntity>
}

class DefaultUserRepository(
  private val database: Database
) : UserRepository {
  override fun findById(id: UUID): UserEntity? {
    return transaction(database) {
      UserTable.selectAll()
        .where { UserTable.id eq id }
        .firstOrNull()
    }?.asUserEntity()
  }

  override fun findByEmail(email: String): UserEntity? {
    return transaction(database) {
      UserTable.selectAll()
        .where { UserTable.email eq email }
        .firstOrNull()
    }?.asUserEntity()
  }

  override fun insert(user: UserEntity): UserEntity {
    return transaction(database) {
      UserTable.insert {
        it[id] = user.id
        it[email] = user.email
        it[password] = user.password
        it[createdAt] = user.createdAt
      }

      user
    }
  }

  override fun listAll(): List<UserEntity> {
    return transaction(database) {
      UserTable.selectAll()
        .map { it.asUserEntity() }
    }
  }
}


fun ResultRow.asUserEntity(): UserEntity {
  return UserEntity(
    id = this[UserTable.id].value,
    password = this[UserTable.password],
    email = this[UserTable.email],
    createdAt = this[UserTable.createdAt],
  )
}
