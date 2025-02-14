package com.webxela.backend.coupit.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.webxela.backend.coupit.utils.AppConstants
import com.webxela.backend.coupit.utils.ApplePassBuilder
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.*
import java.security.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@Service
class PassKitService {

    private val objectMapper = ObjectMapper()
    private val passTemplateDir by lazy {
        ClassPathResource("passkit-template").file
    }
    private val p12FilePath by lazy {
        File(passTemplateDir, "SigningCert.p12").absolutePath
    }
    private val p12Password = AppConstants.P12_PASSWORD
    private val outputPassFile by lazy {
        File.createTempFile("pass", ".pkpass")
    }

    fun createPass(): File {
        try {
            // Build the ApplePass
            val applePass = ApplePassBuilder()
                .setPassTypeIdentifier("pass.com.example.coupon")
                .setSerialNumber("COUPON12345")
                .setTeamIdentifier("ABC1234567")
                .setOrganizationName("Example Store")
                .setDescription("Discount Coupon")
                .setForegroundColor("rgb(255, 255, 255)")
                .setBackgroundColor("rgb(255, 69, 0)")
                .setLogoText("20% OFF")
                .setBarcode("SAVE20", "PKBarcodeFormatQR", "iso-8859-1")
                .addPrimaryField("offer", "Offer", "SAVE20")
                .addSecondaryField("expires", "Expires", "2023-12-31")
                .addAuxiliaryField("terms", "Terms", "One-time use only.")
                .build()

            val passJson = objectMapper.writeValueAsString(applePass)
            val passJsonFile = File(passTemplateDir, "pass.json")
            passJsonFile.writeText(passJson)

            val keyStore = KeyStore.getInstance("PKCS12")
            FileInputStream(p12FilePath).use { fis ->
                keyStore.load(fis, p12Password.toCharArray())
            }
            val alias = keyStore.aliases().nextElement()
            val privateKey = keyStore.getKey(alias, p12Password.toCharArray()) as PrivateKey

            val manifest = createManifest()
            val signature = signManifest(manifest, privateKey)
            createPkPassFile(manifest, signature)
            passJsonFile.delete()
            File(passTemplateDir, "manifest.json").delete()

            return outputPassFile
        } catch (e: Exception) {
            throw RuntimeException("Failed to create Apple Pass: ${e.message}", e)
        }
    }

    private fun createManifest(): String {
        val manifest = mutableMapOf<String, String>()
        passTemplateDir.walk().filter { it.isFile && it.name != "manifest.json" }.forEach { file ->
            val sha1 = hashFile(file)
            manifest[file.name] = sha1
        }
        val manifestJson = objectMapper.writeValueAsString(manifest)
        File(passTemplateDir, "manifest.json").writeText(manifestJson)
        return manifestJson
    }

    private fun hashFile(file: File): String {
        val digest = MessageDigest.getInstance("SHA-1")
        FileInputStream(file).use { fis ->
            val buffer = ByteArray(8192)
            var read: Int
            while (fis.read(buffer).also { read = it } != -1) {
                digest.update(buffer, 0, read)
            }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }

    private fun signManifest(manifest: String, privateKey: PrivateKey): ByteArray {
        Security.addProvider(BouncyCastleProvider())
        val signature = Signature.getInstance("SHA1withRSA", "BC")
        signature.initSign(privateKey)
        signature.update(manifest.toByteArray())
        return signature.sign()
    }

    private fun createPkPassFile(manifest: String, signature: ByteArray) {
        FileOutputStream(outputPassFile).use { fos ->
            ZipOutputStream(fos).use { zos ->
                // Add all files from the pass template directory EXCEPT manifest.json
                passTemplateDir.walk().filter { it.isFile && it.name != "manifest.json" }.forEach { file ->
                    zos.putNextEntry(ZipEntry(file.name))
                    FileInputStream(file).use { fis ->
                        fis.copyTo(zos)
                    }
                    zos.closeEntry()
                }

                zos.putNextEntry(ZipEntry("manifest.json"))
                zos.write(manifest.toByteArray())
                zos.closeEntry()

                zos.putNextEntry(ZipEntry("signature"))
                zos.write(signature)
                zos.closeEntry()
            }
        }
    }
}