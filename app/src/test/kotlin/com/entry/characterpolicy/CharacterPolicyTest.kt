package com.entry.characterpolicy

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CharacterPolicyTest {
    private val policy =
        CharacterPolicy(
            minlength = 10,
            regexPattern = "aaaaaaaaaa",
        )

    @Test
    fun `can validate minLength`() {
        for (i in -1..0) {
            val exception =
                assertThrows<IllegalArgumentException> {
                    CharacterPolicy(
                        minlength = i,
                        regexPattern = "abc",
                    )
                }
            assertEquals("minlength must be greater than 0", exception.message, "expected and actual are not equal")
        }
    }

    @Test
    fun `can validate regexPattern`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                CharacterPolicy(
                    minlength = 1,
                    regexPattern = "",
                )
            }

        assertEquals("regex must not be blank", exception.message, "expected and actual are not equal")
    }

    @Test
    fun `can validate required string length`() {
        val notTrueMessage = "string has too few characters"
        val isTrueMessage = "string has too many characters"
        assertTrue(policy.hasMinLength("aaaaaaaaaa"), message = notTrueMessage)
        assertTrue(policy.hasMinLength("aaaaaaaaaaa"), message = notTrueMessage)

        assertFalse(policy.hasMinLength("aaa"), message = isTrueMessage)
        assertFalse(policy.hasMinLength("aaaa"), message = isTrueMessage)
    }

    @Test
    fun `can validate string on required regex pattern`() {
        val invalidRegexPatternMessage = "Regex pattern is invalid"
        val validRegexPatternMessage = "Regex pattern is valid"
        assertTrue(policy.matchesRegex("aaaaaaaaaa"), message = invalidRegexPatternMessage)
        assertFalse(policy.matchesRegex("ab"), message = invalidRegexPatternMessage)
        assertFalse(policy.matchesRegex("abweffew"), message = invalidRegexPatternMessage)

        val newPolicy =
            CharacterPolicy(
                minlength = 10,
                regexPattern = "[A-Z]+",
            )

        assertTrue(newPolicy.matchesRegex("ASTA"), message = invalidRegexPatternMessage)
        assertTrue(newPolicy.matchesRegex("BUKA"), message = invalidRegexPatternMessage)
        assertTrue(newPolicy.matchesRegex("ROGER"), message = invalidRegexPatternMessage)

        assertFalse(newPolicy.matchesRegex("Roger"), message = validRegexPatternMessage)
        assertFalse(newPolicy.matchesRegex("KuNKa"), message = validRegexPatternMessage)
        assertFalse(newPolicy.matchesRegex("1234"), message = validRegexPatternMessage)
    }

    @Test
    fun `can validate string on required regex pattern and length`() {
        val invalidMessage = "string does not follow policy"
        val validMessage = "string does follow policy"
        assertTrue(policy.followsPolicy("aaaaaaaaaa"), invalidMessage)
        assertFalse(policy.followsPolicy("aaaaa"), validMessage)
        assertFalse(policy.followsPolicy("aaaaaaaaaaa"), validMessage)
    }

    @Test
    fun `can get regex pattern`() {
        assertEquals("aaaaaaaaaa", policy.regexPattern)
    }
}
