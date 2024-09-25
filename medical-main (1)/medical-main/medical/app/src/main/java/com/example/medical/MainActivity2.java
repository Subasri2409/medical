//package com.example.medical;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.google.android.material.button.MaterialButton;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//public class MainActivity2 extends AppCompatActivity {
//
//    EditText etUsername, etEmail, etPassword, etRePassword;
//    MaterialButton btnSignup;
//    DatabaseHelper db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//
//        // Initialize the views
//        etUsername = findViewById(R.id.username);
//        etEmail = findViewById(R.id.email);
//        etPassword = findViewById(R.id.password);
//        etRePassword = findViewById(R.id.re_password);
//        btnSignup = findViewById(R.id.signupbtn);
//
//        // Initialize the database helper
//        db = new DatabaseHelper(this);
//
//        // Set OnClickListener for the signup button
//        btnSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = etUsername.getText().toString();
//                String email = etEmail.getText().toString();
//                String password = etPassword.getText().toString();
//                String rePassword = etRePassword.getText().toString();
//
//                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
//                    Toast.makeText(MainActivity2.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//                } else if (!password.equals(rePassword)) {
//                    Toast.makeText(MainActivity2.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Check if the email already exists in the database
//                    if (db.checkUserExists(email)) {
//                        Toast.makeText(MainActivity2.this, "User already exists", Toast.LENGTH_SHORT).show();
//                    } else {
//                        // Insert user data into the database
//                        long result = db.addUser(username, email, password);
//                        if (result != -1) {
//                            Toast.makeText(MainActivity2.this, "Sign up successful", Toast.LENGTH_SHORT).show();
//                            // Clear the fields after successful registration
//                            etUsername.setText("");
//                            etEmail.setText("");
//                            etPassword.setText("");
//                            etRePassword.setText("");
//                        } else {
//                            Toast.makeText(MainActivity2.this, "Sign up failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }
//        });
//    }
//}