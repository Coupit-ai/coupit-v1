package com.webxela.app.coupit.data.model.mapper

import com.webxela.app.coupit.data.model.dto.ConnectionDto
import com.webxela.app.coupit.domain.model.Connection

object ConnectionDtoMapper {

    fun ConnectionDto.toConnection(): Connection {
        return Connection(
            message = this.data.message,
            redirectUri = this.data.redirectUri
        )
    }
}