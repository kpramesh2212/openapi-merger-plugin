package com.rameshkp.openapi.merger.app.models

import javax.validation.constraints.NotNull

data class License(@field:NotNull(message = "License name cannot be null") var name: String? = null, var url: String? = null)