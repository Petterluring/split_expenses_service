package com.generators.interfaces

interface IdGenerator<T> {

    fun nextId(): T
}