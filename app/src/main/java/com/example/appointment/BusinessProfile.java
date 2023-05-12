package com.example.appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BusinessProfile extends AppCompatActivity {

    TextInputEditText businessName,ownerName,businessDetails,businessEmail;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);

        businessName=findViewById(R.id.BusinessNameId);
        ownerName=findViewById(R.id.OwnerNameID);
        businessDetails=findViewById(R.id.BusinessDetailsId);
        businessEmail=findViewById(R.id.BusinessEmailId);
        mAuth=FirebaseAuth.getInstance();
        String user=mAuth.getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference("UserProfileData/"+user);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileUserDataModel pudm= snapshot.getValue(profileUserDataModel.class);
                businessName.setText(pudm.getbName());
                ownerName.setText(pudm.getbOwner());
                businessDetails.setText(pudm.getbDetails());
                businessEmail.setText(pudm.getbEmail());
                businessName.setEnabled(false);;
                ownerName.setEnabled(false);;
                businessDetails.setEnabled(false);
                businessEmail.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BusinessProfile.this, "Reading data failed", Toast.LENGTH_SHORT).show();
            }
        });




    }
}