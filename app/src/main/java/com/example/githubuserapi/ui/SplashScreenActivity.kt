package com.example.githubuserapi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

}