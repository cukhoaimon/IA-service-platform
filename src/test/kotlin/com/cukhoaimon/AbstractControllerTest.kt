package com.cukhoaimon

import DefaultPasswordManager
import com.cukhoaimon.client.AuthenticationClient
import com.cukhoaimon.database.repository.UserRepository
import com.cukhoaimon.faker.UserFaker
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.jetbrains.exposed.sql.Database

abstract class AbstractControllerTest {

  /**
   * Setup data for all test run
   * Mark it as @BeforeAll
   */
  abstract fun beforeAll()

  /**
   * Clean data after all tests run
   * Mark it as @AfterAll
   */
  abstract fun afterAll()

  val embeddedServer: EmbeddedServer = ApplicationContext.run(EmbeddedServer::class.java, emptyMap())
  private val userRepository: UserRepository = embeddedServer.applicationContext.getBean(UserRepository::class.java)
  val databaseContext: Database = embeddedServer.applicationContext.getBean(Database::class.java)

  val authenticationClient: AuthenticationClient = embeddedServer.applicationContext.getBean(AuthenticationClient::class.java)
  val userFaker = UserFaker(DefaultPasswordManager(), userRepository)
}
