package com.example.BasicAuthenticationAndRestTesting

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/*
This new service will now contain the logic
or you can say all the services that the user can basically use

*/
@Service

class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
){
    // the register function
    fun register(request: RegisterRequest):User{

        //check if the user already exists
        if(userRepository.findByEmail(request.email)!=null){
            throw UserAlreadyExistsException(request.email)
        }

        //Hash the password
        val hashedPassword=passwordEncoder.encode(request.password)

        //Creating a new user object
        val newUser=User(
            email=request.email,
            // Update this to use the new property name
            passwordHash = hashedPassword
        )

        //save the user and then return it
        return userRepository.save(newUser)
    }
}
