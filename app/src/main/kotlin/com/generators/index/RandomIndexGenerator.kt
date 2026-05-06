package com.generators.index

import com.generators.interfaces.IndexGenerator
import kotlin.random.Random


/**
 * Generates random indices that can be used to randomly select
 * elements from an array/list of elements
 */
class RandomIndexGenerator(
    private val random: Random,
) : IndexGenerator {

    override fun nextIndex(maxIndex: Int): Int {
        require(maxIndex >= 1) { "maxIndex must be at least 1, was $maxIndex" }
        return random.nextInt(maxIndex)
    }
}