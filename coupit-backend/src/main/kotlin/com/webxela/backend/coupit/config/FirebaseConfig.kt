package com.webxela.backend.coupit.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.FileInputStream

@Configuration
class FirebaseConfig {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(FirebaseConfig::class.java)
    }

    @Value("\${firebase.config.path}")
    private lateinit var firebaseConfigPath: String

    @PostConstruct
    fun initializeFirebase() {
        try {
            val serviceAccount = ClassPathResource(firebaseConfigPath).inputStream
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
                logger.info("FirebaseApp initialized successfully")
            }
        } catch (ex: Exception) {
            logger.error("Error initializing Firebase: ${ex.message}", ex)
            throw RuntimeException(ex)
        }
    }

}