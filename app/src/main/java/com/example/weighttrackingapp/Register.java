package com.example.weighttrackingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    Button registerButton, backButton;
    EditText username, password, confirmPassword;
    DatabaseManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize view and layout
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.register);
        registerButton = (Button) findViewById(R.id.registrationButton);
        backButton = (Button) findViewById(R.id.registerBackButton);
        username = findViewById(R.id.registerUsernameField);
        password = findViewById(R.id.registerPasswordField);
        confirmPassword = findViewById(R.id.confirmPasswordField);
        // Initialize database
        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        // Onclick for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // On click, check if passwords match and insert it into the database
                // and start main activity
                if (confirmPassword.getText().toString().equals(password.getText().toString())) {
                    dbManager.insert(username.getText().toString(), password.getText().toString());
                    Toast.makeText(Register.this, "Account created!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),Login.class);
                    startActivity(i);
                }
                // else create toast that passwords don't match
                else {
                    Toast.makeText(Register.this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // Onclick for back button to return to login screen
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });
    }
}
