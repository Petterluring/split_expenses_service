package com.entry.configuration

import com.entry.policy.CharacterPolicy
import com.entry.policy.Policy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CharacterPolicyConfiguration {
    @Bean
    fun passwordPolicy(): Policy<String> =
        CharacterPolicy(
            minLength = 8,
            policyName = "Password policy",
            includeLowercase = true,
            includeUppercase = true,
            includeNumbers = true,
            includeSymbols = true,
        )

    @Bean
    fun usernamePolicy(): Policy<String> =
        CharacterPolicy(
            minLength = 4,
            policyName = "Username policy",
            includeLowercase = true,
            includeUppercase = false,
            includeNumbers = true,
            includeSymbols = false,
        )
}
