package com.entry.configuration

import com.entry.characterpolicy.CharacterPolicy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CharacterPolicyConfiguration {

    @Bean
    fun passwordPolicy(): CharacterPolicy = CharacterPolicy(
        minLength = 8,
        policyName = "Password policy",
        includeLowercase = true,
        includeUppercase = true,
        includeNumbers = true,
        includeSymbols = true
    )

    @Bean
    fun usernamePolicy(): CharacterPolicy = CharacterPolicy(
        minLength = 4,
        policyName = "Username policy",
        includeLowercase = true,
        includeUppercase = false,
        includeNumbers = true,
        includeSymbols = false
    )
}
