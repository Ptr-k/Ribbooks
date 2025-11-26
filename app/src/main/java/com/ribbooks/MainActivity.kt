package com.ribbooks

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.perf.session.SessionManager
import com.ribbooks.auth.LoginScreen
import com.ribbooks.navigation.AppNavigation
import com.ribbooks.ui.theme.RibbooksTheme

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://ribbooks-default-rtdb.europe-west1.firebasedatabase.app/").reference

        if (!SessionManager.getKeepLoggedIn(this)) {
            auth.signOut()
        }

        setContent{
            RibbooksTheme {
                AppNavigation(auth, database)
            }
        }
    }
}