package com.example.medical;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.graphics.pdf.PdfDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Videoslot extends AppCompatActivity {
    private static final int SMS_PERMISSION_CODE = 101;
    private String selectedVideoSlot = null;
    private Button btnBookVideoSlot;  // Declare the button at the class level
    private String selectedAppointmentSlot = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoslot);

        // Initialize buttons
        Button btnSlot1 = findViewById(R.id.btn_s1);
        Button btnSlot2 = findViewById(R.id.btn_s2);
        Button btnSlot3 = findViewById(R.id.btn_s3);
        Button btnSlot4 = findViewById(R.id.btn_s4);
        btnBookVideoSlot = findViewById(R.id.btn_book_video_slot);  // Initialize btn_book_video_slot
        String phoneNumber = "+919443699461";

        // Set click listeners for each slot button
        btnSlot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedVideoSlot = "09:00 AM - 10:00 AM";
                highlightSelectedSlot(btnSlot1, btnSlot2, btnSlot3, btnSlot4);
                sendSlotSms(phoneNumber, selectedAppointmentSlot);
            }
        });

        btnSlot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedVideoSlot = "11:00 AM - 12:00 PM";
                highlightSelectedSlot(btnSlot2, btnSlot1, btnSlot3, btnSlot4);
                sendSlotSms(phoneNumber, selectedAppointmentSlot);

            }
        });

        btnSlot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedVideoSlot = "01:00 PM - 02:00 PM";
                highlightSelectedSlot(btnSlot3, btnSlot1, btnSlot2, btnSlot4);
                sendSlotSms(phoneNumber, selectedAppointmentSlot);

            }
        });

        btnSlot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedVideoSlot = "03:00 PM - 04:00 PM";
                highlightSelectedSlot(btnSlot4, btnSlot1, btnSlot2, btnSlot3);
                sendSlotSms(phoneNumber, selectedAppointmentSlot);

            }
        });

        // Book Slot Button Click Listener
        btnBookVideoSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedVideoSlot != null) {
                    String message = "Your appointment has been booked successfully for " + selectedAppointmentSlot + ".";
                    sendSms(phoneNumber, message);
                    generatePdf(selectedVideoSlot);  // Call the PDF generation method
                    Intent i = new Intent(Videoslot.this, paymentActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Videoslot.this, "Please select a time slot", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendSms(String phoneNumber, String message) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            String fullMessage = message + "\nJoin via Google Meet: https://meet.google.com/srv-ffua-smj";  // Add Google Meet link
            smsManager.sendTextMessage(phoneNumber, null, fullMessage, null, null);
            Toast.makeText(this, "SMS sent to " + phoneNumber, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "SMS permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to send SMS when a slot is selected
    private void sendSlotSms(String phoneNumber, String slot) {
        if (phoneNumber != null) {
            String message = "You have selected the appointment slot: " + slot + ".";
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
        // Create a new PdfDocument
        PdfDocument pdfDocument = new PdfDocument();

        // Define the page info and create the page
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // Get the Canvas object for drawing
        Canvas canvas = page.getCanvas();

        // Set up paint for text
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(14);

        // Draw the slot booking details on the PDF
        canvas.drawText("Video Support Slot Booking Confirmation", 10, 50, paint);
        canvas.drawText("Booked Slot: " + slot, 10, 100, paint);
        canvas.drawText("Thank you for booking with us!", 10, 150, paint);

        // Finish the page
        pdfDocument.finishPage(page);

        // Define the path to save the PDF
        String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(directoryPath, "SlotBookingConfirmation.pdf");

        try {
            // Write the document content
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF generated successfully! Check your Downloads folder.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Close the document
        pdfDocument.close();
    }
}