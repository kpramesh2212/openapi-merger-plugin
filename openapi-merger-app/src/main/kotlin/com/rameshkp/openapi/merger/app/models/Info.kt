package com.rameshkp.openapi.merger.app.models

import javax.validation.Valid
import javax.validation.constraints.NotBlank

data class Info(@field:NotBlank(message = "OpenApi Info Title cannot be blank") var title: String? = null,
                var description: String? = null,
                var termsOfService: String? = null,
                @field:NotBlank(message = "OpenApi Info Version cannot be blank") var version: String? = null,
                var contact: Contact? = null,
                @field:Valid var license: License? = null)