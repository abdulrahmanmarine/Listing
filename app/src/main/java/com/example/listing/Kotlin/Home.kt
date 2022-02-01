package com.example.listing.Kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.listing.Login
import com.example.listing.R
import com.example.listing.Utils.DataClass
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Home : AppCompatActivity() {

    lateinit var logout : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        this.getSupportActionBar()!!.hide()

        var loader : ConstraintLayout = findViewById(R.id.loader)
        var dispatcher : ConstraintLayout = findViewById(R.id.dispatcher)
        logout=findViewById(R.id.homelogout_button);
        DataClass.initializer()


        loader.setOnClickListener {
            var intent = Intent(applicationContext, Loader::class.java)
            startActivity(intent)
        }

        dispatcher.setOnClickListener {
            var intent = Intent(applicationContext, Dispatcher::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
           // Loginsession.getInstance().user=null
            var intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }


    }
}