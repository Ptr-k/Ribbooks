package com.ribbooks;

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // Inicializa todos los recursos de firebase
        Firebase.initialize(this);
    }
}