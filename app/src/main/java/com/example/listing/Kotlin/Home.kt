package com.example.listing.Kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.listing.Login
import com.example.listing.R
import com.example.listing.Utils.Loginsession


class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        this.getSupportActionBar()!!.hide()

        var loader : ConstraintLayout = findViewById(R.id.loader)
        var dispatcher : ConstraintLayout = findViewById(R.id.dispatcher)
        //var logout: Button =findViewById(R.id.homelogout_button)

        loader.setOnClickListener {
            var intent = Intent(applicationContext, Loader::class.java)
            startActivity(intent)
        }

        dispatcher.setOnClickListener {
            var intent = Intent(applicationContext, Dispatcher::class.java)
            startActivity(intent)
        }

//        logout.setOnClickListener {
//            Loginsession.getInstance().user=null
//            var intent = Intent(applicationContext, Login::class.java)
//            startActivity(intent)
//        }


    }
}