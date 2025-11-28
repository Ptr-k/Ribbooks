package com.ribbooks.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DatabaseReference
import com.ribbooks.models.usuario

@Composable
fun RegisterScreen(auth: FirebaseAuth, navController: NavController, database: DatabaseReference) {
    val context = LocalContext.current

    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var usuario by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxSize()
        )

        TextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Telefono") },
            modifier = Modifier.fillMaxSize()
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Clave de acceso") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                TextButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(if (passwordVisible) "Ocultar" else "Mostrar")
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        Button(
            onClick = {
                when {
                    email.isBlank() || password.isBlank() || usuario.isBlank() -> {
                        Toast.makeText(
                            context,
                            "Ningún campo puede estar vacio",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        Toast.makeText(
                            context,
                            "El email no es valido",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // si completa todas las validaciones, crea el usuario:
                    else -> {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { authTask ->
                                if (authTask.isSuccessful) {
                                    val currentUser = auth.currentUser
                                    currentUser?.let { user ->
                                        val uid = user.uid
                                        val userData = usuario(user = usuario, email = email, telefono = telefono)
                                    }
                                }
                            }
                    }
                }
            }
        ) { }
    }
}