package com.example.mychat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FacultyActivity extends AppCompatActivity {


    private Button sendMessage;
    private Button viewStudents;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty);

        sendMessage = findViewById(R.id.button);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intent1);
            }
        });

        viewStudents = findViewById(R.id.button2);

        viewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudent();
            }
        });
    }

    private void openStudent() {

            Intent intent1 = new Intent(getApplicationContext(), StudentActivity.class);
            startActivity(intent1);
    }
}