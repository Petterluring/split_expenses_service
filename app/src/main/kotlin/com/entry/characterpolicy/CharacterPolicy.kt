package com.entry.characterpolicy

class CharacterPolicy(
    val minlength: Int,
    regexPattern: String,
) {
    val regexPattern: String
        get() = regex.pattern

    private val regex = Regex(regexPattern)

    init {
        require(minlength > 0) { "minlength must be greater than 0" }
        require(regexPattern.isNotBlank()) { "regex must not be blank" }
    }

    fun followsPolicy(string: String): Boolean = matchesRegex(string) && hasMinLength(string)

    fun matchesRegex(string: String): Boolean = regex.matches(string)

    fun hasMinLength(string: String): Boolean = string.length >= minlength
}
