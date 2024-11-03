package com.cukhoaimon.client

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken

@Client("/")
interface AuthenticationClient {

  @Post("/login")
  fun login(@Body credentials: UsernamePasswordCredentials): BearerAccessRefreshToken
}
