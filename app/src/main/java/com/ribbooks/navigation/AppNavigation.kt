package com.ribbooks.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.ribbooks.auth.HomeScreen
import com.ribbooks.auth.LoginScreen
import com.ribbooks.auth.RegisterScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Register : Screen("register")
}

@Composable
fun AppNavigation(auth: FirebaseAuth, database:DatabaseReference) {
    val navController = rememberNavController()
    val startDestination = if(auth.currentUser  != null) Screen.Home.route else Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(auth, navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(auth, navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(auth, navController)
        }
    }
}