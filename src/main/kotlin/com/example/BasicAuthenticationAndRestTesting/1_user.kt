package com.example.BasicAuthenticationAndRestTesting

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document(collection = "users")
data class User(
    @Id
    val id: ObjectId = ObjectId.get(),
    val email: String,
    // I've renamed this field for clarity, which we'll update in the next step
    val password: String,
    val roles: List<String> = listOf("ROLE_USER")
) : UserDetails { // This is the crucial part that makes it compatible

    // --- These methods are required by UserDetails ---

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it) }
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        // We use the email as the username for Spring Security
        return email
    }

    // You can leave these as true for now
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}