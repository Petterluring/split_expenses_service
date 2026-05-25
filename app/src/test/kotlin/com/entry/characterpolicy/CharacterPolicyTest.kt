package com.entry.characterpolicy

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CharacterPolicyTest {
    private val minLength = 4

    @Test
    fun `can validate character configuration`() {
        assertThrows<IllegalArgumentException> {
            CharacterPolicy(
                minLength = minLength,
                includeLowercase = false,
                includeUppercase = false,
                includeNumbers = false,
                includeSymbols = false,
            )
        }
    }

    @ParameterizedTest
    @ValueSource(
        ints = [
            -2, -1, 0,
        ],
    )
    fun `can validate minLength`(minLength: Int) {
        assertThrows<IllegalArgumentException> {
            CharacterPolicy(minLength)
        }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "roger",
            "roger_killman",
            "this_is_a_test",
        ],
    )
    fun `can validate lowercase configuration`(string: String) {
        val policy =
            CharacterPolicy(
                minLength = minLength,
                includeLowercase = true,
                includeUppercase = false,
                includeNumbers = false,
                includeSymbols = false,
            )
        assertTrue(policy.matches(string))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "rogerR!!?oger",
            "roger_killm43anROGER",
            "tHis_is_A_tes234t",
        ],
    )
    fun `can validate incorrect lowercase and uppercase configuration`(string: String) {
        val policy =
            CharacterPolicy(
                minLength = minLength,
                includeLowercase = true,
                includeUppercase = true,
                includeNumbers = false,
                includeSymbols = false,
            )
        assertFalse(policy.matches(string))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "rogerRoger",
            "roger_killmanROGER",
            "tHis_is_A_test",
        ],
    )
    fun `can validate lowercase and uppercase configuration`(string: String) {
        val policy =
            CharacterPolicy(
                minLength = minLength,
                includeLowercase = true,
                includeUppercase = true,
                includeNumbers = false,
                includeSymbols = false,
            )
        assertTrue(policy.matches(string))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "rogerRoger123_22",
            "roger_killmanROGER_4434",
            "tHis_i342s_A_te22st",
        ],
    )
    fun `can validate lowercase, uppercase and number configuration`(string: String) {
        val policy =
            CharacterPolicy(
                minLength = minLength,
                includeLowercase = true,
                includeUppercase = true,
                includeNumbers = true,
                includeSymbols = false,
            )
        assertTrue(policy.matches(string))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "rogerRoger123??!=_22",
            "roger_killmanROGE.,R_4434",
            "tHis_i342=\$s_A_te22st",
        ],
    )
    fun `can validate lowercase, uppercase, number, and symbol configuration`(string: String) {
        val policy =
            CharacterPolicy(
                minLength = minLength,
                includeLowercase = true,
                includeUppercase = true,
                includeNumbers = true,
                includeSymbols = true,
            )
        assertTrue(policy.matches(string))
    }
}
