package com.example.listing.Kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listing.R
import com.example.listing.Utils.RestApi
import com.example.listing.models.User

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.getSupportActionBar()!!.hide()
        setContentView(R.layout.activity_login)

        //DBManager(this)
        val logIn_btn: Button = findViewById(R.id.logIn_btn)

        logIn_btn.setOnClickListener {
            Toast.makeText(applicationContext, "Loggin In...", Toast.LENGTH_SHORT).show()
            var intent = Intent(applicationContext, Home::class.java)
            RestApi.initializer(application, User("",""))
            startActivity(intent)

        }
    }
}
