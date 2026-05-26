package com.entry.controller.advice

import com.entry.dto.generic.ErrorResponseDto
import com.entry.exception.DatabaseException
import com.entry.exception.InvalidRequestException
import com.entry.exception.ResourceAlreadyExistsException
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerController {
    @ExceptionHandler(ResourceAlreadyExistsException::class)
    fun handleResourceAlreadyExistsException(e: ResourceAlreadyExistsException): ResponseEntity<ErrorResponseDto> =
        ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(
                ErrorResponseDto(
                    code = HttpStatus.CONFLICT.value(),
                    error = HttpStatus.CONFLICT.reasonPhrase,
                    message = e.message ?: "Resource already exists",
                ),
            )

    @ExceptionHandler(InvalidRequestException::class)
    fun handleInvalidRequestException(e: InvalidRequestException): ResponseEntity<ErrorResponseDto> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponseDto(
                    code = HttpStatus.BAD_REQUEST.value(),
                    error = HttpStatus.BAD_REQUEST.reasonPhrase,
                    message = e.message ?: "Invalid request",
                ),
            )

    @ExceptionHandler(DatabaseException::class)
    fun handleDatabaseException(e: DatabaseException): ResponseEntity<ErrorResponseDto> =
        ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(
                ErrorResponseDto(
                    code = HttpStatus.SERVICE_UNAVAILABLE.value(),
                    error = HttpStatus.SERVICE_UNAVAILABLE.reasonPhrase,
                    message = e.message ?: "Database is temporarily unavailable",
                ),
            )
}
