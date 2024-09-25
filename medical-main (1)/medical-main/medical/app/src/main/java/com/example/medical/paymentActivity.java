package com.example.medical;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class paymentActivity extends AppCompatActivity {
    EditText cardno,expiredate,cvv;
    Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        cardno=findViewById(R.id.et_card_number);
        expiredate=findViewById(R.id.et_expiration_date);
        cvv=findViewById(R.id.et_cvv);
        pay=findViewById(R.id.btn_pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(paymentActivity.this, "Payment Successful!", Toast.LENGTH_LONG).show();
            }
        });
    }
}