package com.rameshkp.openapi.merger.app.mergers

import org.slf4j.LoggerFactory
import java.util.*

/**
 *  A class to merge a Map with string keys and value T
 */
open class MapMerger<T>: Mergeable<Map<String, T>> {
    private val log = LoggerFactory.getLogger(javaClass)
    protected val map = TreeMap<String, T>()

    override fun merge(from: Map<String, T>?) {
        from?.run {
            forEach { entry ->
                if (map.containsKey(entry.key)) {
                    whenKeyExists(entry.key, entry.value)
                } else {
                    map[entry.key] = entry.value
                }
            }
        }
    }

    open fun whenKeyExists(key: String, value: T) {
        log.warn("{} already exist in the map. Hence skipping", key)
    }

    override fun get(): Map<String, T>? {
        return if (map.size > 0) map else null
    }
}