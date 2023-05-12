package com.example.appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CreateAccountAsBusiness extends AppCompatActivity {

    TextView alreadyRegisteredBtn;
    Button verify,submit,register;

    TextInputEditText phone,otp,bName,oName,bDetails,bEmail;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    String otpid;
    String userID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_as_business);

        alreadyRegisteredBtn=findViewById(R.id.registerId);
        verify=findViewById(R.id.VerifyNumber_id);
        submit=findViewById(R.id.SubmitID);
        phone=findViewById(R.id.PhoneNumber_ID);
        register=findViewById(R.id.idBtnRegister);
        otp=findViewById(R.id.OtpID);
        bName=findViewById(R.id.BusinessNameId);
        oName=findViewById(R.id.OwnerNameID);
        bDetails=findViewById(R.id.BusinessDetailsId);
        bEmail=findViewById(R.id.BusinessEmailId);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();


        otp.setEnabled(false);
        register.setEnabled(false);
        bName.setEnabled(false);
        oName.setEnabled(false);
        bDetails.setEnabled(false);
        bEmail.setEnabled(false);
        submit.setEnabled(false);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.getText().toString().isEmpty()){
                    Toast.makeText(CreateAccountAsBusiness.this, "Pls Enter phone number", Toast.LENGTH_SHORT).show();
                } else {
                    String no="+91"+phone.getText().toString();
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(no)
                                    .setTimeout(60L, TimeUnit.SECONDS)
                                    .setActivity(CreateAccountAsBusiness.this)
                                    .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        @Override
                                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                            super.onCodeSent(s, forceResendingToken);
                                            Toast.makeText(CreateAccountAsBusiness.this, "code sent", Toast.LENGTH_SHORT).show();
                                            otpid=s;
                                            otp.setEnabled(true);
                                            register.setEnabled(true);
                                            bName.setEnabled(true);
                                            oName.setEnabled(true);
                                            bDetails.setEnabled(true);
                                            bEmail.setEnabled(true);
                                            submit.setEnabled(true);
                                        }

                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                            //agar verification hojaye to kya karna
                                            signInWithPhoneAuthCredential(phoneAuthCredential);
                                        }

                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                            Toast.makeText(CreateAccountAsBusiness.this, "Mobile number verification failed try again later", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }

                }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Pls Enter your otp", Toast.LENGTH_SHORT).show();
                } else if (otp.getText().toString().length()!=6) {
                    Toast.makeText(getApplicationContext(), "Invalid Otp", Toast.LENGTH_SHORT).show();

                }else {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                DatabaseReference databaseReference=firebaseDatabase.getReference("UserProfileData").child(user);
                profileUserDataModel pudm=new profileUserDataModel(bName.getText().toString(),oName.getText().toString(),bDetails.getText().toString(),bEmail.getText().toString());
                databaseReference.setValue(pudm);
                Toast.makeText(CreateAccountAsBusiness.this, "Successfully data pushed to database", Toast.LENGTH_SHORT).show();
                DatabaseReference databaseReference1=firebaseDatabase.getReference("BusinessData");
                databaseReference1.push().setValue(bName.getText().toString());
                startActivity(new Intent(getApplicationContext(),BussinesDashboardActivity.class));
                finishAffinity();
            }
        });






        alreadyRegisteredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CreateAccountAsBusiness.this,LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(CreateAccountAsBusiness.this, "Successfully Sign In Pls fill data to go on DashBoard ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateAccountAsBusiness.this, "Something went wrong pls try again latter", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}