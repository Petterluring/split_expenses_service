package com.generators.id

import com.generators.interfaces.IdGenerator
import com.generators.interfaces.IndexGenerator

/**
 *
 */
class RandomIdGenerator(
    val characters: CharArray,
    private val indexGenerator: IndexGenerator,
    val length: Int,
) : IdGenerator<String> {

    init {
        require(length > 0) { "length must be at least 1, was $length" }
    }

    override fun nextId(): String {
        return buildString {
            repeat(length) {
                val index = indexGenerator.nextIndex(length)
                require(index >= 0) { "index must be non-negative, was $index" }
                append(characters[index])
            }
        }
    }


}