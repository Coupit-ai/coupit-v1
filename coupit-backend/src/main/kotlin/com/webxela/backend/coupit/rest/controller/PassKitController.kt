package com.webxela.backend.coupit.rest.controller

import com.webxela.backend.coupit.rest.static.getRewardPassPage
import com.webxela.backend.coupit.service.PassKitService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/v1/pass")
class PassKitController(private val passKitService: PassKitService) {

    @GetMapping("/generate/{spinId}", produces = ["application/vnd.apple.pkpass"])
    fun generatePass(@PathVariable spinId: UUID): ResponseEntity<ByteArray> {
        val passData = passKitService.generateRewardPass(spinId)
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"reward.pkpass\"")
            .contentType(MediaType.parseMediaType("application/vnd.apple.pkpass"))
            .body(passData)
    }

    @GetMapping("/{spinId}")
    fun getPassPage(@PathVariable spinId: UUID): ResponseEntity<String> {
        val passkitUrl = "/v1/pass/generate/$spinId"
        val html = getRewardPassPage(passkitUrl)
        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_HTML)
            .body(html)
    }

}