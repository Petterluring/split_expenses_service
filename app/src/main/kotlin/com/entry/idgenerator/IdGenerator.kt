package com.entry.idgenerator

interface IdGenerator<T> {
    fun nextId(): T
}
