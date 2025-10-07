package com.example.BasicAuthenticationAndRestTesting

import java.lang.RuntimeException

class UserAlreadyExistsException(email: String): RuntimeException("User with this email `$email` already exists")