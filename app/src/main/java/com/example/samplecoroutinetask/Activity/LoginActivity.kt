package com.example.samplecoroutinetask.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.samplecoroutinetask.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signinbtn.setOnClickListener {
            if (email.hasFocus()) {
                email.clearFocus()
            }
            if (password.hasFocus()) {
                password.clearFocus()
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}