package com.example.mychat;

import static com.example.mychat.R.id.signup21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mychat.ui.gallery.GalleryViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText name;
    private EditText mobileNo;
    private EditText email;
    private EditText password;
    private CheckBox faculty;
    private CheckBox cr;
    private CheckBox br;
    private Button saveToRealTimeDatabase;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    //String token = null;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        name = findViewById(R.id.name);
        mobileNo = findViewById(R.id.mobileNo);
        email = findViewById(R.id.emailsignup);
        password = findViewById(R.id.passwordsignup);
        faculty = findViewById(R.id.faculty);
        cr = findViewById(R.id.cr);
        br = findViewById(R.id.br);
        saveToRealTimeDatabase = findViewById(R.id.signup21);
//        System.out.println(signup2);
        //System.out.println(saveToRealTimeDatabase);


        /*FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                        System.out.println("Token created"+token);

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });*/


        saveToRealTimeDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getName = name.getText().toString();
                String getEmail = email.getText().toString();
                String getMobile = mobileNo.getText().toString();
                String getPass = password.getText().toString();
                String getFaculty = faculty.getText().toString();
                String getCR = cr.getText().toString();
                String getBR = br.getText().toString();
                //String getToken = token;



                HashMap<String, Object> hashmap = new HashMap<>();
                hashmap.put("name",getName);
                hashmap.put("mobileNo",getMobile);
                hashmap.put("email",getEmail);
                hashmap.put("password",getPass);
                //hashmap.put("token",getToken);
                if(faculty.isChecked())
                {
                    hashmap.put("role",getFaculty);
                }
                else if(cr.isChecked())
                {
                    hashmap.put("role", getCR);
                }
                else {
                    hashmap.put("role", getBR);
                }
                databaseReference
                        .child(getName)
                        .setValue(hashmap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this, "Sign up failed!!Please try again", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}