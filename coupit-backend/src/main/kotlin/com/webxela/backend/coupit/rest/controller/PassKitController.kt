package com.webxela.backend.coupit.rest.controller

import com.webxela.backend.coupit.service.PassKitService
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/pass")
class PassKitController(private val passKitService: PassKitService) {

    @GetMapping("/{spinId}", produces = ["application/vnd.apple.pkpass"])
    fun generatePass(
        @PathVariable spinId: String
    ): ResponseEntity<FileSystemResource> {
        val passFile = passKitService.createPass()
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"pass.pkpass\"")
            .contentType(MediaType.parseMediaType("application/vnd.apple.pkpass"))
            .body(FileSystemResource(passFile))
    }
}