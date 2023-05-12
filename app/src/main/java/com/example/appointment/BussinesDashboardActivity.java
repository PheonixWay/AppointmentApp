package com.example.appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.index.IndexEntry;

public class BussinesDashboardActivity extends AppCompatActivity {

    ImageView logOutImgV,profile,rAppointment,cAppointment,rejected;
    TextView name;
    DatabaseReference dbref;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussines_dashboard);

        rAppointment=findViewById(R.id.RequestedAppointment_id);
        logOutImgV=findViewById(R.id.LogOutId);
        cAppointment=findViewById(R.id.confirmed);
        profile=findViewById(R.id.ProfileID);
        name=findViewById(R.id.nameId);
        progressBar=findViewById(R.id.pb_id);
        rejected=findViewById(R.id.rejected);

        mAuth=FirebaseAuth.getInstance();
        String user=mAuth.getUid();

        profile.setEnabled(false);
        logOutImgV.setEnabled(false);
        rAppointment.setEnabled(false);




        dbref=FirebaseDatabase.getInstance().getReference("UserProfileData/"+user);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileUserDataModel pudm= snapshot.getValue(profileUserDataModel.class);
                name.setText(pudm.getbName());
                progressBar.setVisibility(View.INVISIBLE);
                profile.setEnabled(true);
                logOutImgV.setEnabled(true);
                rAppointment.setEnabled(true);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BussinesDashboardActivity.this, "Reading data failed", Toast.LENGTH_SHORT).show();
            }
        });

        cAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),ConfirmedAppointmentByBusiness.class);
                intent.putExtra("bName",name.getText().toString());
                startActivity(intent);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),BusinessProfile.class);
                startActivity(intent);
            }
        });

        logOutImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });


        rAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RequestedAppointmentBusiness.class);
                intent.putExtra("bName",name.getText().toString());
                startActivity(intent);

            }
        });

        rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RejectedAppointmentByBusiness.class);
                intent.putExtra("bName",name.getText().toString());
                startActivity(intent);

            }
        });


    }

}