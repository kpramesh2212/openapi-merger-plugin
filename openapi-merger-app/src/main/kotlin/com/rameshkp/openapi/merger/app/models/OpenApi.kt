package com.rameshkp.openapi.merger.app.models

import javax.validation.Valid
import javax.validation.constraints.NotNull

data class OpenApi(@field:NotNull(message = "OpenApi version cannot be null") var version: String? = null,
                   @field:NotNull(message = "OpenApi Info object cannot be null") @field:Valid var info: Info? = null,
                   @field:Valid var externalDocs: ExternalDocs? = null)