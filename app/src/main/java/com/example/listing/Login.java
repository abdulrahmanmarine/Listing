package com.example.listing;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.listing.DataViewModel.LoginView_Model;
import com.example.listing.Kotlin.Home;
import com.example.listing.Utils.Loginsession;
import com.example.listing.ViewModelsFactory.LoginView_ModelFactory;
import com.example.listing.databinding.ActivityLoginBinding;
import com.example.listing.models.User;
import com.example.listing.notes.pictureMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    TextView Error_Msg;
    BottomNavigationView bottomNavigationView;
    private LoginView_Model model;
    private ProgressDialog myProgress;
    private String m_Text = "";

    @SuppressLint("NonConstantResourceId")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        model = new ViewModelProvider(this, new LoginView_ModelFactory(Login.this.getApplication())).get(LoginView_Model.class);

        if (Loginsession.getInstance() != null) {
//            if (Loginsession.getInstance().getUser() != null) {
//                Intent intent = new Intent(Login.this, Home.class);
//                startActivity(intent);
//            }
        }
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Error_Msg = findViewById(R.id.Error_MSG);
        myProgress = new ProgressDialog(Login.this);
        myProgress.setTitle("Logging...");
        myProgress.setMessage("Please wait...");
        myProgress.setCancelable(true);
        myProgress.setIndeterminate(true);


        binding.logInBtn.setOnClickListener(view1 -> {

            model = new ViewModelProvider(this, new LoginView_ModelFactory(Login.this.getApplication())).get(LoginView_Model.class);
            login();

        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.online:
                    saveMode("online");
                    binding.logInBtn.setEnabled(true);
                    binding.logInBtn.setClickable(true);
                    break;
                case R.id.offline:
                    binding.logInBtn.setClickable(false);
                    binding.logInBtn.setEnabled(false);
                    saveMode("offline");
                    Error_Msg.setVisibility(View.INVISIBLE);
                    getusername();
                    model.Offline.observe(Login.this, flag -> {
                        myProgress.dismiss();
                        if (flag) {
                            Intent intent = new Intent(Login.this, Home.class);
                            startActivity(intent);
                        } else {
                            Error_Msg.setText(model.ErrorMsg.getValue());
                            Error_Msg.setVisibility(View.VISIBLE);
                        }
                    });


                    break;
            }
            return true;
        });


    }


    private void getusername() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Username");

// Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            m_Text = input.getText().toString().toUpperCase();
            if (m_Text.isEmpty()) {
                Error_Msg.setText(getResources().getString(R.string.Login_Empty_Error_msg));
                Error_Msg.setVisibility(View.VISIBLE);
            } else {
                myProgress.show();
                model.OffLogin(m_Text, this);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    public void login() {
        Error_Msg.setVisibility(View.INVISIBLE);
        saveMode("online");

        User user = new User(binding.username.getText().toString(), binding.password.getText().toString());
        if (binding.username.getText().toString().isEmpty() || binding.password.getText().toString().isEmpty()) {

            Error_Msg.setText(getResources().getString(R.string.Login_Empty_Error_msg));
            Error_Msg.setVisibility(View.VISIBLE);
        } else {
            myProgress.show();
            Error_Msg.setVisibility(View.GONE);
            model.Login(getApplication(), user, this);
            model.Logged_in.observe(Login.this, Loggedin -> {
                myProgress.dismiss();
                if (Loggedin != null) {
                    if (Loggedin) {
                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);
                    } else {
                        model.Logged_in.setValue(null);
                        Error_Msg.setText(model.ErrorMsg.getValue());
                        Error_Msg.setVisibility(View.VISIBLE);
                    }
                }


            });
        }
    }

    public void saveMode(String mode) {
        SharedPreferences sharedPreferences = Login.this.getSharedPreferences(getResources().getString(R.string.SharedPrefName), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.UserMode), mode);
        editor.apply();
        editor.commit();
    }



}