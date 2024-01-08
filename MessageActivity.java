package com.example.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {


    private EditText message;

    private TextView dateText,timeText;
    private Button sendmsg,date,time;
    private CheckBox faculty, cr, br;
    String messagestr = "";
    ArrayList<String> mobileNo = new ArrayList<>();

    //ArrayList<String> fcmTokens = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        FirebaseDatabase firebasedatabase = FirebaseDatabase.getInstance();
        DatabaseReference dataref = firebasedatabase.getReference("Users");
        //DatabaseReference tokenData = firebasedatabase.getReference("Users");
        /*Query msgToFaculty = dataref.orderByChild("role").equalTo("Faculty");
        Query msgToCR = dataref.orderByChild("role").equalTo("CR");
        Query msgToBR = dataref.orderByChild("role").equalTo("BR");*/


        /*dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String mobileNumber = userSnapshot.child("mobileNo").getValue(String.class);
                    mobileNo.add(mobileNumber);
                    //System.out.println("The mobile numbers are"+ mobileNumber);
                }
                //System.out.println(mobileNo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });*/

            message = findViewById(R.id.editTextTextPersonName);
            sendmsg = findViewById(R.id.sendmsg);


        /*date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDate();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTime();
            }
        });*/

        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messagestr = message.getText().toString();
                dataref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String mobileNumber = userSnapshot.child("mobileNo").getValue(String.class);
                            mobileNo.add(mobileNumber);
                            //System.out.println("The mobile numbers are"+ mobileNumber);
                        }
                        //System.out.println(mobileNo);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });

                // Create a Calendar object to store the selected date and time
                Calendar calendar = Calendar.getInstance();

// Create a DatePickerDialog to allow the user to select the date
                DatePickerDialog datePickerDialog = new DatePickerDialog(MessageActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Set the selected date in the Calendar object
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Create a TimePickerDialog to allow the user to select the time
                                TimePickerDialog timePickerDialog = new TimePickerDialog(MessageActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                // Set the selected time in the Calendar object
                                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                                calendar.set(Calendar.MINUTE, minute);
                                                calendar.set(Calendar.SECOND, 0);
                                                calendar.set(Calendar.MILLISECOND, 0);

                                                Toast.makeText(MessageActivity.this, "Message Scheduled", Toast.LENGTH_SHORT).show();

                                                // Check if WhatsApp is installed and the message is not empty
                                                if (isWhatsappInstalled() && !messagestr.isEmpty()) {
                                                    // Iterate over the list of mobile numbers and send the message to each number
                                                    for (String mobileNumber : mobileNo) {
                                                        // Create the Intent to send the message
                                                        Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+ mobileNumber +"&text="+messagestr));
                                                        sendIntent.setPackage("com.whatsapp");

                                                        // Create the PendingIntent to send the message
                                                        PendingIntent pendingIntent = PendingIntent.getActivity(MessageActivity.this, 0, sendIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                                        // Create a BroadcastReceiver to send the PendingIntent when the alarm is triggered
                                                        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                                                            @Override
                                                            public void onReceive(Context context, Intent intent) {
                                                                try {
                                                                    pendingIntent.send();
                                                                } catch (PendingIntent.CanceledException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        };

                                                        // Register the BroadcastReceiver to be triggered by the alarm
                                                        IntentFilter intentFilter = new IntentFilter("com.example.whatsappdemo.SEND_MESSAGE");
                                                        MessageActivity.this.registerReceiver(broadcastReceiver, intentFilter);

                                                        // Schedule the PendingIntent to be triggered at the specified time
                                                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                                                        /*Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                                                        startActivity(intent);*/


                                                    }
                                                    Toast.makeText(MessageActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                                                    messagestr = "";
                                                } else {
                                                    Toast.makeText(MessageActivity.this, "WhatsApp not installed or message is empty", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

                                // Show the TimePickerDialog
                                timePickerDialog.show();
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

// Show the DatePickerDialog
                datePickerDialog.show();




                /*if(!messagestr.isEmpty())
                {
                    if(isWhatsappInstalled())
                    {

                        for(String mobileNumber: mobileNo){
                            Intent msgintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+ mobileNumber +"&text="+messagestr));
                            startActivity(msgintent);
                            //messagestr = "";
                        }
                        messagestr = "";




                    }
                    else {
                        Toast.makeText(MessageActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MessageActivity.this, "Please enter the message", Toast.LENGTH_SHORT).show();
                }*/
            }
        });



    }

    /*private void openDate()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                dateText.setText(String.valueOf(d)+"/"+String.valueOf(m+1)+"/"+String.valueOf(y));
                //System.out.println(dateText);
            }
        }, 2022, 0, 10);

        datePickerDialog.show();
    }

    private void openTime()
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                timeText.setText(String.valueOf(hour)+":"+String.valueOf(minute));
                //System.out.println(timeText);
            }
        },15,00,false);
        timePickerDialog.show();
    }*/



    private boolean isWhatsappInstalled()
    {
        //PackageManager packageManager = getPackageManager();

        boolean whatsAppInstalled;

        try {
            getPackageManager().getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            whatsAppInstalled = true;
        }catch (PackageManager.NameNotFoundException e)
        {
            whatsAppInstalled = false;
        }

        return whatsAppInstalled;
    }

}