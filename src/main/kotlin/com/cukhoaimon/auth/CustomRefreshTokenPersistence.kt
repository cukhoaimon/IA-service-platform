package com.cukhoaimon.auth

import com.cukhoaimon.database.entity.RefreshTokenEntity
import com.cukhoaimon.database.repository.RefreshTokenRepository
import com.cukhoaimon.database.repository.UserRepository
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class CustomRefreshTokenPersistence(
  private val refreshTokenRepository: RefreshTokenRepository,
  private val userRepository: UserRepository
) : RefreshTokenPersistence {

  override fun persistToken(event: RefreshTokenGeneratedEvent?) {
    if (event?.refreshToken == null || event.authentication?.name == null) {
      return
    }

    refreshTokenRepository.insert(
      RefreshTokenEntity(
        identity = event.authentication?.name!!,
        token = event.refreshToken
      )
    )
  }

  override fun getAuthentication(refreshToken: String): Publisher<Authentication> {
    return Flux.create({ emitter: FluxSink<Authentication> ->
      val tokenOpt = refreshTokenRepository.byToken(refreshToken)
      if (tokenOpt == null) {
        emitter.error(OauthErrorResponseException(INVALID_GRANT, "refresh token not found", null))
      }

      val user = tokenOpt?.let { userRepository.findByEmail(it.identity) }
      user?.let {
        emitter.next(Authentication.build(it.email))
        emitter.complete()
      } ?: emitter.error(OauthErrorResponseException(INVALID_GRANT, "user not found", null))

    }, FluxSink.OverflowStrategy.ERROR)
  }
}
