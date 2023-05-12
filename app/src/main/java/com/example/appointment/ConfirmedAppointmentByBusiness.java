package com.example.appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmedAppointmentByBusiness extends AppCompatActivity {

    ConfirmedAppointmentAdapter adapter;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_appointment_by_business);

        rv=findViewById(R.id.confirmedRcV);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Bundle dataByIntent=getIntent().getExtras();
        String user=dataByIntent.getString("bName");

        FirebaseRecyclerOptions<CreateAppointmentData> options =
                new FirebaseRecyclerOptions.Builder<CreateAppointmentData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("BusinessAppointmentConfirmed/"+user), CreateAppointmentData.class)
                        .build();
        adapter= new ConfirmedAppointmentAdapter(options);
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