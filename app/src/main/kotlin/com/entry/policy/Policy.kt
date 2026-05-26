package com.entry.policy

interface Policy<T> {
    fun matches(input: T): Boolean
}
