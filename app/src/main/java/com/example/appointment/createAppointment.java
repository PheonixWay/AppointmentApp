package com.example.appointment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class createAppointment extends AppCompatActivity {

    TextInputEditText name,email,phone,reason;
    Spinner selectBussinesSppiner;

    Button next;

    ValueEventListener listener;
    ArrayList <String> myBusinessdataForSppiner;
    ArrayAdapter <String> mySppinerAdapter;
    DatabaseReference dbref;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        next=findViewById(R.id.next_id);
        name=findViewById(R.id.name_id);
        email=findViewById(R.id.email_id);
        phone=findViewById(R.id.phone_id);
        reason=findViewById(R.id.reason_id);
        selectBussinesSppiner=findViewById(R.id.select_business_id);//my spinner ui

        final Random myRandom = new Random();
        String idForAppointment= String.valueOf(myRandom.nextInt(99999999));
        mAuth=FirebaseAuth.getInstance();
        dbref= FirebaseDatabase.getInstance().getReference("BusinessData");
        DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("CustomerUserProfileData/"+mAuth.getUid());
        databaseReference2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomerProfileUserDataModel cpudm=snapshot.getValue(CustomerProfileUserDataModel.class);
                name.setText(cpudm.getcName());
                name.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        myBusinessdataForSppiner =new ArrayList<String>();
        mySppinerAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,myBusinessdataForSppiner);
        selectBussinesSppiner.setAdapter(mySppinerAdapter);





        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_data=name.getText().toString();
                String email_data=email.getText().toString();
                String phone_data=phone.getText().toString();
                String reason_data=reason.getText().toString();
                String business_id_data= selectBussinesSppiner.getSelectedItem().toString();
                if (!name_data.isEmpty()&&!email_data.isEmpty()&&!phone_data.isEmpty()&&!reason_data.isEmpty()&&!business_id_data.isEmpty()){
                    Intent intent=new Intent(getApplicationContext(),pickdate.class);
                    intent.putExtra("Name",name_data);
                    intent.putExtra("Email",email_data);
                    intent.putExtra("Phone",phone_data);
                    intent.putExtra("Reason",reason_data);
                    intent.putExtra("Business",business_id_data);
                    intent.putExtra("AppointmentId",idForAppointment);
                    startActivity(intent);
                }else {
                    Toast.makeText(createAppointment.this, "Please fill all the data to Create Appointment", Toast.LENGTH_SHORT).show();
                }


            }
        });
        fetchdata();

    }
    private void fetchdata(){
        listener=dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myBusinessdataForSppiner.clear();
                for(DataSnapshot mydata : snapshot.getChildren()){
                    myBusinessdataForSppiner.add(mydata.getValue().toString());
                }
                mySppinerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(createAppointment.this, "something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}