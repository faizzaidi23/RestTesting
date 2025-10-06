package com.example.authorizationtesting.Connection

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import okhttp3.MediaType.Companion.toMediaType

interface ApiService{

    @POST("/api/auth/register")
    suspend fun register(@Body request: AuthRequest): Response<Unit>

    @POST("/api/auth/login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>
}

// A singleton object to ensure we have only one instance of the Retrofit for the entire application
object RetrofitClient{

    //Critical:This is the IP address the android emulator
    private  const val BASE_URL=""

    private val json=Json{ignoreUnknownKeys=true}

    //Use lazy to create the Retrofit instance only when it is first needed

    val instance: ApiService by lazy{

        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        //Create the implementation of the ApiService interface

        retrofit.create(ApiService::class.java)

    }
}