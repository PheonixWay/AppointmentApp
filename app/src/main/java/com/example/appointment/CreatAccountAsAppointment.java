package com.example.appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreatAccountAsAppointment extends AppCompatActivity {

    Button registerBtn,loginBtn;
    TextInputEditText email,password,cPassword,cName,cProfession,cAddress,cNumber;
    ProgressBar pb;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    TextView alreadyRegistered;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_as_appointment);

        registerBtn=findViewById(R.id.idBtnRegister);
        loginBtn=findViewById(R.id.idBtnLogin);
        alreadyRegistered=findViewById(R.id.alreadyRegistered_id);
        email=findViewById(R.id.gmail_id);
        password=findViewById(R.id.pass_id);
        cPassword=findViewById(R.id.confirmPass_id);
        cName=findViewById(R.id.cnameid);
        cProfession=findViewById(R.id.cprofession);
        cAddress=findViewById(R.id.caddress);
        cNumber=findViewById(R.id.cnumber);
        pb=findViewById(R.id.progressBarid);
        pb.setVisibility(View.INVISIBLE);
        mAuth=FirebaseAuth.getInstance();

        cName.setEnabled(false);
        cProfession.setEnabled(false);
        cAddress.setEnabled(false);
        cNumber.setEnabled(false);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail= String.valueOf(email.getText());
                String cPass= String.valueOf(cPassword.getText());
                if (mail.isEmpty()|cPass.isEmpty()|password.getText().toString().isEmpty()){
                    Toast.makeText(CreatAccountAsAppointment.this, "Pls set gmail and password before register", Toast.LENGTH_SHORT).show();
                } else if (!isEmailValid(email.getText().toString())) {
                    Toast.makeText(CreatAccountAsAppointment.this, "Pls enter valid Gmail", Toast.LENGTH_SHORT).show();
                }
                else {
                    pb.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(mail,cPass).addOnCompleteListener(CreatAccountAsAppointment.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                pb.setVisibility(View.INVISIBLE);
                                mAuth.signInWithEmailAndPassword(mail,cPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            cName.setEnabled(true);
                                            cProfession.setEnabled(true);
                                            cAddress.setEnabled(true);
                                            cNumber.setEnabled(true);
                                            Toast.makeText(CreatAccountAsAppointment.this, "Successfully created Your Account Pls Fill your data to continue", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(CreatAccountAsAppointment.this, "Pls enter valid email and Password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }
                            else
                            {
                                Toast.makeText(CreatAccountAsAppointment.this, "Something Went Wrong Please try Again later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });


        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CreatAccountAsAppointment.this,LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAuth.getCurrentUser()==null){
                    Toast.makeText(CreatAccountAsAppointment.this, "Pls first create account using your gmail ", Toast.LENGTH_SHORT).show();
                } else if (cName.getText().toString()!=null & cProfession.getText().toString()!=null&cAddress.getText().toString()!=null&cNumber.getText().toString()!=null) {
                    databaseReference= FirebaseDatabase.getInstance().getReference("CustomerUserProfileData/"+mAuth.getUid());
                    CustomerProfileUserDataModel cpudm=new CustomerProfileUserDataModel(cName.getText().toString(),cProfession.getText().toString(),cAddress.getText().toString(),cNumber.getText().toString());
                    databaseReference.setValue(cpudm);

                    Toast.makeText(CreatAccountAsAppointment.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(CreatAccountAsAppointment.this,CustomerDashboardActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
                else {
                    Toast.makeText(CreatAccountAsAppointment.this, "Pls Fill your details all fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                
            }
        });


    }
    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }





}