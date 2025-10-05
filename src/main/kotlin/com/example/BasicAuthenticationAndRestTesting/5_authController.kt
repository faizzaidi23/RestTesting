package com.example.BasicAuthenticationAndRestTesting

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userService:UserService,
    private val userRepository: UserRepository,
    private val jwtService: JwtService
){

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<User>{
        val savedUser=userService.register(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser)

    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<LoginResponse>{
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email,request.password)
        )

        //if the authencation is successful , find the user
        val user= userRepository.findByEmail(request.email)
            ?:throw IllegalStateException("User not found after the authentication")

        //Generate the JWT for the user

        val jwtToken = jwtService.generateToken(user)

        return ResponseEntity.ok(LoginResponse(token=jwtToken))


    }

}