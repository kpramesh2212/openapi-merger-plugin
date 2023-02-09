package com.rameshkp.openapi.merger.app.mergers

import io.swagger.v3.oas.models.PathItem
import org.slf4j.LoggerFactory

class PathItemMerger(private val path: String, private val pathItem: PathItem): Mergeable<PathItem> {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun merge(from: PathItem?) {
        from?.apply {
           pathItem.setIfMissing("description", { pathItem ->  pathItem.description}, { pathItem -> pathItem.description = description })
           pathItem.setIfMissing("summary", { pathItem ->  pathItem.summary}, { pathItem -> pathItem.summary = summary })
           pathItem.setIfMissing("get", { pathItem ->  pathItem.get}, { pathItem -> pathItem.get = get })
           pathItem.setIfMissing("put", { pathItem ->  pathItem.put}, { pathItem -> pathItem.put = put })
           pathItem.setIfMissing("post", { pathItem ->  pathItem.post}, { pathItem -> pathItem.post = post })
           pathItem.setIfMissing("delete", { pathItem ->  pathItem.delete}, { pathItem -> pathItem.delete = delete })
           pathItem.setIfMissing("options", { pathItem ->  pathItem.options}, { pathItem -> pathItem.options = options })
           pathItem.setIfMissing("head", { pathItem ->  pathItem.head}, { pathItem -> pathItem.head = head })
           pathItem.setIfMissing("patch", { pathItem ->  pathItem.patch}, { pathItem -> pathItem.patch = patch })
           pathItem.setIfMissing("trace", { pathItem ->  pathItem.trace}, { pathItem -> pathItem.trace = trace })
           pathItem.setIfMissing("servers", { pathItem ->  pathItem.servers}, { pathItem -> pathItem.servers = servers })
           pathItem.setIfMissing("parameters", { pathItem ->  pathItem.parameters}, { pathItem -> pathItem.parameters = parameters })
           pathItem.setIfMissing("ref", { pathItem ->  pathItem.`$ref`}, { pathItem -> pathItem.`$ref` = `$ref` })
           pathItem.setIfMissing("extensions", { pathItem ->  pathItem.extensions}, { pathItem -> pathItem.extensions = extensions })
        }
    }

    override fun get(): PathItem {
        return pathItem
    }

    private fun <T> PathItem.setIfMissing(propertyName: String, getProperty: (pathItem: PathItem) -> T?, setProperty: (pathItem: PathItem) -> Unit) {
        val property = getProperty(this)
        if (property != null) log.warn("{} in path item for path {} already exists. Hence skipping", propertyName, path) else setProperty(this)
    }

}