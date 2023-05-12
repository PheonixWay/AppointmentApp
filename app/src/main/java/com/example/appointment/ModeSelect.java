package com.example.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModeSelect extends AppCompatActivity {

    Button appbtn,busbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);

        appbtn=findViewById(R.id.Appointment_id);
        busbtn = findViewById(R.id.Business_id);

        appbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                startActivity(intent =new Intent(getApplicationContext(),CreatAccountAsAppointment.class));
            }
        });

        busbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                startActivity(intent =new Intent(getApplicationContext(),CreateAccountAsBusiness.class));
            }
        });
    }
}