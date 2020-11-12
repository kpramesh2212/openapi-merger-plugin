package com.rameshkp.openapi.mergers

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.tags.Tag
import org.slf4j.LoggerFactory

class OpenApiMerger: Mergeable<OpenAPI> {
    private val log = LoggerFactory.getLogger(javaClass)
    private val openAPI = OpenAPI()

    private val serversMerger = ListMerger<Server>()
    private val tagsMerger = ListMerger<Tag>()
    private val securityMerger = ListMerger<SecurityRequirement>()
    private val pathsMerger = PathsMerger()
    private val componentsMerger = ComponentsMerger()

    override fun merge(from: OpenAPI?) {
        from?.run {
            // Merge the server
            log.info("Merging servers")
            serversMerger.merge(this.servers)

            // Merge the paths
            log.info("Merging paths")
            pathsMerger.merge(this.paths)

            // Merge the components
            log.info("Merging components")
            componentsMerger.merge(this.components)

            // Merge security
            log.info("Merging Security")
            securityMerger.merge(this.security)

            // Merge tags
            log.info("Merging tags")
            tagsMerger.merge(this.tags)
        }
    }

    override fun get(): OpenAPI? {
        openAPI.servers = serversMerger.get()
        openAPI.paths = pathsMerger.get()
        openAPI.components = componentsMerger.get()
        openAPI.security = securityMerger.get()
        openAPI.tags = tagsMerger.get()
        return openAPI
    }
}