package com.example.BasicAuthenticationAndRestTesting

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService(private val userRepository: UserRepository): UserDetailsService{

    override fun loadUserByUsername(username: String): UserDetails{
        val user=userRepository.findByEmail(username)
            ?:throw UsernameNotFoundException("User with this email `$username` not found")

        //converting  our User roles to spring security's SimpleGrantedAuthority
        val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))

        return org.springframework.security.core.userdetails.User(user.email,user.password,authorities)
    }
}