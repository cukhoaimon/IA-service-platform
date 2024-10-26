package com.cukhoaimon.exception

import io.micronaut.http.HttpStatus

enum class Exceptions {
  INTERNAL_SERVER_ERROR {
    override fun throwable(message: String?): FsException {
      return FsException(message ?: "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.code, code)
    }
  },

  BAD_REQUEST {
    override fun throwable(message: String?): FsException {
      return FsException(message ?: "Bad request", HttpStatus.BAD_REQUEST.code, code)
    }
  },

  USER_NOT_FOUND {
    override fun throwable(message: String?): FsException {
      return FsException(message ?: "User not found", HttpStatus.NOT_FOUND.code, code)
    }
  },

  USER_EXISTED {
    override fun throwable(message: String?): FsException {
      return FsException(message ?: "User existed", HttpStatus.BAD_REQUEST.code, code)
    }
  },

  ;

  abstract fun throwable(message: String? = null): FsException
  val code = name.lowercase()
}
