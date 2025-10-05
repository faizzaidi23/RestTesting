package com.example.BasicAuthenticationAndRestTesting

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    // We only need to inject the filter here now
    private val jwtAuthFilter: JwtAuthenticationFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity, authenticationProvider: AuthenticationProvider): SecurityFilterChain {
        http
            // 1. Disable CSRF (Cross-Site Request Forgery)
            .csrf { it.disable() }

            // 2. Define authorization rules
            .authorizeHttpRequests { auth -> auth
                // Allow all requests to the /api/v1/auth/** endpoints (for register and login)
                .requestMatchers("/api/v1/auth/**").permitAll()
                // All other requests must be authenticated
                .anyRequest().authenticated()
            }

            // 3. Set session management to stateless (no sessions will be created)
            .sessionManagement { session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

            // 4. Set the custom authentication provider
            .authenticationProvider(authenticationProvider)

            // 5. THIS IS THE CRITICAL MISSING PIECE: Add the JWT filter to the security chain
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun authenticationProvider(
        userDetailsService: AppUserDetailsService,
        passwordEncoder: PasswordEncoder
    ): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder) // Correctly inject the password encoder bean
        return authProvider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}