package com.cukhoaimon.runtime.factory

import DefaultPasswordManager
import PasswordManager
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton

@Factory
class ManagerFactory {
  @Singleton
  fun providePasswordManager(): PasswordManager {
    return DefaultPasswordManager()
  }
}
