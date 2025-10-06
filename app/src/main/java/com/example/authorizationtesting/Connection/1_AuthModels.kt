package com.example.authorizationtesting.Connection

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/*
These data classes are essential for kotlinx serialization to work
the @Serializable annotation tells the compiler to generrate code for converting these
objects to and from JSON. The variable names must watch the JSON keys
*/

@OptIn(InternalSerializationApi::class)
@Serializable
data class AuthRequest(
    val email:String,
    val password: String
)

@OptIn(InternalSerializationApi::class)

@Serializable
data class AuthResponse(
    val token: String
)