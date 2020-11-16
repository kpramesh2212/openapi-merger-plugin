package com.rameshkp.openapi.merger.app.mergers

interface Mergeable<T> {
    fun merge(from: T?)
    fun get(): T?
}
