package com.example.listing.Kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.listing.R

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        this.getSupportActionBar()!!.hide()
        setContentView(R.layout.activity_login2)
        //DBManager(this)
        val logIn_btn: Button = findViewById(R.id.logIn_btn)

        logIn_btn.setOnClickListener {
            Toast.makeText(applicationContext, "Loggin In...", Toast.LENGTH_SHORT).show()
            var intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)

        }
    }
}
