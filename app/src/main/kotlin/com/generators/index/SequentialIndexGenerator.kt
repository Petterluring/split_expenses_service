package com.generators.index

import com.generators.interfaces.IndexGenerator

/**
 * Generates indices sequentially from a starting index.
 * e.g, if startingIndex = 1, then nextIndex function generates
 * 2 when called. This class is used for mocking purposes during testing.
 */
class SequentialIndexGenerator(
    private var startingIndex: Int,
) : IndexGenerator {

    init {
        require(startingIndex >= 0)
    }
    override fun nextIndex(maxIndex: Int): Int {
        require(maxIndex >= 1) {"maxIndex must at least be 1, was $maxIndex" }
        return startingIndex++ % maxIndex
    }


}