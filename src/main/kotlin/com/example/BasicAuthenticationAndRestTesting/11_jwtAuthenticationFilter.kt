package com.example.BasicAuthenticationAndRestTesting

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService // Injects Spring's service to find users
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 1. Check for the Authorization header
        val authHeader: String? = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response) // If no token, pass the request to the next filter
            return
        }

        // 2. Extract the token from the header
        val jwt: String = authHeader.substring(7) // "Bearer ".length is 7

        // 3. Extract the username (email) from the token
        val userEmail: String = jwtService.extractUsername(jwt)

        // 4. Check if the user is not already authenticated
        if (SecurityContextHolder.getContext().authentication == null) {
            // 5. Load the user details from the database
            val userDetails: UserDetails = this.userDetailsService.loadUserByUsername(userEmail)

            // 6. Validate the token
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // 7. If token is valid, create an authentication token and set it in the security context
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // We don't have credentials, we have a token
                    userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        // 8. Pass the request to the next filter in the chain
        filterChain.doFilter(request, response)
    }
}