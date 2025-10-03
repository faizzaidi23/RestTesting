package com.example.BasicAuthenticationAndRestTesting

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.util.Date
import org.springframework.security.core.userdetails.UserDetails
import java.security.Key
import java.util.function.Function
@Service
class JwtService {

    @Value("\${jwt.secret}") // This injects the secret key from the application.properties file
    private lateinit var secretKey: String

    /**
     * Extracts the username (subject) from the JWT.
     */
    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    /**
     * A generic function to extract a specific claim from the JWT.
     * This is the main utility function that other extraction methods use.
     */
    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        // The cast to java.util.function.Function is for compatibility
        return claimsResolver.apply(claims)
    }

    /**
     * Generates a new JWT for a given user.
     */
    fun generateToken(userDetails: UserDetails): String {
        return Jwts.builder()
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Token is valid for 24 hours
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * Validates the token by checking if the username matches and if the token has expired.
     */
    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username) && !isTokenExpired(token)
    }

    /**
     * Checks if the token's expiration date is before the current date.
     */
    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    /**
     * Extracts only the expiration date from the token.
     * It uses the generic extractClaim function to do this.
     */
    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    /**
     * The foundational private method that parses the token and validates its signature.
     * If the token is invalid, it will throw an exception here.
     */
    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    /**
     * Decodes the Base64 secret key and prepares it for signing.
     */
    private fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
