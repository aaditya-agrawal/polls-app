package com.example.pollsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class createPoll extends Activity implements AdapterView.OnItemSelectedListener {
    private DatabaseReference mDatabase;
    private EditText ques, op1,op2;
    private String message1 = "New Poll on ";
    private String message2 = " posted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        Button button = (Button) findViewById(R.id.submit);
        ques = (EditText) findViewById(R.id.ques);
        op1 = (EditText) findViewById(R.id.op1);
        op2 = (EditText) findViewById(R.id.op2);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Fashion");
        categories.add("Beauty");
        categories.add("Lifestyle");
        categories.add("Wedding");
        categories.add("Wellness");
        categories.add("Entertainment");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // Spinner 2 Drop down elements
        List<String> categories2 = new ArrayList<String>();

        categories2.add("Text Poll");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);

        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewUser(spinner.getSelectedItem().toString(),ques.getText().toString(),op1.getText().toString(),op2.getText().toString());


                //Notification Intent
                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                        createPoll.this
                )
                        .setContentText(message1 +spinner.getSelectedItem().toString()+message2)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                        .setAutoCancel(true)
                        .setContentTitle("New poll posted");
                Intent intent1 = new Intent(createPoll.this,MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(createPoll.this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel);
                    builder.setChannelId(channelId);
                }
                notificationManager.notify(0,builder.build());

                //Activities Intent
                Intent intent = new Intent(createPoll.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
    public void writeNewUser(String topic, String question, String option1,String option2) {
         Polls poll = new Polls(topic,question,option1,option2);
         mDatabase.child("polls").push().setValue(poll);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub


    }
}
