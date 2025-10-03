package com.example.BasicAuthenticationAndRestTesting

import java.lang.RuntimeException

class UserAlreadyExistsException(email: String): RuntimeException("User with email `$email` already exists")