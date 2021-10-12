package com.example.listing.Kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.listing.R


class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        this.getSupportActionBar()!!.hide()

        var loader : ConstraintLayout = findViewById(R.id.loader)
        var dispatcher : ConstraintLayout = findViewById(R.id.dispatcher)


        loader.setOnClickListener {
            var intent = Intent(applicationContext, Loader::class.java)
            startActivity(intent)
        }

        dispatcher.setOnClickListener {
            var intent = Intent(applicationContext, Dispatcher::class.java)
            startActivity(intent)
        }

    }
}