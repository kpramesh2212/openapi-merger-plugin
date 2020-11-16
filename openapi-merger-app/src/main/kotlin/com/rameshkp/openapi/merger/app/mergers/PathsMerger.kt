package com.rameshkp.openapi.merger.app.mergers

import io.swagger.v3.oas.models.Paths
import org.slf4j.LoggerFactory

class PathsMerger: Mergeable<Paths> {
    private val log = LoggerFactory.getLogger(javaClass)
    private val paths = Paths()

    override fun merge(from: Paths?) {
        from?.run {
            forEach { entry ->
                if (paths.containsKey(entry.key)) {
                    log.warn("Path entry for {} already exists. Hence skipping", entry.key)
                } else {
                    paths[entry.key] = entry.value
                }
            }
        }
    }

    override fun get(): Paths? {
        return if (paths.size > 0) paths else null
    }
}