package com.generators.index

import org.junit.jupiter.api.assertThrows
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

class TestRandomIndexGenerator {

    private val seed: Int = 100
    private val generator = RandomIndexGenerator(
        Random(seed)
    )

    @Test
    fun `throws error when max index is less than 1`() {
        assertThrows<IllegalArgumentException> {
            generator.nextIndex(0)
        }
        assertThrows<IllegalArgumentException> {
            generator.nextIndex(-1)
        }
    }

    @Test
    fun `can generate indices in the correct range`() {
        val maxIndex = 10
        for (i in 0..1000) {
            val index = generator.nextIndex(maxIndex)
            assertTrue { index in 0..<maxIndex }
        }
    }
}