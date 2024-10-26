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
import io.micronaut.serde.annotation.SerdeImport
import jakarta.validation.Valid

@SerdeImport(UserRegisterRequest::class)
@SerdeImport(UserEntity::class)
@ExecuteOn(TaskExecutors.IO)
@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/api/user/register")
class AuthController(
  private val userRepository: UserRepository,
  private val passwordManager: PasswordManager,
) {

  @Post
  fun post(@Body @Valid request: UserRegisterRequest): UserEntity {
    getOrThrow(email = request.email)

    return userRepository.insert(
      UserEntity(
        email = request.email,
        password = passwordManager.hash(request.password)
      )
    )
  }

  @Get()
  fun getAll(): List<UserEntity> {
    return userRepository.listAll()
  }

  private fun getOrThrow(email: String) {
    val user = userRepository.findByEmail(email)
    if (user != null) {
      throw Exceptions.USER_EXISTED.throwable()
    }
  }
}
