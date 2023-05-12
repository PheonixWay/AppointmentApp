package com.example.appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerDashboardActivity extends AppCompatActivity {
    ImageView createAppointment,logOut,status,profile;
    ProgressBar pb;
    TextView usertxt;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        usertxt=findViewById(R.id.userName_id);
        pb=findViewById(R.id.progressBarID);

        createAppointment=findViewById(R.id.cAppointmentId);
        logOut=findViewById(R.id.logoutID);
        status=findViewById(R.id.statusId);
        profile=findViewById(R.id.profileId);
        final String[] username = new String[1];
        mAuth=FirebaseAuth.getInstance();

        createAppointment.setEnabled(false);
        logOut.setEnabled(false);
        status.setEnabled(false);
        profile.setEnabled(false);

        DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("CustomerUserProfileData/"+mAuth.getUid());
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomerProfileUserDataModel cpudm=snapshot.getValue(CustomerProfileUserDataModel.class);
                username[0] =cpudm.getcName();
                usertxt.setText(username[0]);
                pb.setVisibility(View.INVISIBLE);
                createAppointment.setEnabled(true);
                logOut.setEnabled(true);
                status.setEnabled(true);
                profile.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),Status.class);
                intent.putExtra("UserName",username[0]);
                startActivity(intent);
            }
        });
        createAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),createAppointment.class);
                startActivity(intent);

            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CustomerUserProfile.class);
                startActivity(intent);
            }
        });


    }
}