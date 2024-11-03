package com.cukhoaimon.auth

import PasswordManager
import com.cukhoaimon.database.repository.UserRepository
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider
import jakarta.inject.Singleton

@Singleton
class AuthenticationProviderUserPassword<B>(
  private val userRepository: UserRepository,
  private val passwordManager: PasswordManager,
) : HttpRequestAuthenticationProvider<B> {

  override fun authenticate(
    httpRequest: HttpRequest<B>?,
    authenticationRequest: AuthenticationRequest<String, String>
  ): AuthenticationResponse {
//    val user = userRepository.findByEmail(authenticationRequest.identity) ?: return AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND)
//
//    if (!passwordManager.isValidPassword(authenticationRequest.secret)) {
//      return AuthenticationResponse.failure(AuthenticationFailureReason.UNKNOWN)
//    }
//
//    if (!passwordManager.check(authenticationRequest.secret, user.password)) {
//      return AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
//    }
    return AuthenticationResponse.success(authenticationRequest.identity)
  }
}
