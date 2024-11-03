package com.cukhoaimon.faker

import kotlin.random.Random
import org.apache.commons.lang3.RandomStringUtils

object StringFaker {
  val simplePassword = "password"

  fun randomPassword(): String {
    val uppercaseLetters = ('A'..'Z')
    val lowercaseLetters = ('a'..'z')
    val digits = ('0'..'9')
    val specialChars = listOf('!', '@', '#', '$', '%', '^', '&', '*')
    val allowedChars = uppercaseLetters + lowercaseLetters + digits + specialChars
    var password: String
    while (true) {
      password = (1..8).map { allowedChars.random() }.joinToString("")
      if (password.any { it in uppercaseLetters } &&
        password.any { it in lowercaseLetters } &&
        password.any { it in digits } &&
        password.any { it in specialChars }
      ) {
        break
      }
    }
    return password
  }

  // emails must start with an alphabet. They can't start with a number
  fun randomEmail(prefix: String? = null): String {
    return "${prefix ?: randomAlphabet(5)}${randomText(5)}@gmail.com"
  }

  fun randomText(length: Int = 10): String {
    return RandomStringUtils.randomAlphanumeric(length)
  }

  fun randomDateOfBirth(): String {
    val year = Random.nextInt(1950, 2000)
    val month = Random.nextInt(1, 12).toString().padStart(2, '0')
    val day = Random.nextInt(1, 28).toString().padStart(2, '0') // Simplified day generation
    return "$year-$month-$day"
  }

  fun randomPhoneNumber(): String {
    val areaCode = Random.nextInt(100, 999)
    val centralOfficeCode = Random.nextInt(100, 999)
    val lineNumber = Random.nextInt(1000, 9999)
    return "$areaCode-$centralOfficeCode-$lineNumber"
  }

  fun randomSocialSecurityNumber(): String {
    val part1 = Random.nextInt(100, 999)
    val part2 = Random.nextInt(10, 99)
    val part3 = Random.nextInt(1000, 9999)
    return "$part1-$part2-$part3"
  }

  fun randomAlphabet(length: Int = 10): String {
    return RandomStringUtils.randomAlphabetic(length)
  }
}

