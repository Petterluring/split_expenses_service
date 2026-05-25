package com.entry.characterpolicy

/**
 * Allows the user to specify a policy for what characters are allowed in a string.
 * The class supports limited set of regex configurations, including occurrence of lowercase, lowercase + uppercase,
 * lowercase + uppercase + numbers, etc. All regex patterns include underscore (_).
 * */
class CharacterPolicy(
    val minLength: Int,
    val policyName: String = "default policy",
    private val includeLowercase: Boolean = true,
    private val includeUppercase: Boolean = true,
    private val includeNumbers: Boolean = true,
    private val includeSymbols: Boolean = true,
) {
    private val lowercase = "a-z"
    private val uppercase = "A-Z"
    private val numbers = "0-9"
    private var symbols = "?,.!@#$%&*=_-(){}[]"
    private val regex: Regex

    val regexPattern: String
        get() = regex.pattern

    init {
        require(minLength > 0) { "minlength must be greater than 0" }
        require(includeLowercase || includeUppercase || includeNumbers || includeSymbols) {
            "at least one set of characters must be included"
        }

        var lowercase = if (includeLowercase) this.lowercase else ""
        var uppercase = if (includeUppercase) this.uppercase else ""
        var numbers = if (includeNumbers) this.numbers else ""
        var symbols = if (includeSymbols) Regex.escape(this.symbols) else "_" // always include underscore

        val regexPattern = "^[${lowercase}${uppercase}${numbers}$symbols]{$minLength,}$"
        regex = Regex(regexPattern)
    }

    fun matches(str: String): Boolean = regex.matches(str)

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("**Character policy**\n")
        sb.append("Name: ${policyName}\n")
        sb.append("Included characters:\n")
        if (includeLowercase) sb.append(lowercase).append("\n")
        if (includeUppercase) sb.append(uppercase).append("\n")
        if (includeNumbers) sb.append(numbers).append("\n")
        if (includeSymbols) sb.append(symbols) else sb.append("_")

        return sb.toString()
    }
}
