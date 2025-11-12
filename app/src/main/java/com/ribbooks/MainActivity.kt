package com.ribbooks

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.ribbooks.auth.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login : Button = findViewById(R.id.login)
        login.setOnClickListener {
            startActivity(Intent(this, LoginScreen::class.java))
        }
    }
}