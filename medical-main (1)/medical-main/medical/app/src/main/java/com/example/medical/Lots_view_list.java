package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Lots_view_list extends AppCompatActivity {
    ImageButton appointment1;
    ImageButton video;
    ImageButton chat;
    ImageButton directvist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lots_view_list);
        appointment1=(ImageButton) findViewById(R.id.imageButton);
        video=(ImageButton) findViewById(R.id.videobutton);
        chat=(ImageButton) findViewById(R.id.chatsupport);
        directvist=(ImageButton) findViewById(R.id.docvist);

        appointment1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(Lots_view_list.this,"Please book your appointment for doctor", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Lots_view_list.this, appointment4doctor.class);
                startActivity(intent);
                finish();
            }
        });
        video.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(Lots_view_list.this,"Please book your video appointment", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Lots_view_list.this, Videoslot.class);
                startActivity(intent);
                finish();
            }
        });
        chat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(Lots_view_list.this,"Please book your direct chat appointment", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Lots_view_list.this, chat_appointment.class);
                startActivity(intent);
                finish();
            }
        });
        directvist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(Lots_view_list.this,"Please book Doctor direct visit appointment", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Lots_view_list.this, Direct_visit.class);
                startActivity(intent);
                finish();
            }
        });
    }
}