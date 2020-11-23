package com.rameshkp.openapi.merger.app.models

import javax.validation.constraints.NotBlank

data class ExternalDocs(@field:NotBlank(message = "ExternalDocs Url cannot be blank") var url: String? = null, var description: String? = null)