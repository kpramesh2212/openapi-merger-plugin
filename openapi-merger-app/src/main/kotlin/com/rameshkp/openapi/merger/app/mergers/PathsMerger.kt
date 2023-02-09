package com.rameshkp.openapi.merger.app.mergers

import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.Paths

class PathsMerger: MapMerger<PathItem>() {
    override fun whenKeyExists(key: String, value: PathItem) {
        val existingPathItem = map.getValue(key)
        PathItemMerger(key, existingPathItem).merge(value)
    }

    override fun get(): Paths {
        val path = Paths()
        map.forEach { (key, value) ->  path.addPathItem(key, value)}
        return path
    }
}