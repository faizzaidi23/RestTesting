package com.example.authorizationtesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.authorizationtesting.ui.theme.AuthorizationTestingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuthorizationTestingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // This is the main navigation hub for the app
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var isLoggedIn by remember { mutableStateOf(false) }

    if (isLoggedIn) {
        HomeScreen()
    } else {
        // If not logged in, show the AuthNavigator (Login/Register screens).
        // We pass a function that will be called on successful login.
        AuthNavigator(onLoginSuccess = {
            isLoggedIn = true
        })
    }
}