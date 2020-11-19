package com.rameshkp.openapi.merger.app.mergers

import org.slf4j.LoggerFactory
import java.util.*

/**
 *  A class to merge a Map with string keys and value T
 */
class MapMerger<T>: Mergeable<Map<String, T>> {
    private val log = LoggerFactory.getLogger(javaClass)
    private val map = TreeMap<String, T>()

    override fun merge(from: Map<String, T>?) {
        from?.run {
            forEach { entry ->
                if (map.containsKey(entry.key)) {
                    log.warn("{} already exist in the map. Hence skipping", entry.key)
                } else {
                    map[entry.key] = entry.value
                }
            }
        }
    }

    override fun get(): Map<String, T>? {
        return if (map.size > 0) map else null
    }
}