package com.cukhoaimon

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License

@OpenAPIDefinition(
  info = Info(
    title = "service-platform",
    version = "\${api.version}",
    description = "\${openapi.description}",
    license = License(
      name = "Cu khoai mon"
    )
  )
)
class Main {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      Micronaut
        .build(*args)
        .mainClass(Main::class.java)
        .eagerInitSingletons(true)
        .deduceEnvironment(true)
        .start()
    }
  }
}
