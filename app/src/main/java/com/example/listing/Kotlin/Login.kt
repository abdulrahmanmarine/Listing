package com.example.listing.Kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.listing.DataViewModel.PlansDataModel
import com.example.listing.DataViewModel.PlansDataModelFactory
import com.example.listing.R
import com.example.listing.Utils.RestApi
import com.example.listing.models.Plan2
import com.example.listing.models.User

class Login : AppCompatActivity() {
    private lateinit var model: PlansDataModel

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
