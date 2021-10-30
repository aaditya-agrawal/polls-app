package com.example.pollsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private PollsAdapter myAdapter;
    private RecyclerView rv;
    private ArrayList<Polls> pollsArrayList;
    private onCLickInterface onclickinterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        pollsArrayList=new ArrayList<>();
        myAdapter =  new PollsAdapter(this,pollsArrayList,onclickinterface);
        rv.setAdapter(myAdapter);

        onclickinterface = new onCLickInterface() {
            @Override
            public void setClick(int abc) {
                Toast.makeText(MainActivity.this,"Position is"+abc,Toast.LENGTH_LONG).show();
                myAdapter.notifyDataSetChanged();
            }
        };

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("polls");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            private static final String TAG ="hii";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                    Polls l=npsnapshot.getValue(Polls.class);
                    pollsArrayList.add(l);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
    public void addEvent (View view) {
        startActivity(new Intent(this, createPoll.class));
    }


}