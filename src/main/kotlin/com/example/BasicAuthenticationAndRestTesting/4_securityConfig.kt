package com.example.BasicAuthenticationAndRestTesting

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

/*
Configuration--> Marks this class as a source of bean definitions
@Bean--> this annotation tells the spring that the passwordEncoder() method
produces a bean to be managed by the spring container. Now, whenever we need a PasswordEncoder in the application like
in our controller spring will know to provide the BCryptPasswordEncoder instance.
BCrypt is the industry standard algorithm for hashing the passwords
*/

@Configuration
class SecurityConfig(val userDetailService:AppUserDetailsService){

    @Bean
    fun PasswordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFileChain(http: HttpSecurity): SecurityFilterChain{
        http
            .csrf{it.disable()}
            .authorizeHttpRequests { auth->
                auth
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .anyRequest().authenticated()//secure all other endpoints
            }
            .sessionManagement {
                session->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // to use the stateless session for jwt
            }
            .authenticationProvider(authenticationProvider())

        return http.build()
    }


    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailService)
        authProvider.setPasswordEncoder(PasswordEncoder())
        return authProvider
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }


}