package com.rameshkp.openapi.merger.app.mergers

import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.Paths
import org.slf4j.LoggerFactory

/**
 *  A class to merge path objects in open api
 */
class PathsMerger: Mergeable<Paths> {
    private val log = LoggerFactory.getLogger(javaClass)
    private val paths = Paths()

    override fun merge(from: Paths?) {
        from?.run {
            forEach { entry ->
                val existingPathItem: PathItem? = paths[entry.key]
                val pathItem: PathItem = entry.value
                if (existingPathItem == null) {
                    paths[entry.key] = entry.value
                } else {
                    existingPathItem.run {

                        fun <T> setPropertyOnExistingPathItemIfMissing(
                            propertyName: String,
                            getProperty: (it: PathItem) -> T?,
                            setValueOnExistingPathItem: (value: T?) -> Unit
                        ) {
                            if (getProperty(existingPathItem) == null) {
                                setValueOnExistingPathItem(getProperty(pathItem))
                            } else if (getProperty(pathItem) != null) {
                                log.warn("{} in path entry for {} already exists. Hence skipping", propertyName, entry.key)
                            }
                        }

                        setPropertyOnExistingPathItemIfMissing("description", {it.description}, {existingPathItem.description = it})
                        setPropertyOnExistingPathItemIfMissing("summary", {it.summary}, {existingPathItem.summary = it})
                        setPropertyOnExistingPathItemIfMissing("get", {it.get}, {existingPathItem.get = it})
                        setPropertyOnExistingPathItemIfMissing("put", {it.put}, {existingPathItem.put = it})
                        setPropertyOnExistingPathItemIfMissing("post", {it.post}, {existingPathItem.post = it})
                        setPropertyOnExistingPathItemIfMissing("delete", {it.delete}, {existingPathItem.delete = it})
                        setPropertyOnExistingPathItemIfMissing("options", {it.options}, {existingPathItem.options = it})
                        setPropertyOnExistingPathItemIfMissing("head", {it.head}, {existingPathItem.head = it})
                        setPropertyOnExistingPathItemIfMissing("patch", {it.patch}, {existingPathItem.patch = it})
                        setPropertyOnExistingPathItemIfMissing("trace", {it.trace}, {existingPathItem.trace = it})
                        setPropertyOnExistingPathItemIfMissing("servers", {it.servers}, {existingPathItem.servers = it})
                        setPropertyOnExistingPathItemIfMissing("parameters", {it.parameters}, {existingPathItem.parameters = it})
                        setPropertyOnExistingPathItemIfMissing("ref", {it.`$ref`}, {existingPathItem.`$ref` = it})
                        setPropertyOnExistingPathItemIfMissing("extensions", {it.extensions}, {existingPathItem.extensions = it})
                    }
                }
            }
        }
    }

    override fun get(): Paths? {
        return if (paths.size > 0) paths else null
    }
}
