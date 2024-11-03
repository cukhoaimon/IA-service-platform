package com.cukhoaimon

import com.cukhoaimon.database.table.RefreshTokenTable
import com.cukhoaimon.database.table.UserTable
import com.cukhoaimon.faker.StringFaker
import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.micronaut.security.authentication.UsernamePasswordCredentials
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationTest : AbstractControllerTest() {
  override fun beforeAll() {
  }

  @AfterAll
  override fun afterAll() {
    transaction(databaseContext) {
      RefreshTokenTable.deleteAll()
      UserTable.deleteAll()
    }
  }

  @Test
  fun `Login - Happy path`() {
    val user = userFaker.userEntity()
    val credentials = UsernamePasswordCredentials(user.email, StringFaker.simplePassword)

    val loginRsp = authenticationClient.login(credentials)
    assertNotNull(loginRsp)
    assertNotNull(loginRsp.accessToken)
    assertNotNull(loginRsp.refreshToken)
    assertTrue(JWTParser.parse(loginRsp.accessToken) is SignedJWT)
    assertTrue(JWTParser.parse(loginRsp.refreshToken) is SignedJWT)
    assert(loginRsp.username == user.email)
  }
}
