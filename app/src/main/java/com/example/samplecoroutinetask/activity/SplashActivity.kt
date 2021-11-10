package com.example.samplecoroutinetask.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.samplecoroutinetask.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            val mainIntent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 3000)
    }
}