package com.example.appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class pickdate extends AppCompatActivity {

    Button Submit;
    TimePicker pickTime;
    DatePicker pickDate;
    FirebaseAuth mAuth;

    //database initializing
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickdate);
        Submit=findViewById(R.id.submit_id);
        Bundle dataByIntent=getIntent().getExtras();
        pickDate=findViewById(R.id.date_picker_id);
        pickTime=findViewById(R.id.timePickerId);
        mAuth=FirebaseAuth.getInstance();


        final String[] name_data = {dataByIntent.getString("Name")};
        String email_data=dataByIntent.getString("Email");
        String phone_data=dataByIntent.getString("Phone");
        String reason_data=dataByIntent.getString("Reason");
        String business_id_data= dataByIntent.getString("Business");
        String idForAppointment= dataByIntent.getString("AppointmentId");
        String status="pending";
        final String[] userName = new String[1];
        DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("CustomerUserProfileData/"+mAuth.getUid());
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomerProfileUserDataModel cpudm=snapshot.getValue(CustomerProfileUserDataModel.class);
                assert cpudm != null;
                userName[0] =cpudm.getcName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String name=userName[0];

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("AppointmentRequested/" + business_id_data);



        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String time= String.valueOf(pickTime.getHour())+"-"+String.valueOf(pickTime.getMinute());
               String date=pickDate.getDayOfMonth()+"-"+(pickDate.getMonth()+1)+"-"+pickDate.getYear();

                if (!time.isEmpty()&&!date.isEmpty()){
                    DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("CustomerUserProfileData/"+mAuth.getUid());
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            CustomerProfileUserDataModel cpudm=snapshot.getValue(CustomerProfileUserDataModel.class);
                            assert cpudm != null;
                            userName[0] =cpudm.getcName();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    String name=userName[0];

                    databaseReference= FirebaseDatabase.getInstance().getReference("Appointment/"+name+"/"+idForAppointment);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("AppointmentRequested/" + business_id_data);
                    CreateAppointmentData createAppointmentData=new CreateAppointmentData(name_data[0],email_data,phone_data,reason_data,business_id_data,status,date,time,idForAppointment);
                    databaseReference.setValue(createAppointmentData);
                    databaseReference1.push().setValue(createAppointmentData);
                    Toast.makeText(pickdate.this, "Successfully Created Appointment pls check Status for confirmation of Your Appointment", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),CustomerDashboardActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }else {
                    Toast.makeText(pickdate.this, "Pls select date and time if not selected", Toast.LENGTH_SHORT).show();
                }
                
                
                
            }
        });
    }

}