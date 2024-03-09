package com.example.quizapp_baghdad_ghazi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private Button bBack;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // This links the XML layout file

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find view elements by their IDs
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.bLogin); // Button text should be "register" based on your layout
        bBack = (Button) findViewById(R.id.bBack);
        // Set click listener for register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password is required");
                    return;
                }
                // Password and confirm password comparison
                if (!password.equals(etConfirmPassword.getText().toString())) {
                    etConfirmPassword.setError("Passwords DO NOT MATCH!");
                    return;
                } else {
                    registerUser();
                }
            }
        });
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to activity_login.xml
                Intent loginIntent = new Intent(registerActivity.this, loginActivity.class);
                startActivity(loginIntent);
            }
        });

    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validate user input (e.g., check for empty fields, password strength)

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful, navigate or perform next action
                            Toast.makeText(registerActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                            // Your logic for handling successful registration here (e.g., navigate to another activity)
                        } else {
                            // Registration failed, handle error
                            Exception e = task.getException();
                            Toast.makeText(registerActivity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
