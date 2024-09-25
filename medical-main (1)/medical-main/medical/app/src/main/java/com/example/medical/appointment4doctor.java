package com.example.medical;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class appointment4doctor extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 101;
    private String selectedAppointmentSlot = null;
    private Button btnBookAppointmentSlot;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment4doctor);

        // Initialize buttons
        Button btnSlot1 = findViewById(R.id.btn_s1);
        Button btnSlot2 = findViewById(R.id.btn_s2);
        Button btnSlot3 = findViewById(R.id.btn_s3);
        Button btnSlot4 = findViewById(R.id.btn_s4);
        btnBookAppointmentSlot = findViewById(R.id.btn_book_appointment_slot);

        dbHelper = new DatabaseHelper(this);
        String phoneNumber = "+919443699461";

        // Request SMS permission if not already granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        }

        // Set click listeners for each slot button
        btnSlot1.setOnClickListener(v -> {
            selectedAppointmentSlot = "09:00 AM - 10:00 AM";
            highlightSelectedSlot(btnSlot1, btnSlot2, btnSlot3, btnSlot4);
            sendSlotSms(phoneNumber, selectedAppointmentSlot, "Slot 1");
        });

        btnSlot2.setOnClickListener(v -> {
            selectedAppointmentSlot = "11:00 AM - 12:00 PM";
            highlightSelectedSlot(btnSlot2, btnSlot1, btnSlot3, btnSlot4);
            sendSlotSms(phoneNumber, selectedAppointmentSlot, "Slot 2");
        });

        btnSlot3.setOnClickListener(v -> {
            selectedAppointmentSlot = "01:00 PM - 02:00 PM";
            highlightSelectedSlot(btnSlot3, btnSlot1, btnSlot2, btnSlot4);
            sendSlotSms(phoneNumber, selectedAppointmentSlot, "Slot 3");
        });

        btnSlot4.setOnClickListener(v -> {
            selectedAppointmentSlot = "03:00 PM - 04:00 PM";
            highlightSelectedSlot(btnSlot4, btnSlot1, btnSlot2, btnSlot3);
            sendSlotSms(phoneNumber, selectedAppointmentSlot, "Slot 4");
        });

        // Book Slot Button Click Listener
        btnBookAppointmentSlot.setOnClickListener(v -> {
            if (selectedAppointmentSlot != null) {
                String message = "Your appointment has been booked successfully for " + selectedAppointmentSlot + ".";
                sendSms(phoneNumber, message);  // Send appointment confirmation via SMS
                generatePdf(selectedAppointmentSlot);  // Call the PDF generation method
                Intent i = new Intent(appointment4doctor.this, paymentActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(appointment4doctor.this, "Please select a time slot", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Handle permission request response
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Method to send SMS
    private void sendSms(String phoneNumber, String message) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            // Send the original message
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            // Show success toast
            Toast.makeText(this, "SMS sent to " + phoneNumber, Toast.LENGTH_SHORT).show();
        } else {
            // Show error toast if permission is not granted
            Toast.makeText(this, "SMS permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to send SMS when a slot is selected (includes slot number)
    private void sendSlotSms(String phoneNumber, String slot, String slotNumber) {
        if (phoneNumber != null) {
            String message = "You have selected " + slotNumber + " (" + slot + ").";
            sendSms(phoneNumber, message);
        } else {
            Toast.makeText(this, "Failed to retrieve phone number", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to highlight the selected slot
    private void highlightSelectedSlot(Button selected, Button... others) {
        selected.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        for (Button other : others) {
            other.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        }
    }

    // Method to generate the PDF
    private void generatePdf(String slot) {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(14);

        canvas.drawText("Doctor Appointment Confirmation", 10, 50, paint);
        canvas.drawText("Booked Appointment Slot: " + slot, 10, 100, paint);
        canvas.drawText("Thank you for booking an appointment with Dr. Smith!", 10, 150, paint);
        canvas.drawText("Please arrive 10 minutes early.", 10, 200, paint);

        pdfDocument.finishPage(page);

        String directoryPath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(directoryPath, "DoctorAppointmentConfirmation.pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF generated successfully! Check your Documents folder.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        pdfDocument.close();
    }
}
