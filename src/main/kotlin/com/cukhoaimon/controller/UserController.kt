package com.cukhoaimon.controller

import PasswordManager
import com.cukhoaimon.client.request.UserRegisterRequest
import com.cukhoaimon.database.entity.UserEntity
import com.cukhoaimon.database.repository.UserRepository
import com.cukhoaimon.exception.Exceptions
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import java.security.Principal

@Tag(name = "UserController")
@Controller("/api/user")
@ExecuteOn(TaskExecutors.IO)
class UserController(
  private val userRepository: UserRepository,
  private val passwordManager: PasswordManager,
) {
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Post("/register")
  fun register(@Body @Valid request: UserRegisterRequest): UserEntity {
    getOrThrow(email = request.username)

    return userRepository.insert(
      UserEntity(
        email = request.username,
        password = passwordManager.hash(request.password)
      )
    )
  }

  @Secured(SecurityRule.IS_AUTHENTICATED)
  @Get("/me")
  fun me(principal: Principal): String = principal.name

  private fun getOrThrow(email: String) {
    val user = userRepository.findByEmail(email)
    if (user != null) {
      throw Exceptions.USER_EXISTED.throwable()
    }
  }
}
