package com.rameshkp.openapi.merger.app.mergers

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.callbacks.Callback
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.headers.Header
import io.swagger.v3.oas.models.links.Link
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.parameters.Parameter
import io.swagger.v3.oas.models.parameters.RequestBody
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.security.SecurityScheme

class ComponentsMerger: Mergeable<Components> {
    private val components = Components()

    private val schemasMerger = MapMerger<Schema<*>>()
    private val responsesMerger = MapMerger<ApiResponse>()
    private val parametersMerger = MapMerger<Parameter>()
    private val examplesMerger = MapMerger<Example>()
    private val requestBodiesMerger = MapMerger<RequestBody>()
    private val headersMerger = MapMerger<Header>()
    private val securitySchemasMerger = MapMerger<SecurityScheme>()
    private val linksMerger = MapMerger<Link>()
    private val callbacksMerger = MapMerger<Callback>()

    override fun merge(from: Components?) {
        from?.run {
            schemasMerger.merge(this.schemas)
            responsesMerger.merge(this.responses)
            parametersMerger.merge(this.parameters)
            examplesMerger.merge(this.examples)
            requestBodiesMerger.merge(this.requestBodies)
            headersMerger.merge(this.headers)
            securitySchemasMerger.merge(this.securitySchemes)
            linksMerger.merge(this.links)
            callbacksMerger.merge(this.callbacks)
        }
    }

    override fun get(): Components? {
        components.schemas = schemasMerger.get()
        components.responses = responsesMerger.get()
        components.parameters = parametersMerger.get()
        components.examples = examplesMerger.get()
        components.requestBodies = requestBodiesMerger.get()
        components.headers = headersMerger.get()
        components.securitySchemes = securitySchemasMerger.get()
        components.links = linksMerger.get()
        components.callbacks = callbacksMerger.get()
        return components
    }
}