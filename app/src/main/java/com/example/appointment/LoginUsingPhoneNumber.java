package com.example.appointment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginUsingPhoneNumber extends AppCompatActivity {

    TextInputEditText phoneNumber,Otp;
    MaterialButton submit,login;
    FirebaseAuth mAuth;

    String otpid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_using_phone_number);

        phoneNumber=findViewById(R.id.phoneNumber_id);
        Otp=findViewById(R.id.Otp_Id);
        submit=findViewById(R.id.Submitbtn_id);
        login=findViewById(R.id.login_btn);
        mAuth=FirebaseAuth.getInstance();

        Otp.setEnabled(false);
        login.setEnabled(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNumber.getText().toString().isEmpty()){
                    Toast.makeText(LoginUsingPhoneNumber.this, "Pls Enter phone number", Toast.LENGTH_SHORT).show();
                } else {
                    String no="+91"+phoneNumber.getText().toString();
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(no)
                                    .setTimeout(60L, TimeUnit.SECONDS)
                                    .setActivity(LoginUsingPhoneNumber.this)
                                    .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        @Override
                                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                            super.onCodeSent(s, forceResendingToken);
                                            Toast.makeText(LoginUsingPhoneNumber.this, "code sent", Toast.LENGTH_SHORT).show();
                                            otpid=s;
                                            Otp.setEnabled(true);
                                            login.setEnabled(true);
                                        }

                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                            signInWithPhoneAuthCredential(phoneAuthCredential);
                                            Otp.setEnabled(true);
                                            login.setEnabled(true);
                                        }

                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                            Toast.makeText(LoginUsingPhoneNumber.this, "Mobile number verification failed try again later", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
                }


        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Otp.getText().toString().isEmpty()){
                    Toast.makeText(LoginUsingPhoneNumber.this, "Pls Enter your otp", Toast.LENGTH_SHORT).show();
                } else if (Otp.getText().toString().length()!=6) {
                    Toast.makeText(LoginUsingPhoneNumber.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                    
                }else {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,Otp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });


    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent=new Intent(getApplicationContext(),BussinesDashboardActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(LoginUsingPhoneNumber.this, "Something went wrong pls try again latter", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}