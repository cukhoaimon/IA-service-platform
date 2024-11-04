package com.cukhoaimon.database.repository

import com.cukhoaimon.database.entity.RefreshTokenEntity
import com.cukhoaimon.database.table.RefreshTokenTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

interface RefreshTokenRepository {
  fun insert(refreshToken: RefreshTokenEntity): RefreshTokenEntity
  fun byIdentity(identity: String): RefreshTokenEntity?
  fun byToken(token: String): RefreshTokenEntity?
  fun deleteByIdentity(identity: String): Boolean
}

class DefaultRefreshTokenRepository(
  val database: Database,
) : RefreshTokenRepository {
  override fun insert(refreshToken: RefreshTokenEntity): RefreshTokenEntity {
    return transaction(database) {
      RefreshTokenTable.insert {
        it[id] = refreshToken.id
        it[identity] = refreshToken.identity
        it[token] = refreshToken.token
        it[createdAt] = refreshToken.createdAt
      }

      refreshToken
    }
  }

  override fun byIdentity(identity: String): RefreshTokenEntity? {
    return transaction(database) {
      RefreshTokenTable.selectAll()
        .where { RefreshTokenTable.identity eq identity }
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

  override fun deleteByIdentity(identity: String): Boolean {
    val rowEffected = transaction(database) {
      RefreshTokenTable.deleteWhere {
        RefreshTokenTable.identity eq identity
      }
    }

    return rowEffected > 0
  }
}

fun ResultRow.toRefreshTokenEntity(): RefreshTokenEntity {
  return RefreshTokenEntity(
    id = this[RefreshTokenTable.id].value,
    identity = this[RefreshTokenTable.identity],
    token = this[RefreshTokenTable.token],
    createdAt = this[RefreshTokenTable.createdAt],
  )
}
