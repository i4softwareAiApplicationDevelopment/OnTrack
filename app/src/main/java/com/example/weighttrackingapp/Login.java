package com.example.weighttrackingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {
    Button loginButton, registerButton;
    EditText username, password;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize layout and views
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.login);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registrationButton);
        username = (EditText) findViewById(R.id.usernameField);
        password = (EditText) findViewById(R.id.passwordField);

        // Initialize database
        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        // Onclick for registration button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the register class activity
                Intent i = new Intent(getApplicationContext(),Register.class);
                startActivity(i);

            }
        });
        // Onclick for logging in
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Compares the username password input and matches it with ones in database
                if (dbManager.checkUserNamePassword(username.getText().toString(), password.getText().toString())) {
                    // If match, start the main activity
                    Intent i = new Intent(getApplicationContext(),Main.class);
                    startActivity(i);
                }
                // If failed, display incorrect login info
                else {
                    Toast.makeText(Login.this, "Incorrect login information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}