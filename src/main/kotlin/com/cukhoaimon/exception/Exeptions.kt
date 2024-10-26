package com.cukhoaimon.exception

import io.micronaut.http.HttpStatus

class FsException(
  override val message: String,
  val httpStatus: Int = HttpStatus.INTERNAL_SERVER_ERROR.code,
  val code: String
) : RuntimeException(message)
