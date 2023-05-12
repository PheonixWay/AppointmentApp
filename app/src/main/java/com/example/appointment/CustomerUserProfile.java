package com.example.appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerUserProfile extends AppCompatActivity {

    TextInputEditText cName,cProfession,cAddress,cNumber;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_user_profile);
        cName=findViewById(R.id.CNameId);
        cProfession=findViewById(R.id.ProfesionId);
        cAddress=findViewById(R.id.AddressId);
        cNumber=findViewById(R.id.PhoneID);

        mAuth=FirebaseAuth.getInstance();
        String user=mAuth.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("CustomerUserProfileData/"+user);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomerProfileUserDataModel cpdum= snapshot.getValue(CustomerProfileUserDataModel.class);
                cName.setText(cpdum.getcName());
                cProfession.setText(cpdum.getcProfession());
                cAddress.setText(cpdum.getcAddress());
                cNumber.setText(cpdum.getcNumber());
                cName.setEnabled(false);;
                cProfession.setEnabled(false);;
                cAddress.setEnabled(false);
                cNumber.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CustomerUserProfile.this, "Reading data failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}