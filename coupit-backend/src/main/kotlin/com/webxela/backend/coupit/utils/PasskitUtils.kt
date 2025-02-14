package com.webxela.backend.coupit.utils

import com.fasterxml.jackson.annotation.JsonProperty

data class ApplePass(
    @JsonProperty("formatVersion") val formatVersion: Int = 1,
    @JsonProperty("passTypeIdentifier") val passTypeIdentifier: String,
    @JsonProperty("serialNumber") val serialNumber: String,
    @JsonProperty("teamIdentifier") val teamIdentifier: String,
    @JsonProperty("organizationName") val organizationName: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("foregroundColor") val foregroundColor: String,
    @JsonProperty("backgroundColor") val backgroundColor: String,
    @JsonProperty("labelColor") val labelColor: String? = null,
    @JsonProperty("logoText") val logoText: String? = null,
    @JsonProperty("barcode") val barcode: Barcode,
    @JsonProperty("coupon") val coupon: Coupon // Add the coupon section here
) {
    data class Barcode(
        @JsonProperty("message") val message: String,
        @JsonProperty("format") val format: String,
        @JsonProperty("messageEncoding") val messageEncoding: String
    )

    data class Coupon(
        @JsonProperty("primaryFields") val primaryFields: List<Field>,
        @JsonProperty("secondaryFields") val secondaryFields: List<Field> = emptyList(),
        @JsonProperty("auxiliaryFields") val auxiliaryFields: List<Field> = emptyList()
    ) {
        data class Field(
            @JsonProperty("key") val key: String,
            @JsonProperty("label") val label: String,
            @JsonProperty("value") val value: String
        )
    }
}

class ApplePassBuilder {
    private var passTypeIdentifier: String = ""
    private var serialNumber: String = ""
    private var teamIdentifier: String = ""
    private var organizationName: String = ""
    private var description: String = ""
    private var foregroundColor: String = ""
    private var backgroundColor: String = ""
    private var labelColor: String? = null
    private var logoText: String? = null
    private var barcodeMessage: String = ""
    private var barcodeFormat: String = ""
    private var barcodeMessageEncoding: String = ""

    // Fields for the coupon section
    private val primaryFields: MutableList<ApplePass.Coupon.Field> = mutableListOf()
    private val secondaryFields: MutableList<ApplePass.Coupon.Field> = mutableListOf()
    private val auxiliaryFields: MutableList<ApplePass.Coupon.Field> = mutableListOf()

    fun setPassTypeIdentifier(identifier: String): ApplePassBuilder {
        this.passTypeIdentifier = identifier
        return this
    }

    fun setSerialNumber(serial: String): ApplePassBuilder {
        this.serialNumber = serial
        return this
    }

    fun setTeamIdentifier(teamId: String): ApplePassBuilder {
        this.teamIdentifier = teamId
        return this
    }

    fun setOrganizationName(orgName: String): ApplePassBuilder {
        this.organizationName = orgName
        return this
    }

    fun setDescription(desc: String): ApplePassBuilder {
        this.description = desc
        return this
    }

    fun setForegroundColor(color: String): ApplePassBuilder {
        this.foregroundColor = color
        return this
    }

    fun setBackgroundColor(color: String): ApplePassBuilder {
        this.backgroundColor = color
        return this
    }

    fun setLabelColor(color: String?): ApplePassBuilder {
        this.labelColor = color
        return this
    }

    fun setLogoText(text: String?): ApplePassBuilder {
        this.logoText = text
        return this
    }

    fun setBarcode(message: String, format: String, encoding: String): ApplePassBuilder {
        this.barcodeMessage = message
        this.barcodeFormat = format
        this.barcodeMessageEncoding = encoding
        return this
    }

    fun addPrimaryField(key: String, label: String, value: String): ApplePassBuilder {
        primaryFields.add(ApplePass.Coupon.Field(key, label, value))
        return this
    }

    fun addSecondaryField(key: String, label: String, value: String): ApplePassBuilder {
        secondaryFields.add(ApplePass.Coupon.Field(key, label, value))
        return this
    }

    fun addAuxiliaryField(key: String, label: String, value: String): ApplePassBuilder {
        auxiliaryFields.add(ApplePass.Coupon.Field(key, label, value))
        return this
    }

    fun build(): ApplePass {
        require(primaryFields.isNotEmpty()) { "At least one primary field is required." }
        val barcode = ApplePass.Barcode(barcodeMessage, barcodeFormat, barcodeMessageEncoding)
        val coupon = ApplePass.Coupon(
            primaryFields = primaryFields.toList(),
            secondaryFields = secondaryFields.toList(),
            auxiliaryFields = auxiliaryFields.toList()
        )
        return ApplePass(
            passTypeIdentifier = passTypeIdentifier,
            serialNumber = serialNumber,
            teamIdentifier = teamIdentifier,
            organizationName = organizationName,
            description = description,
            foregroundColor = foregroundColor,
            backgroundColor = backgroundColor,
            labelColor = labelColor,
            logoText = logoText,
            barcode = barcode,
            coupon = coupon
        )
    }
}