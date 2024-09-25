package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Patient_Form extends AppCompatActivity {

    EditText usernameInput, passwordInput, emailInput, phoneNumberInput, addressInput, ageInput, descriptionInput;
    RadioGroup genderGroup;
    RadioButton selectedGender;
    Button submitButton;
    // Database Helper
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_form);

        // Initialize UI elements
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        emailInput = findViewById(R.id.email);
        phoneNumberInput = findViewById(R.id.phoneNumber);
        addressInput = findViewById(R.id.address);
        ageInput = findViewById(R.id.age);
        descriptionInput = findViewById(R.id.description);
        genderGroup = findViewById(R.id.genderGroup);
        submitButton = findViewById(R.id.submitButton);

        // Initialize DBHelper
        dbHelper = new DatabaseHelper(this);

        // Set OnClickListener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        // Get user input
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String phoneNumber = phoneNumberInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();

        // Validate age input with exception handling
        int age;
        try {
            age = Integer.parseInt(ageInput.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid age!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected gender
        int selectedGenderId = genderGroup.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            Toast.makeText(this, "Please select a gender!", Toast.LENGTH_SHORT).show();
            return;
        }
        selectedGender = findViewById(selectedGenderId);
        String gender = selectedGender.getText().toString();

        // Validate input
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(address) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert data into the database
        boolean isInserted = dbHelper.insertUser(username, password, email, phoneNumber, address, gender, age, description);
        if (isInserted) {
            Toast.makeText(this, "Patient added successfully!", Toast.LENGTH_SHORT).show();
            clearForm();
            Intent i = new Intent(Patient_Form.this, Lots_view_list.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Failed to add patient!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearForm() {
        // Clear all input fields
        usernameInput.setText("");
        passwordInput.setText("");
        emailInput.setText("");
        phoneNumberInput.setText("");
        addressInput.setText("");
        ageInput.setText("");
        genderGroup.clearCheck();
        descriptionInput.setText("");
    }
}
