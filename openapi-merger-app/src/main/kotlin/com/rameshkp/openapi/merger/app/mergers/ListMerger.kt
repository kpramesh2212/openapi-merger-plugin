package com.rameshkp.openapi.merger.app.mergers

import org.slf4j.LoggerFactory

/**
 *  A class to merge a list of components of type T
 */
class ListMerger<T>: Mergeable<List<T>> {
    private val log = LoggerFactory.getLogger(javaClass)
    private val list = ArrayList<T>()

    override fun merge(from: List<T>?) {
        from?.run {
            forEach { t ->
                if (list.contains(t)) {
                    log.warn("List already contains {}. Hence Skipping", t)
                } else {
                    list.add(t)
                }
            }
        }
    }

    override fun get(): List<T>? {
        return if (list.size > 0) list else null
    }
}