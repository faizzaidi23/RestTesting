package com.example.BasicAuthenticationAndRestTesting

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    @Id
    val id: ObjectId = ObjectId.get(),

    @Indexed(unique=true)
    val email: String,

    val  password: String
)