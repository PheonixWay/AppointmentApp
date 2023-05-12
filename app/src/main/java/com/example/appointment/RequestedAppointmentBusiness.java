package com.example.appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.WeakReference;

public class RequestedAppointmentBusiness extends AppCompatActivity {

    RecyclerView rv;
    RequestedAppointmentAdapter requestedAppointmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_appointment_business);
        rv=findViewById(R.id.recyclerID);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Bundle dataByIntent=getIntent().getExtras();
        String user=dataByIntent.getString("bName");

        FirebaseRecyclerOptions<CreateAppointmentData> options =
                new FirebaseRecyclerOptions.Builder<CreateAppointmentData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("AppointmentRequested/"+user), CreateAppointmentData.class)
                        .build();


        requestedAppointmentAdapter=new RequestedAppointmentAdapter(options,this);
        rv.setAdapter(requestedAppointmentAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        requestedAppointmentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        requestedAppointmentAdapter.stopListening();
    }
}