package com.rameshkp.openapi.merger.app.models

import javax.validation.constraints.NotBlank

data class License(@field:NotBlank(message = "License name cannot be blank") var name: String? = null, var url: String? = null)