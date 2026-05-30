package com.entry.dto.generic

data class ErrorResponseDto(
    val code: Int,
    val error: String,
    val message: String,
)
