package com.cukhoaimon.events

import com.cukhoaimon.runtime.config.AppConfiguration
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.event.ApplicationStartupEvent
import jakarta.inject.Singleton
import org.flywaydb.core.Flyway

@Singleton
class MainApplicationStartupEventListener(
	private val config: AppConfiguration.DatabaseConfig
) : ApplicationEventListener<ApplicationStartupEvent> {
	override fun onApplicationEvent(event: ApplicationStartupEvent) {
		val flyway = Flyway.configure()
			.dataSource("jdbc:postgresql://${config.url}:${config.port}/${config.name}", config.username, config.password)
			.load()
		flyway.migrate()
	}
}
