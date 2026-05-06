package com.generators.index

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class TestSequentialIndexGenerator {
    private val generator = SequentialIndexGenerator(
        0
    )

    @Test
    fun `can throw error if index is smaller than 0`() {
        assertThrows<IllegalArgumentException> {
            SequentialIndexGenerator(-1)
        }
        assertThrows<IllegalArgumentException> {
            SequentialIndexGenerator(-2)
        }
    }

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
    fun `can generate indices sequentially`() {
        val maxIndex = 30
        for (i in 0..<maxIndex) {
            assertEquals(i, generator.nextIndex(maxIndex))
        }
        assertEquals(0, generator.nextIndex(maxIndex))
    }
}