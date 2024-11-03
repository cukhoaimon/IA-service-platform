package com.cukhoaimon.client

import com.cukhoaimon.client.request.UserRegisterRequest
import com.cukhoaimon.database.entity.UserEntity
import io.micronaut.http.HttpHeaders.AUTHORIZATION
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("/api/user/")
interface UserClient {

  @Post("/register")
  fun register(@Body request: UserRegisterRequest): UserEntity

  @Get("/me")
  fun me(@Header(AUTHORIZATION) authorization: String): String
}
