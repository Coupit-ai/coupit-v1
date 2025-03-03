package com.webxela.backend.coupit.config

import de.brendamour.jpasskit.signing.PKSigningInformation
import de.brendamour.jpasskit.signing.PKSigningInformationUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.io.IOException

@Configuration
class PassKitConfig {

    @Value("\${passkit.certificate.path}")
    private lateinit var certificatePath: Resource

    @Value("\${passkit.certificate.password}")
    private lateinit var certificatePassword: String

    @Value("\${passkit.wwdr.certificate.path}")
    private lateinit var wwdrCertificatePath: Resource

    @Bean
    fun pkSigningInformation(): PKSigningInformation {
        try {
            val signingInformationUtil = PKSigningInformationUtil()
            return signingInformationUtil.loadSigningInformationFromPKCS12AndIntermediateCertificate(
                certificatePath.inputStream,
                certificatePassword,
                wwdrCertificatePath.inputStream
            )
        } catch (e: IOException) {
            throw RuntimeException("Failed to load PassKit certificates", e)
        }
    }
}