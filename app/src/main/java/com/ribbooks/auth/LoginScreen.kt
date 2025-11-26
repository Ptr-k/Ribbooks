package com.ribbooks.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuthException
import com.ribbooks.R
import com.ribbooks.navigation.Screen
import com.ribbooks.auth.LoginScreen

@Composable
fun LoginScreen(auth: FirebaseAuth, navController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo of Ribbooks",
            modifier = Modifier.fillMaxSize(0.5f)
        )
        Column(
            modifier = Modifier.fillMaxWidth(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(
                            context,
                            "Ningún campo puede estar vacio",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        } else {
                            val exception = task.exception
                            when {
                                exception is FirebaseAuthException -> {
                                    when (exception.errorCode) {
                                        // para el error de email incorrecto
                                        "ERROR_INVALID_EMAIL" -> {
                                            Toast.makeText(
                                                context,
                                                "El email no es válido",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        // para el error de contraseña incorrecta
                                        "ERROR_WRONG_PASSWORD" -> {
                                            Toast.makeText(
                                                context,
                                                "Contraseña incorrecta",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }, modifier = Modifier.fillMaxWidth()
            ) { Text("Loggin") }
        }
    }

}