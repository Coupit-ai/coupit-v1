package com.webxela.backend.coupit.service

import com.webxela.backend.coupit.infra.persistence.adapter.SpinRepoAdapter
import de.brendamour.jpasskit.PKBarcode
import de.brendamour.jpasskit.PKField
import de.brendamour.jpasskit.PKPass
import de.brendamour.jpasskit.enums.PKBarcodeFormat
import de.brendamour.jpasskit.enums.PKPassType
import de.brendamour.jpasskit.passes.PKGenericPass
import de.brendamour.jpasskit.signing.PKFileBasedSigningUtil
import de.brendamour.jpasskit.signing.PKPassTemplateFolder
import de.brendamour.jpasskit.signing.PKSigningInformation
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.awt.Color
import java.net.URI
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*


@Service
class PassKitService(
    private val pkSigningInformation: PKSigningInformation,
    private val spinRepo: SpinRepoAdapter
) {

    @Value("\${passkit.pass.typeIdentifier}")
    private lateinit var passTypeIdentifier: String

    @Value("\${passkit.team.identifier}")
    private lateinit var teamIdentifier: String

    @Value("\${passkit.organization.name}")
    private lateinit var organizationName: String

    @Value("\${passkit.webservice.url}")
    private lateinit var webServiceUrl: String

    @Value("\${passkit.template.directory}")
    private lateinit var templateDirectory: Resource

    fun generateRewardPass(spinId: UUID): ByteArray {

        val spin = spinRepo.getSpinById(spinId)
            ?: throw RuntimeException("Cannot generate pass for non-existent reward")

        val pass = createRewardPassTemplate(
            title = spin.reward.title,
            expiryDate = spin.expiresAt,
            spinId = spin.id!!,
            discountCode = spin.reward.discountCode,
            storeName = spin.reward.merchant.businessName
        )

        // Get the template folder
        val templatePath = templateDirectory.file.absolutePath
        val passTemplate = PKPassTemplateFolder(templatePath)

        // Sign and package the pass
        val pkSigningUtil = PKFileBasedSigningUtil()
        return pkSigningUtil.createSignedAndZippedPkPassArchive(
            pass,
            passTemplate,
            pkSigningInformation
        )
    }

    private fun createRewardPassTemplate(
        title: String,
        expiryDate: Instant,
        spinId: UUID,
        discountCode: String,
        storeName: String
    ): PKPass {
        val formatter = java.time.format.DateTimeFormatter
            .ofPattern("MMM dd, yyyy")
            .withZone(java.time.ZoneId.systemDefault())
        val formattedExpiry = formatter.format(expiryDate)

        return PKPass.builder()
            .pass(
                PKGenericPass.builder()
                    .passType(PKPassType.PKCoupon)
                    .primaryFieldBuilder(
                        PKField.builder()
                            .key("title")
                            .label("REWARD")
                            .value(title)
                    )
                    .headerFieldBuilder(
                        PKField.builder()
                            .key("expiry")
                            .label("EXPIRES")
                            .value(formattedExpiry)
                    )
                    .auxiliaryFieldBuilder(
                        PKField.builder()
                            .key("Code")
                            .label("DISCOUNT CODE")
                            .value(discountCode)
                    )
            )
            .barcodeBuilder(
                PKBarcode.builder()
                    .format(PKBarcodeFormat.PKBarcodeFormatQR)
                    .message(spinId.toString())
                    .messageEncoding(StandardCharsets.UTF_8)
            )
            .formatVersion(1)
            .passTypeIdentifier(passTypeIdentifier)
            .serialNumber(spinId.toString())
            .teamIdentifier(teamIdentifier)
            .organizationName(organizationName)
            .webServiceURL(URI(webServiceUrl).toURL())
            .authenticationToken(UUID.randomUUID().toString())
            .logoText("Coupit")
            .expirationDate(expiryDate)
            .organizationName(storeName)
            .description("Coupit Reward")
            .backgroundColor(Color.decode("#2C3E50"))
            .foregroundColor(Color.decode("#ECF0F1"))
            .labelColor(Color.decode("#3498DB"))
            .build()
    }
}