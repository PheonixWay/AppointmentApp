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

public class RejectedAppointmentByBusiness extends AppCompatActivity {

    RejectedAppointmentAdapter adapter;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejected_appointment_by_business);

        rv=findViewById(R.id.rejectedRcV);
        rv.setLayoutManager(new LinearLayoutManager(this));
        Bundle dataByIntent=getIntent().getExtras();
        String user=dataByIntent.getString("bName");

        FirebaseRecyclerOptions<CreateAppointmentData> options =
                new FirebaseRecyclerOptions.Builder<CreateAppointmentData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("BusinessAppointmentRejected/"+user), CreateAppointmentData.class)
                        .build();


        adapter=new RejectedAppointmentAdapter(options);
        rv.setAdapter(adapter);





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