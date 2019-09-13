package com.esi.pharmacie

import android.app.Application
import com.google.firebase.FirebaseApp

class Firebase : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

}