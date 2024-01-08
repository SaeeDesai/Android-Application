package com.example.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private CheckBox faculty;
    private CheckBox cr;
    private CheckBox br;
    private Button login;
    private Button signupbtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = findViewById(R.id.name_signin);
        password = findViewById(R.id.password);
        faculty = findViewById(R.id.checkBox);
        cr = findViewById(R.id.checkBox2);
        br = findViewById(R.id.checkBox3);

        login = findViewById(R.id.login_button);
        signupbtn = findViewById(R.id.signup1);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName =name.getText().toString();
                String getPass = password.getText().toString();

                if(!getName.isEmpty())
                {
                    name.setError(null);
                    if(!getPass.isEmpty())
                    {
                        password.setError(null);

                        final String name_data = name.getText().toString();
                        final String password_data = password.getText().toString();

                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference().child("Users");

                        Query checkName = databaseReference.orderByChild("name").equalTo(name_data);
                        checkName.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists())
                                {
                                    name.setError(null);
                                    String passwordCheck = snapshot.child(name_data).child("password").getValue(String.class);
                                    if(passwordCheck.equals(password_data))
                                    {
                                        password.setError(null);
                                        Toast.makeText(MainActivity.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                                        if(faculty.isChecked())
                                        {
                                            openFaculty();
                                        }
                                        if(cr.isChecked())
                                        {
                                            openStudent();
                                        }
                                        if(br.isChecked())
                                        {
                                            openStudent();
                                        }
                                    }
                                    else {
                                        password.setError("Wrong password");
                                    }
                                }
                                else {
                                    name.setError("User not found");

                                }
                            }

                            private void openStudent() {
                                Intent student = new Intent(getApplicationContext(),StudentActivity.class);
                                startActivity(student);
                            }

                            private void openFaculty() {
                                Intent faculty = new Intent(getApplicationContext(), FacultyActivity.class);
                                startActivity(faculty);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else {
                        password.setError("Please enter password");
                    }
                }
                else
                {
                    name.setError("Please enter your name");
                }

            }
        });
    }
}