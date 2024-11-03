package com.cukhoaimon.database.repository

import com.cukhoaimon.database.entity.RefreshTokenEntity
import com.cukhoaimon.database.table.RefreshTokenTable
import java.util.UUID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

interface RefreshTokenRepository {
  fun insert(refreshToken: RefreshTokenEntity): RefreshTokenEntity
  fun byUserId(userId: UUID): RefreshTokenEntity?
  fun byToken(token: String): RefreshTokenEntity?
  fun deleteByUserId(userId: UUID): Boolean
}

class DefaultRefreshTokenRepository(
  val database: Database,
) : RefreshTokenRepository {
  override fun insert(refreshToken: RefreshTokenEntity): RefreshTokenEntity {
    return transaction(database) {
      RefreshTokenTable.insert {
        it[userId] = refreshToken.userId
        it[token] = refreshToken.token
        it[createdAt] = refreshToken.createdAt
      }

      refreshToken
    }
  }

  override fun byUserId(userId: UUID): RefreshTokenEntity? {
    return transaction(database) {
      RefreshTokenTable.selectAll()
        .where { RefreshTokenTable.userId eq userId }
        .firstOrNull()
    }?.toRefreshTokenEntity()
  }

  override fun byToken(token: String): RefreshTokenEntity? {
    return transaction(database) {
      RefreshTokenTable.selectAll()
        .where { RefreshTokenTable.token eq token }
        .firstOrNull()
    }?.toRefreshTokenEntity()
  }

  override fun deleteByUserId(userId: UUID): Boolean {
    val rowEffected = transaction(database) {
      RefreshTokenTable.deleteWhere {
        RefreshTokenTable.userId eq userId
      }
    }

    return rowEffected > 0
  }
}

fun ResultRow.toRefreshTokenEntity(): RefreshTokenEntity {
  return RefreshTokenEntity(
    userId = this[RefreshTokenTable.userId],
    token = this[RefreshTokenTable.token],
    createdAt = this[RefreshTokenTable.createdAt],
  )
}
