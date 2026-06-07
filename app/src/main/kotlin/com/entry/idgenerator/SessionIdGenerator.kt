package com.entry.idgenerator

class SessionIdGenerator(
    val prefix: String? = null,
    val suffix: String? = null,
    val length: Int = 20,
    val chars: String,
) : IdGenerator<String> {
    override fun nextId(): String {
        val suffix = this.suffix ?: ""
        val prefix = this.prefix ?: ""

        return prefix +
            (1..length)
                .map { chars.random() }
                .joinToString("") + suffix
    }
}
