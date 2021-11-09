package com.example.samplecoroutinetask.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.samplecoroutinetask.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    var login: String = ""
    var id: String = ""
    var type: String = ""
    var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        login = intent.getStringExtra("login").toString()
        id = intent.getStringExtra("id").toString()
        type = intent.getStringExtra("type").toString()
        url = intent.getStringExtra("url").toString()

        detail_value.text = login + "\n" + id + "\n" + type + "\n" + url
    }
}