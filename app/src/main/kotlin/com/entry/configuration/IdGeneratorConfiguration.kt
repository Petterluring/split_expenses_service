package com.entry.configuration

import com.entry.idgenerator.IdGenerator
import com.entry.idgenerator.SessionIdGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class IdGeneratorConfiguration {
    @Bean
    fun sessionIdGenerator(): IdGenerator<String> =
        SessionIdGenerator(
            length = 25,
            chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,?!=-_@#%&*+",
        )
}
