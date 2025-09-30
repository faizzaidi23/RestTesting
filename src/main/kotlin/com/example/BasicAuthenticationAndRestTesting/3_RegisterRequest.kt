package com.example.BasicAuthenticationAndRestTesting

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank(message="Email cannot be blank")
    @field:Email(message="Please provide a valid email format")
    val email: String,

    @field:NotBlank(message="Password cannot be blank")
    @field:Size(min=8,message="password myst be at least 8 characters long")
    val password: String
)