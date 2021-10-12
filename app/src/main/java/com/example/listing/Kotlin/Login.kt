package com.example.listing.Kotlin

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.listing.DataViewModel.LoginView_Model
import com.example.listing.R
import com.example.listing.ViewModelsFactory.LoginView_ModelFactory
import com.example.listing.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class Login : AppCompatActivity() {
    private lateinit var model: LoginView_Model
    private var m_Text = ""

    lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.getSupportActionBar()!!.hide()
        setContentView(R.layout.activity_login)

        //DBManager(this)
        val logIn_btn: Button = findViewById(R.id.logIn_btn)


        model = ViewModelProvider(this, LoginView_ModelFactory(this@Login.application))[LoginView_Model::class.java]


        bottomNavigationView = findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.online -> {
                    saveMode("online")
                    logIn_btn.isEnabled = true
                }
                R.id.offline -> {
                    logIn_btn.isEnabled =false
                    model.OffLogin(getusername())
                    model.Offline.observe(this@Login) { flag -> saveMode("offline") }
                }
            }
            true
        }



        logIn_btn.setOnClickListener {
            Toast.makeText(applicationContext, "Loggin In...", Toast.LENGTH_SHORT).show()
            var intent = Intent(applicationContext, Home::class.java)

            startActivity(intent)

        }
    }
    private fun getusername(): String? {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter Your Username")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
            m_Text = input.text.toString()
            if (m_Text.isEmpty()) {

            }
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
        builder.show()
        Log.i("usert1", m_Text + "")
        return m_Text
    }


    fun saveMode(mode: String?) {
        val sharedPreferences =
            getSharedPreferences(resources.getString(R.string.SharedPrefName), MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(resources.getString(R.string.UserMode), mode)
        editor.apply()
        editor.commit()
    }


}
