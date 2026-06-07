package com.entry.idgenerator

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class IdGeneratorTest {
    private val chars = "A"
    private val length = 10

    @Test
    fun `can generate id with correct length`() {
        val idGen =
            SessionIdGenerator(
                length = length,
                chars = chars,
            )

        assertEquals(10, idGen.nextId().length)
    }

    @Test
    fun `can generate id with prefix`() {
        val idGen =
            SessionIdGenerator(
                prefix = "P-",
                length = length,
                chars = chars,
            )

        assertTrue(idGen.nextId().startsWith("P-"))
    }

    @Test
    fun `can generate id with prefix and suffix`() {
        val idGen =
            SessionIdGenerator(
                prefix = "P-",
                suffix = "-S",
                length = length,
                chars = chars,
            )

        assertTrue(idGen.nextId().startsWith("P-"))
        assertTrue(idGen.nextId().endsWith("-S"))
    }

    @Test
    fun `can generate id with and suffix`() {
        val idGen =
            SessionIdGenerator(
                suffix = "-S",
                length = length,
                chars = chars,
            )

        assertTrue(idGen.nextId().endsWith("-S"))
    }
}
