package com.example.authorizationtesting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

val DarkBlueBackground = Color(0xFF1A387E)
val PrimaryBlue = Color(0xFF387BEE)
val LightText = Color(0xFFE0E0E0)
val DarkGreyText = Color(0xFF555555)
val DividerColor = Color(0xFFE0E0E0)
val WhiteCard = Color(0xFFFFFFFF)
val GoogleButtonBorder = Color(0xFFDCDCDC)
val HintGray = Color(0xFF8A8A8A)


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel,
    onLoginSuccess:()-> Unit
){
    val context=LocalContext.current
    var passwordVisible by remember{mutableStateOf(false)}
    var rememberMeChecked by remember{mutableStateOf(false)}

    Box(
        modifier=Modifier.fillMaxSize()
    ){
        Column(
            modifier=Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Spacer(modifier = Modifier.height(80.dp)) // Top padding

            // App Logo (Assuming you have an app_logo.png in your drawable folder)
            Image(
                painter = painterResource(id = R.drawable.app_logo), // Replace with your actual logo
                contentDescription = "App Logo",
                modifier = Modifier.size(72.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sign in to your\nAccount",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                lineHeight = 40.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter your email and password to log in",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 38.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            //white card for the login fields

            Surface(
                modifier=Modifier.fillMaxWidth().weight(1f), //takes the remaining space
                color=Color.White,
                shape= RoundedCornerShape(topStart = 32.dp,topEnd=32.dp),
                shadowElevation = 8.dp
            ){
                Column(
                    modifier=Modifier.fillMaxSize().padding(horizontal=32.dp,vertical=40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    Row(
                        modifier=Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        Text(
                            text="Login With",
                            modifier=Modifier.padding(horizontal=16.dp),
                            color=Color.DarkGray,
                            fontSize=14.sp
                        )

                    }

                    Spacer(modifier=Modifier.height(15.dp))

                    OutlinedTextField(
                        value=viewModel.email,
                        onValueChange = {viewModel.onPasswordChange(it)},
                        label={Text("Email",color=Color.Gray)},
                        modifier=Modifier.fillMaxWidth(),
                        shape=RoundedCornerShape(12.dp),
                        colors= OutlinedTextFieldDefaults.colors(
                            focusedBorderColor=PrimaryBlue,
                            unfocusedBorderColor=LightText,
                            focusedLabelColor=PrimaryBlue,
                            unfocusedLabelColor = HintGray,
                            cursorColor=PrimaryBlue,
                            unfocusedContainerColor = Color(0xFFF0F0F0),
                            focusedContainerColor = Color(0xFFF0F0F0)
                        ),
                        singleLine=true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image=if(passwordVisible)Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(
                                onClick = {passwordVisible=!passwordVisible}
                            ){
                                Icon(
                                    imageVector = image,
                                    contentDescription = "Toggle password visibility"
                                )
                            }
                        }


                    )

                    Spacer(modifier=Modifier.height(18.dp))

                    //Login Button

                    Button(
                        onClick = {
                            viewModel.login(context = context){
                                onLoginSuccess()
                            }
                        },
                        enabled=!viewModel.isLoading,
                        modifier=Modifier.fillMaxWidth().height(12.dp),
                        colors= ButtonDefaults.buttonColors(
                            containerColor=PrimaryBlue,
                            contentColor=Color.White
                        )
                    ){
                        if(viewModel.isLoading){
                            CircularProgressIndicator(
                                modifier=Modifier.size(24.dp),
                                color=Color.White
                            )
                        }else{
                            Text("Log In", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }

                    }

                    //Sign Up text


                    val annotatedString= buildAnnotatedString {
                        withStyle(style= SpanStyle(color=DarkGreyText, fontSize = 14.sp)){
                            append("Don't have an account?")
                        }
                        pushStringAnnotation(tag="SignUp", annotation = "SignUp")
                        withStyle(style=SpanStyle(color=PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp)){
                            append("Sign Up")
                        }
                        pop()
                    }

                    ClickableText(
                        text=annotatedString,
                        onClick = {
                            offset-> //offset is the exact character position where the user clicked
                            annotatedString.getStringAnnotations(tag="SignUp",start=offset,end=offset) //this will return a list
                                .firstOrNull()?.let{ //This firstOrNull will check whether the the list it returned container something at the first character that can be clicked or is that null
                                    navController.navigate("register")
                                }
                        },
                        modifier=Modifier.fillMaxWidth().padding(bottom=16.dp),
                        style= LocalTextStyle.current.copy(textAlign=TextAlign.Center)
                    )

                    /*
                    offset-->offset is the exact character position the user click on

                    now we will be checking for our specific tag at the clicked position

                    annotatedString.getStringAnnotations(...)---->

                    This function is a crucial link
                    it checks the annotatedString and asks "at the exact character ofset where the ues clicked is there an annotation with the tag named signedup

                    .firstOrNull(): Now you have a list that is either empty or has one item. This function safely handles both cases:

                    If the list has an item, it returns that item.

                    If the list is empty, it returns null.

                    ?.let { ... }: This is the final step that performs the action safely.

                    If .firstOrNull() returned an actual annotation (because the user clicked the link), the let block runs, and the navigation happens.

                    If .firstOrNull() returned null (because the user clicked the plain text), the safe call ?. stops execution, and the let block is skipped.

                    */

                }

            }

        }

    }

}