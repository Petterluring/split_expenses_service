package com.entry.service.component

class CharacterPolicy(
    val minlength: Int,
    regexPattern: String,
) {
    private val regex = Regex(regexPattern)

    init {
        require(minlength > 0) { "minlength must be greater than 0" }
        require(regexPattern.isNotBlank()) { "regex must not be blank" }
    }

    fun matchesRegex(string: String): Boolean = regex.matches(string)

    fun hasMinLength(string: String): Boolean = string.length >= minlength
}
