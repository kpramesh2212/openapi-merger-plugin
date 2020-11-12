package com.rameshkp.openapi.mergers

interface Mergeable<T> {
    fun merge(from: T?)
    fun get(): T?
}
