package com.cukhoaimon.database.repository

import com.cukhoaimon.database.entity.UserEntity
import com.cukhoaimon.database.table.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

interface UserRepository {
  fun findByEmail(email: String): UserEntity?
  fun insert(user: UserEntity): UserEntity
  fun listAll(): List<UserEntity>
}

class DefaultUserRepository(
  private val database: Database
) : UserRepository {
  override fun findByEmail(email: String): UserEntity? {
    return transaction(database) {
      User.selectAll()
        .where { User.email eq email }
        .firstOrNull()
    }?.asUserEntity()
  }

  override fun insert(user: UserEntity): UserEntity {
    return transaction(database) {
      User.insert {
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
      User.selectAll()
        .map { it.asUserEntity() }
    }
  }
}


fun ResultRow.asUserEntity(): UserEntity {
  return UserEntity(
    id = this[User.id].value,
    password = this[User.password],
    email = this[User.email],
    createdAt = this[User.createdAt],
  )
}
