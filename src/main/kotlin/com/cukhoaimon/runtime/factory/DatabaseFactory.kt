package com.cukhoaimon.runtime.factory

import com.cukhoaimon.database.repository.DefaultRefreshTokenRepository
import com.cukhoaimon.database.repository.DefaultUserRepository
import com.cukhoaimon.database.repository.RefreshTokenRepository
import com.cukhoaimon.database.repository.UserRepository
import com.cukhoaimon.runtime.config.AppConfiguration
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import org.jetbrains.exposed.sql.Database

@Factory
class DatabaseFactory {
  @Singleton
  fun provideDatabase(config: AppConfiguration.DatabaseConfig): Database {
    return Database.connect(
      "jdbc:postgresql://${config.url}:${config.port}/${config.name}",
      user = config.username,
      password = config.password,
      driver = "org.postgresql.Driver"
    )
  }

  @Singleton
  fun provideUserRepository(db: Database): UserRepository {
    return DefaultUserRepository(db)
  }

  @Singleton
  fun provideRefreshTokenRepository(db: Database): RefreshTokenRepository {
    return DefaultRefreshTokenRepository(db)
  }
}
