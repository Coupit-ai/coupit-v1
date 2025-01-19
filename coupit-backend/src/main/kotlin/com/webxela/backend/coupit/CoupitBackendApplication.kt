package com.webxela.backend.coupit

import io.github.cdimascio.dotenv.dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class CoupitBackendApplication

fun main(args: Array<String>) {
    runApplication<CoupitBackendApplication>(*args) {
        addInitializers(object : ApplicationContextInitializer<ConfigurableApplicationContext> {
            override fun initialize(applicationContext: ConfigurableApplicationContext) {
                val dotenv = dotenv()
                for (entry in dotenv.entries()) {
                    System.setProperty(entry.key, entry.value)
                }
            }
        })
    }
}
