package com.example.authorizationtesting

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AuthNavigator(onLoginSuccess:()->Unit){
    val navController= rememberNavController()
    val authViewModel: AuthViewModel=viewModel()

    NavHost(navController = navController, startDestination = "Login"){
        composable("login"){
            LoginScreen(
                navController = navController,
                viewModel = authViewModel,
                onLoginSuccess =onLoginSuccess
            )
        }

        composable("register"){
            RegisterScreen(navController = navController,viewModel=authViewModel)
        }
    }
}