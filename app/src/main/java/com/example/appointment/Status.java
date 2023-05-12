package com.example.appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Status extends AppCompatActivity {

    TextView userTv;
    RecyclerView recyclerView;
    myAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        userTv=findViewById(R.id.username);
        recyclerView=findViewById(R.id.RecyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Bundle dataByIntent=getIntent().getExtras();

        String username=dataByIntent.getString("UserName");
        userTv.setText(username);


        FirebaseRecyclerOptions<CreateAppointmentData> options =
                new FirebaseRecyclerOptions.Builder<CreateAppointmentData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Appointment/"+username), CreateAppointmentData.class)
                        .build();
        adapter=new myAdapter(options);
        recyclerView.setAdapter(adapter);




    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}