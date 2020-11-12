package com.rameshkp.openapi.mergers.models

import javax.validation.Valid
import javax.validation.constraints.NotNull

data class Info(@field:NotNull(message = "OpenApi Info Title cannot be null") var title: String? = null,
                var description: String? = null,
                var termsOfService: String? = null,
                @field:NotNull(message = "OpenApi Info Version cannot be null") var version: String? = null,
                var contact: Contact? = null,
                @field:Valid var license: License? = null)