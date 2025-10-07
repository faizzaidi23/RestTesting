package com.example.authorizationtesting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RegisterScreen(navController:NavController, viewModel: AuthViewModel){
    val context= LocalContext.current
    var passwordVisible by remember {mutableStateOf(false)}
    var confirmPasswordVisible by remember{mutableStateOf(false)}
    var confirmPassword by remember{mutableStateOf("")} //Local State for confirming the password

    Box(modifier=Modifier.fillMaxSize()){
        Column(
            modifier=Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier=Modifier.height(80.dp))

            //App logo
            Image(
                painter = painterResource(id=R.drawable.app_logo),
                contentDescription = "App logo",
                modifier=Modifier.size(72.dp)

            )

            Spacer(modifier=Modifier.height(16.dp))

            Text(
                text="Create an\n account",
                style= MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color=Color.White,
                textAlign = TextAlign.Center,
                fontSize=32.sp,
                lineHeight = 40.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Let's get you started on your journey",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // White card for Registration fields

            Surface(
                modifier=Modifier.fillMaxWidth()
                    .weight(1f),
                color=Color.LightGray,
                shape= RoundedCornerShape(topStart = 32.dp, topEnd=40.dp)
            ){
                Column(
                    modifier=Modifier.fillMaxSize().
                    padding(horizontal = 32.dp,vertical=40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                    ){

                    OutlinedTextField(value=viewModel.email,
                        onValueChange = {viewModel.onEmailChange(it)},
                        label={Text("Email",color=Color.Gray)},
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier=Modifier.height(16.dp))

                    //Password text field

                    OutlinedTextField(
                        value=viewModel.password,
                        onValueChange = {viewModel.onPasswordChange(it)},
                        label={Text("Password",color=Color.Gray)},
                        modifier=Modifier.fillMaxWidth(),
                        shape=RoundedCornerShape(12.dp),
                        singleLine=true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if(confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image=if(confirmPasswordVisible)Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = {confirmPasswordVisible=!confirmPasswordVisible}){
                                Icon(imageVector = image, contentDescription = "Toggle visiblilty",tint=Color.Gray)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    //Register button
                    Button(
                        onClick = {viewModel.register(context){
                            navController.popBackStack()
                        }
                        },
                        enabled=!viewModel.isLoading,
                        modifier=Modifier.fillMaxWidth()
                            .height(50.dp),
                        shape=RoundedCornerShape(12.dp),
                        colors= ButtonDefaults.buttonColors(
                            containerColor=Color.Gray,
                            contentColor=Color.DarkGray
                        )
                    ){
                        if(viewModel.isLoading){
                            CircularProgressIndicator(
                                modifier=Modifier.size(24.dp),
                                color=Color.White
                            )
                        }else{
                            Text("Create Account",fontSize=16.sp, fontWeight = FontWeight.Bold)
                        }

                    }

                    Spacer(modifier = Modifier.weight(1f))

                    //Login Text
                    val annotatedString = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.DarkGray, fontSize = 14.sp)) {
                            append("Already have an account? ")
                        }
                        pushStringAnnotation(tag = "Login", annotation = "Login")
                        withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 14.sp)) {
                            append("Log In")
                        }
                        pop()
                    }

                    ClickableText(
                        text = annotatedString,
                        onClick = { offset ->
                            annotatedString.getStringAnnotations(tag = "Login", start = offset, end = offset)
                                .firstOrNull()?.let {
                                    navController.popBackStack()
                                }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        style = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                    )

                }

            }


        }
    }
}