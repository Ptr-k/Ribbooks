package com.ribbooks.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Register : Screen("register")
}

@Composable
fun AppNavigation(auth: FirebaseAuth, database:DatabaseReference) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        // te lleva a la pantalla de login si no hay usuario loggeado.
        startDestination = if(auth.currentUser  != null) Screen.Home.route else Screen.Login.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController,auth)
        }
    }
}