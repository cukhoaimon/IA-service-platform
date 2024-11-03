package com.cukhoaimon.faker

import PasswordManager
import com.cukhoaimon.database.entity.UserEntity
import com.cukhoaimon.database.repository.UserRepository

class UserFaker(
  private val passwordManager: PasswordManager,
  private val repository: UserRepository
) {
  fun userEntity(email: String? = null): UserEntity {
    return repository.insert(
      UserEntity(
        email = email ?: StringFaker.randomEmail(),
        password = passwordManager.hash(StringFaker.simplePassword)
      )
    )
  }
}
