package com.rameshkp.openapi.merger.app.mergers

/**
 *  Interface that can merge objects of type T
 */
interface Mergeable<T> {
    /**
     *  A method to merge an object of type T
     */
    fun merge(from: T?)

    /**
     *  Return the merged object of type T
     */
    fun get(): T?
}
