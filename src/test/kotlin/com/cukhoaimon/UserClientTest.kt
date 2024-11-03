package com.cukhoaimon

import com.cukhoaimon.client.UserClient
import com.cukhoaimon.database.table.UserTable
import com.cukhoaimon.faker.StringFaker
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserClientTest : AbstractControllerTest() {
  override fun beforeAll() {
  }

  @AfterAll
  override fun afterAll() {
    transaction(databaseContext) {
      UserTable.deleteAll()
    }
  }

  private val userClient = embeddedServer.applicationContext.getBean(UserClient::class.java)

  @Test
  fun `login and get user information - happy path`() {
    val user = userFaker.userEntity()
    val credentials: BearerAccessRefreshToken = authenticationClient.login(
      UsernamePasswordCredentials(
        user.email,
        StringFaker.simplePassword
      )
    )

    assertDoesNotThrow {
      val userInfo = userClient.me("Bearer ${credentials.accessToken}")
      assert(userInfo.email == user.email)
    }
  }
}
